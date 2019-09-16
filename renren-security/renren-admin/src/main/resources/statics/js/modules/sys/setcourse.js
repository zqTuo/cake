$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/setcourse/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '套餐名称', name: 'title', index: 'title', width: 80 },
			{ label: '套餐图片', name: 'picUrl', index: 'pic_url', width: 80 ,formatter:function (cellValue, options, rowObject) {
                    return '<img class="img-thumbnail" width="80px" src="/images/'+cellValue+'" alt="商品图片">'
                }},
			{ label: '有效期', name: 'validPeriod', index: 'valid_period', width: 80  ,formatter:function (cellValue, options, rowObject) {
                    if(cellValue === 0){
                        return '无期限'
                    }else{
                        return cellValue + '个月'
                    }
                }},
			{ label: '套餐价格', name: 'price', index: 'price', width: 80 },
			{ label: '套餐描述', name: 'remark', index: 'remark', width: 80 }
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		setCourse: {},
        detailList:[]
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.setCourse = {};
			vm.detailList = [];
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
                var url = vm.setCourse.id == null ? "sys/setcourse/save" : "sys/setcourse/update";

                vm.setCourse.picUrl = $("#imgProDetail100").attr("src");
                if(vm.setCourse.picUrl === "/cake-admin/statics/img/a11.png"){
                    layer.alert("请上传套餐主图！");
                    return;
                }

                var data = {
                    setCourse:vm.setCourse,
                    detailList:vm.detailList
                }

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(data),
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
                        url: baseURL + "sys/setcourse/delete",
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
			$.get(baseURL + "sys/setcourse/info/"+id, function(r){
                vm.setCourse = r.setCourse;
                vm.detailList = r.detailList;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        course_addOrUpdate:function (title,opt,e) {
            var html = '<select class="form-control cateOpt" >';
            var idx = e.target.dataset.idx;
            console.log("序号：" + idx)
            var detail = {};

            if(opt === 'update'){
                detail = vm.detailList[idx]
            }

            var that = this;
            $.get(baseURL + "sys/setcourse/cateList", function(r){
                if(r.code === 0){
                    $.each(r.typeList,function (index,item) {
                        if(opt === 'update'){
                            var typeid = $(that).closest('tr').find('td').eq(0).attr('data-typeid');
                            console.log(typeid);
                            if(typeid === item.id){
                                html += '<option value="' + item.id + '" selected>'+ item.title +'</option>';
                            }else{
                                html += '<option value="' + item.id + '" >'+ item.title +'</option>';
                            }

                        }else{
                            html += '<option value="' + item.id + '" >'+ item.title +'</option>';
                        }

                    })

                    if(opt === 'update'){
                        html += '</select>' +
                            '<input type="number" class="form-control courseNum" value="'+ detail.num +'" style="margin-top: 10px;" placeholder="课时数量"/>';
                    }else{
                        html += '</select>' +
                            '<input type="number" class="form-control courseNum" style="margin-top: 10px;" placeholder="课时数量"/>';
                    }

                    layer.confirm(html,{title:title},function () {
                        var num = $(".courseNum").val();
                        var typeid = $(".cateOpt").val();
                        var title = $(".cateOpt").find("option:selected").text();
                        if(num > 0 && typeid > 0){

                            detail.typeId = typeid;
                            detail.num = num;
                            detail.title = title;
                            if(opt === 'update'){
                                detail.setCourseId = vm.setCourse.id;
                                var idx = e.target.dataset.idx;
                                vm.detailList[idx] = detail;
                            }else{
                                vm.detailList.push(detail)
                            }

                            layer.close(layer.index);
                        }else{
                            layer.msg("请将课程填完整",{icon: 7})
                        }
                    })
                }
            });
        },
        delCourseItem:function (e) {
            var idx = e.target.dataset.idx;
            var id = e.target.dataset.itemid;
            var len = vm.detailList.length;

            if(len === 1){
                layer.msg("至少需要留下一个课程",{icon: 7})
                return;
            }

            layer.confirm("确定删除此课程？",function () {
                if(id != undefined && id > 0){

                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/setcourse/deleteItem",
                        contentType: "application/json",
                        data: JSON.stringify(id),
                        success: function(r){
                            if(r.code === 0){
                                layer.msg("操作成功", {icon: 1});
                                vm.detailList.splice(idx,1)
                                layer.close(layer.index);
                            }else{
                                layer.alert(r.msg);
                            }
                        }
                    });
                }else{
                    vm.detailList.splice(idx,1)
                    layer.close(layer.index);
                }
            })
        }
	}
});