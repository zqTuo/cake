var ue;

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/course/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '课程名称', name: 'title', index: 'title', width: 80 }, 			
			{ label: '分类名称', name: 'categoryName', index: 'category_name', width: 80 },
			{ label: '主图图片', name: 'courseImg', index: 'course_img', width: 80 ,formatter:function (cellValue, options, rowObject) {
                    return '<img class="img-thumbnail" width="80px" src="/images/'+cellValue+'" alt="图片">'
                }},
			{ label: '售价', name: 'price', index: 'price', width: 80 },
			{ label: '课程简介', name: 'courseDes', index: 'course_des', width: 80 },
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

    // 文章内容 实例化百度富文本编辑器
    ue = UE.getEditor('baseDataEditor',{
        initialFrameWidth : 645
    });
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action === 'uploadimage') {
            console.log("正在进行ueditor图片上传....");
            return '/cake-admin/admin/upload/action';    /* 这里填上你自己的上传图片的接口地址 */
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };

    $("#banner_pic").takungaeImgup({
        formData: {},
        url:"/cake-admin/admin/upload/uploadFile.do" //上传路径
    });
});



var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		course: {},
        cateList:[],
        bannerArr:[],
        comboTypeList:[]
	},
    mounted: function(){
        var that = this;
        that.getCateList();
        that.getComboTypeList();

    },
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.course = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.course.id == null ? "sys/course/save" : "sys/course/update";

                var content = "";
                ue.ready(function () {
                    content = ue.getContent();
                });

                vm.course.courseInfo = content;

                var banner_imgs = [];
                var banner_imgsObj = $(".banner img[class='up-img']");
                var banner_imgsLen = banner_imgsObj.length;
                for(var i=0;i<banner_imgsLen;i++){
                    var bannersPath = banner_imgsObj[i].src;
                    banner_imgs.push(bannersPath);
                }
                vm.course.courseBanner = banner_imgs.toString();

                vm.course.courseImg = $("#imgProDetail100").attr("src");
                if(vm.course.courseImg === "/cake-admin/statics/img/a11.png"){
                    layer.alert("请上传商品主图！");
                }

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.course),
                    headers: {
                        auth: "ueditor"
                    },
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                            location.replace(location.href);
                        }else{
                            layer.alert(r.msg);
                            $('#btnSaveOrUpdate').button('reset');
                            $('#btnSaveOrUpdate').dequeue();
                        }
                    }
                });
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "sys/course/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function(r){
                            if(r.code == 0){
                                layer.msg("操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
				    });
			    }
             }, function(){
             });
		},
		getInfo: function(id){
			$.get(baseURL + "sys/course/info/"+id, function(r){
                vm.course = r.course;
                vm.bannerArr = r.bannerArr;
                var content = r.course.courseInfo;
                ue.ready(function () {
                    ue.setContent(content);
                });
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        getCateList: function (event) {
            $.get(baseURL + "sys/productcategory/findAll?showFlag=2", function(r){
                vm.cateList = r.arrayData;
                var cate = {};
                cate.id = 0;
                cate.lev = 0;
                cate.name = '请选择类型';
                vm.cateList.unshift(cate)
            });
        },
        checkType: function (e) {
            var idx = e.target.options.selectedIndex;
            if(idx > 0){
                var depth = vm.cateList[idx].lev;
                if(depth === 0){
                    $(this).val(-1);
                    $("#cateType option[value='-1']").prop("selected",true);
                    layer.msg("不能选择一级目录！",{icon:7,time:3000})
                }
            }

        },
        getComboTypeList:function () {
            $.get(baseURL + "sys/setcourse/cateList", function(r) {
                if (r.code === 0) {
                    vm.comboTypeList = r.typeList;
                }
            })
        }
	}
});


