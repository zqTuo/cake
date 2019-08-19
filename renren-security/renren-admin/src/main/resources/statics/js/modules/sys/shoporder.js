$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/shoporder/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '订单编号', name: 'orderNo', index: 'order_no', width: 80 }, 			
			{ label: '用户ID', name: 'userId', index: 'user_id', width: 80 }, 			
			{ label: '订单支付金额', name: 'orderPrice', index: 'order_price', width: 80 }, 			
			{ label: '订单优惠金额', name: 'orderDiscount', index: 'order_discount', width: 80 }, 			
			{ label: '订单优惠类型 0：无优惠 1：优惠券', name: 'orderDiscountType', index: 'order_discount_type', width: 80 }, 			
			{ label: '用户的优惠券ID', name: 'couponUserId', index: 'coupon_user_id', width: 80 }, 			
			{ label: '订单状态 -1：未支付 0：已取消 1：已支付 2：已发货 3：（已确认）已签收', name: 'orderState', index: 'order_state', width: 80 }, 			
			{ label: '订单来源 0：蛋糕订购 1：预约烘焙课程 2:购买会员', name: 'orderSourceType', index: 'order_source_type', width: 80 }, 			
			{ label: '备注', name: 'orderRemark', index: 'order_remark', width: 80 }, 			
			{ label: '配送省份', name: 'addrPro', index: 'addr_pro', width: 80 }, 			
			{ label: '配送城市', name: 'addrCity', index: 'addr_city', width: 80 }, 			
			{ label: '配送区域', name: 'addrDis', index: 'addr_dis', width: 80 }, 			
			{ label: '配送详细地址', name: 'addrDetail', index: 'addr_detail', width: 80 }, 			
			{ label: '收货人', name: 'addrReceiver', index: 'addr_receiver', width: 80 }, 			
			{ label: '配送方式 0：送货上门 1：门店自取', name: 'sendType', index: 'send_type', width: 80 }, 			
			{ label: '联系方式', name: 'addrPhone', index: 'addr_phone', width: 80 }, 			
			{ label: '派送时间', name: 'sendTime', index: 'send_time', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '支付时间', name: 'payTime', index: 'pay_time', width: 80 }, 			
			{ label: '支付方式 0：微信支付', name: 'payType', index: 'pay_type', width: 80 }, 			
			{ label: '门店ID', name: 'shopId', index: 'shop_id', width: 80 }, 			
			{ label: '取消时间', name: 'cancelTime', index: 'cancel_time', width: 80 }, 			
			{ label: '签收时间', name: 'finishTime', index: 'finish_time', width: 80 }, 			
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