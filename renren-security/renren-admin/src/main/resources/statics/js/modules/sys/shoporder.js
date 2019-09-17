$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/shoporder/list',
        datatype: "json",
        colModel: [			
			{ label: '订单编号', name: 'orderNo', index: 'order_no', width: 130 },
			{ label: '用户昵称', name: 'userName', index: 'user_name', width: 80 },
			{ label: '支付金额', name: 'orderPrice', index: 'order_price', width: 70 },
			{ label: '优惠金额', name: 'orderDiscount', index: 'order_discount', width: 70 },
			{ label: '优惠类型', name: 'orderDiscountType', index: 'order_discount_type', width: 70 ,formatter:function (cellValue, options, rowObject) {
                    if(cellValue === 0){
                        return "无优惠";
                    }else if(cellValue === 1){
                        return "优惠券";
                    }else if(cellValue === 2){
                        return "美团券";
                    }else{
                        return "优惠券+美团券";
                    }
                }},
			{ label: '订单状态', name: 'orderState', index: 'order_state', width: 80 ,formatter:function (cellValue, options, rowObject) {
                    if(cellValue === -1){
                        return "未支付";
                    }else if(cellValue === 0){
                        return "已取消";
                    }else if(cellValue === 1){
                        return "<span class='label label-warning radius'>已支付</span>";
                    }else if(cellValue === 2){
                        return "<span class='label label-primary radius'>已发货</span>";
                    }else{
                        return "<span class='label label-success radius'>已确认</span>";
                    }
                }},
			{ label: '订单来源', name: 'orderSourceType', index: 'order_source_type', width: 90 ,formatter:function (cellValue, options, rowObject) {
                    if(cellValue === 0){
                        return "蛋糕订购";
                    }else if(cellValue === 1){
                        return "预约单次体验课程";
                    }else if(cellValue === 2){
                        return "购买套餐课程";
                    }else{
                        return "套餐课程";
                    }
                }},
			{ label: '生日牌', name: 'orderRemark', index: 'order_remark', width: 80 },
			{ label: '收货(预约)人', name: 'addrReceiver', index: 'addr_receiver', width: 80 },
			{ label: '配送方式', name: 'sendType', index: 'send_type', width: 70 ,formatter:function (cellValue, options, rowObject) {
                    if(cellValue === 0){
                        return "<span class='label label-warning radius'>送货上门</span>";
                    }else{
                        return "<span class='label label-primary radius'>门店自取</span>";
                    }
                }},
			{ label: '联系方式', name: 'addrPhone', index: 'addr_phone', width: 80 }, 			
			{ label: '时间', name: 'sendTime', index: 'send_time', width: 80 ,formatter:function (cellValue, options, rowObject) {
			    var html = '下单时间：' + rowObject.createTime;
                    if(rowObject.orderSourceType === 0){
                        if(rowObject.sendType === 0){
                            html += '<br>配送时间：' + rowObject.sendDate
                        }else {
                            html += '<br>取货时间：' + rowObject.sendDate
                        }
                    }else if(rowObject.orderSourceType === 1){
                        html += '<br>上课时间：' + rowObject.sendDate
                    }else if(rowObject.orderSourceType === 3){
                        html += '<br>上课时间：' + rowObject.sendDate
                    }

                    if(rowObject.orderState === 1){
                        html += '<br>付款时间：' + rowObject.payTime
                    }

                    return html;

                }},
			{ label: '下单门店', name: 'shopName', index: 'shop_name', width: 80 }
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
        postData:{
            orderState: $("#cateOptions").val()
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});


function JumpByEnter() {
    var lKeyCode = (navigator.appname == "Netscape") ? event.which : window.event.keyCode; //event.keyCode按的建的代码，13表示回车
    if (lKeyCode == 13) {
        var searchVal = encodeURIComponent($("#orderSearch").val());
        var postJson = {searchVal:searchVal};

        //传入查询条件参数
        $("#jqGrid").jqGrid("setGridParam",{postData:postJson});
        //每次提出新的查询都转到第一页
        $("#jqGrid").jqGrid("setGridParam",{page:1});
        //提交post并刷新表格
        $("#jqGrid").jqGrid("setGridParam",{url:baseURL + 'sys/shoporder/list'}).trigger("reloadGrid");
    }
}

