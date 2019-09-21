var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		baseData: {}
	},
	mounted: function(){
		var that = this;
		that.getInfo(0);
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.baseData = {};
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
                var url = vm.baseData.id == null ? "sys/basedata/save" : "sys/basedata/update";

                var cake = {};
				cake.title = vm.baseData.cake.title;
				cake.icon = $("#imgProDetail1").attr("src");

                var tea = {};
				tea.title = vm.baseData.tea.title;
				tea.icon = $("#imgProDetail2").attr("src");

                var hot = {};
				hot.title = vm.baseData.hot.title;
				hot.icon = $("#imgProDetail3").attr("src");

                var meituan = {};
				meituan.title = vm.baseData.meituan.title;
				meituan.icon = $("#imgProDetail4").attr("src");

				var id = vm.baseData.id;
				vm.baseData = {};
                var content = {};
                content.cake = cake;
                content.tea = tea;
                content.hot = hot;
                content.meituan = meituan;

                vm.baseData.content = JSON.stringify(content)
				vm.baseData.sourceType = 0;
                vm.baseData.id = id;

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.baseData),
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
                        url: baseURL + "sys/basedata/delete",
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
		getInfo: function(sourceType){
			$.get(baseURL + "sys/basedata/info/"+sourceType, function(r){
                vm.baseData = r.baseData;
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