$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/wxuser/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户昵称', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: '用户头像', name: 'userHead', index: 'user_head', width: 80 ,formatter:function (cellValue) {
                    return '<img class="img-thumbnail" height="80px" width="80px" src="'+cellValue+'">';
                }},
			{ label: '会员等级', name: 'userMember', index: 'user_member', width: 80,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>会员用户</span>";
                    }else{
                        return "<span class='label label-primary radius'>普通用户</span>";
                    }
                } },
			{ label: '上次登录IP地址', name: 'userLastip', index: 'user_lastIp', width: 80 },
			{ label: '上次登录时间', name: 'userLastlogintime', index: 'user_lastLoginTime', width: 80 }, 			
			{ label: '手机号', name: 'userPhone', index: 'user_phone', width: 80 }, 			
			{ label: '用户状态', name: 'userState', index: 'user_state', width: 80 ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>正常使用</span>";
                    }else{
                        return "<span class='label label-danger radius'>已禁用</span>";
                    }
                } },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }
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
		wxuser: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wxuser = {};
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
                var url = vm.wxuser.id == null ? "sys/wxuser/save" : "sys/wxuser/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.wxuser),
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
                        url: baseURL + "sys/wxuser/delete",
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
			$.get(baseURL + "sys/wxuser/info/"+id, function(r){
                vm.wxuser = r.wxuser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});