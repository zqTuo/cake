$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sendtime/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '派送开始时间点', name: 'startTime', index: 'start_time', width: 80 },
			{ label: '派送时间点', name: 'endTime', index: 'end_time', width: 80 },
			{ label: '最大预约订单数', name: 'maxOrder', index: 'max_order', width: 80 }, 			
			{ label: '类别', name: 'type', index: 'type', width: 80 ,formatter:function (cellValue, options, rowObject) {
                    if(cellValue === 0){
                        return '配送选择时间'
                    }else{
                        return '课程选择时间'
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

layui.use('laydate', function(){
    var laydate = layui.laydate;

    //时间有效范围设定在: 上午九点半到下午五点半
    var start = laydate.render({
        elem: '#datetimeStart'
        ,type: 'time'
        ,format: 'HH:mm'
        ,min: '06:00:00'
        ,max: '23:59:00'
        ,choose: function(datas){ //选择日期完毕的回调
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    });

    //时间有效范围设定在: 上午九点半到下午五点半
    var end = laydate.render({
        elem: '#datetimeEnd'
        ,type: 'time'
        ,format: 'HH:mm'
        ,min: '06:00:00'
        ,max: '23:59:00'
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sendTime: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sendTime = {};
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
                var url = vm.sendTime.id == null ? "sys/sendtime/save" : "sys/sendtime/update";

                vm.sendTime.startTime = $("#datetimeStart").val();
                vm.sendTime.endTime = $("#datetimeEnd").val();

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sendTime),
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
                        url: baseURL + "sys/sendtime/delete",
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
			$.get(baseURL + "sys/sendtime/info/"+id, function(r){
                vm.sendTime = r.sendTime;
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