$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/custom/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '微信昵称', name: 'userName', index: 'user_name', width: 80 }, 			
			{ label: 'OPENID', name: 'userOpenId', index: 'user_open_id', width: 80 }, 			
			{ label: '微信头像', name: 'userHead', index: 'user_head', width: 80 }, 			
			{ label: '完整客服帐号，格式为：帐号前缀@公众号微信号', name: 'kfAccount', index: 'kf_account', width: 80 }, 			
			{ label: '客服昵称', name: 'kfNick', index: 'kf_nick', width: 80 }, 			
			{ label: '客服编号', name: 'kfAccount', index: 'kf_account', width: 80 },
			{ label: '客服头像', name: 'kfHeadImgUrl', index: 'kf_head_img_url', width: 80 }, 			
			{ label: '如果客服帐号已绑定了客服人员微信号， 则此处显示微信号', name: 'kfWx', index: 'kf_wx', width: 80 }, 			
			{ label: '客服状态 0：已停用 1：已启用', name: 'state', index: 'state', width: 80 }, 			
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
		custom: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.custom = {};
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
                var url = vm.custom.id == null ? "sys/custom/save" : "sys/custom/update";

                vm.custom.kfHeadImgUrl = $("#imgProDetail100").attr("src");

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.custom),
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
                        url: baseURL + "sys/custom/delete",
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
			$.get(baseURL + "sys/custom/info/"+id, function(r){
                vm.custom = r.custom;
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