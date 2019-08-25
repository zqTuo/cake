$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/product/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '商品名称', name: 'productName', index: 'product_name', width: 80 }, 			
			{ label: '商品类型ID', name: 'productCateid', index: 'product_cateid', width: 80 }, 			
			{ label: '商品售价', name: 'productPrice', index: 'product_price', width: 80 }, 			
			{ label: '商品规格价格区间', name: 'productPriceRegion', index: 'product_price_region', width: 80 }, 			
			{ label: '商品原价', name: 'productOrigin', index: 'product_origin', width: 80 }, 			
			{ label: '商品SKU，主要是为了匹配美团套餐', name: 'productSku', index: 'product_sku', width: 80 }, 			
			{ label: '商品主图', name: 'productImg', index: 'product_img', width: 80 }, 			
			{ label: '商品视频', name: 'productVideo', index: 'product_video', width: 80 }, 			
			{ label: '商品副图', name: 'productBanner', index: 'product_banner', width: 80 }, 			
			{ label: '商品描述', name: 'productDesc', index: 'product_desc', width: 80 }, 			
			{ label: '商品详情HTML代码', name: 'productInfo', index: 'product_info', width: 80 }, 			
			{ label: '商品状态 0：下架 1：上架', name: 'productFlag', index: 'product_flag', width: 80 }, 			
			{ label: '热销标记(展示在首页) 0：不推荐 1：推荐', name: 'productHot', index: 'product_hot', width: 80 }, 			
			{ label: '加购标记 0：不设为加购 1：设为加购', name: 'productExtra', index: 'product_extra', width: 80 }, 			
			{ label: '所属门店', name: 'shopId', index: 'shop_id', width: 80 }, 			
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
		product: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.product = {};
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
                var url = vm.product.id == null ? "sys/product/save" : "sys/product/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.product),
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
                        url: baseURL + "sys/product/delete",
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
			$.get(baseURL + "sys/product/info/"+id, function(r){
                vm.product = r.product;
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