$("#orderSearch").click(function () {
    var searchVal = encodeURIComponent($("#orderSearch").val());
    var postJson = {searchVal:searchVal};

    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam",{postData:postJson});
    //每次提出新的查询都转到第一页
    $("#jqGrid").jqGrid("setGridParam",{page:1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam",{url:baseURL + 'sys/shoporder/list'}).trigger("reloadGrid");
})

$(document).on('change','#cateOptions',function () {
    var postJson = {orderState:$("#cateOptions").val()};

    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam",{postData:postJson});
    //每次提出新的查询都转到第一页
    $("#jqGrid").jqGrid("setGridParam",{page:1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam",{url:baseURL + 'sys/shoporder/list'}).trigger("reloadGrid");
})

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		shopOrder: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.shopOrder = {};
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
                var url = vm.shopOrder.id == null ? "sys/shoporder/save" : "sys/shoporder/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.shopOrder),
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
                        url: baseURL + "sys/shoporder/delete",
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
			$.get(baseURL + "sys/shoporder/info/"+id, function(r){
                vm.shopOrder = r.shopOrder;
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

$("#export").on('click', function () {
    var ids = getSelectedRows();

    var date = $.trim($(".datetimeStart").val());
    var dateEnd = $.trim($(".datetimeEnd").val());

    if(ids != null){
        layer.confirm("确定导出选中数据?", function (index) {
            window.location.href = "/cake-admin/sys/upload/downloadSmallOrder?date=" + date + "&dateEnd=" + dateEnd + "&ids=" + ids;
            layer.closeAll('dialog');
        });
    }else{
        if (date === "" || dateEnd === "") {
            layer.msg("请选择导出时段", {icon: 7, time: 1500}) //icon:  1:成功  2：失败 3:疑问 4：锁 5：红色哭脸 6：绿色笑脸 7以上：橙色感叹号
        } else {
            layer.confirm("确定导出该时段数据?", function (index) {
                window.location.href = "/cake-admin/sys/upload/downloadSmallOrder?date=" + date + "&dateEnd=" + dateEnd;
                layer.closeAll('dialog');
            });
        }
    }
})

$("#exportSmall").on('click', function () {
    var ids = getSelectedRows();
    var date = $.trim($(".datetimeStart").val());
    var dateEnd = $.trim($(".datetimeEnd").val());
    if(ids != null){
        layer.confirm("确定导出选中数据?", function (index) {
            window.location.href = "/cake-admin/sys/upload/downloadOrder?date=" + date + "&dateEnd=" + dateEnd + "&ids=" + ids;
            layer.closeAll('dialog');
        });
    }else{
        if (date === "" || dateEnd === "") {
            layer.msg("请选择导出时段", {icon: 7, time: 1500}) //icon:  1:成功  2：失败 3:疑问 4：锁 5：红色哭脸 6：绿色笑脸 7以上：橙色感叹号
        } else {
            layer.confirm("确定导出该时段数据?", function (index) {
                window.location.href = "/cake-admin/sys/upload/downloadOrder?date=" + date + "&dateEnd=" + dateEnd;
                layer.closeAll('dialog');
            });
        }
    }
})

/*订单-时间改变*/
var o_startdate = $.trim($(".datetimeStart").val());
var o_dateEnd = $(".datetimeEnd").val();
function dateChange() {
    var date = $.trim($(".datetimeStart").val());
    var dateEnd = $.trim($(".datetimeEnd").val());
    if (o_startdate != date) {
        o_startdate = date;

        var postJson = {date:date,dateEnd:dateEnd};
        //传入查询条件参数
        $("#jqGrid").jqGrid("setGridParam",{postData:postJson});
        //每次提出新的查询都转到第一页
        $("#jqGrid").jqGrid("setGridParam",{page:1});
        //提交post并刷新表格
        $("#jqGrid").jqGrid("setGridParam",{url:baseURL + 'sys/shoporder/list'}).trigger("reloadGrid");
    }
    if (o_dateEnd != dateEnd) {
        o_dateEnd = dateEnd;
        var postJson = {date:date,dateEnd:dateEnd};
        //传入查询条件参数
        $("#jqGrid").jqGrid("setGridParam",{postData:postJson});
        //每次提出新的查询都转到第一页
        $("#jqGrid").jqGrid("setGridParam",{page:1});
        //提交post并刷新表格
        $("#jqGrid").jqGrid("setGridParam",{url:baseURL + 'sys/shoporder/list'}).trigger("reloadGrid");
    }
}

