$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/productcategory/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '分类名称', name: 'categoryName', index: 'category_name', width: 80 }, 			
			{ label: '父类ID', name: 'categoryParentid', index: 'category_parentid', width: 80 }, 			
			{ label: '展示位置', name: 'showFlag', index: 'show_flag', width: 80  ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "蛋糕首页";
                    }else if(cellValue === 2){
                        return "课程首页";
                    }
                }},
			{ label: '状态', name: 'categoryFlag', index: 'category_flag', width: 80   ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>已启用</span>";
                    }else{
                        return "<span class='label label-warning radius'>已禁用</span>";
                    }
                }},
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '修改时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '修改管理员', name: 'updateBy', index: 'update_by', width: 80 }			
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
		productCategory: {},
        cateList:[]
    },
    mounted: function(){
        var that = this;
        that.getCateList();
    },
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.productCategory = {};
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
                var url = vm.productCategory.id == null ? "sys/productcategory/save" : "sys/productcategory/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.productCategory),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();
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
                        url: baseURL + "sys/productcategory/delete",
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
			$.get(baseURL + "sys/productcategory/info/"+id, function(r){
                vm.productCategory = r.productCategory;
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
            $.get(baseURL + "sys/productcategory/findAll", function(r){
                vm.cateList = r.arrayData;
                var cate = {};
                cate.id = 0;
                cate.lev = 0;
                cate.name = '顶级分类';
                vm.cateList.unshift(cate)
            });
        }
	}
});