var ue;

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/product/list',
        datatype: "json",
        colModel: [			
            { label: '商品主图', name: 'productImg', index: 'product_img', width: 80 ,formatter:function (cellValue, options, rowObject) {
                    return '<img class="img-thumbnail" width="80px" src="/images/'+cellValue+'" alt="商品图片">'
                }},
            { label: '商品名称', name: 'productName', index: 'product_name', width: 80},
            { label: '商品类型', name: 'categoryName', index: 'category_name', width: 80 },
            { label: '商品售价', name: 'productPrice', index: 'product_price', width: 80 ,formatter:function (cellValue) {
                    return '￥' + cellValue;
                }},
            { label: '商品原价', name: 'productOrigin', index: 'product_origin', width: 80 ,formatter:function (cellValue) {
                    return '￥' + cellValue;
                }},
            { label: '价格区间', name: 'productPriceRegion', index: 'product_price_region', width: 80 },
            { label: '商品SKU', name: 'productSku', index: 'product_sku', width: 80 },
			{ label: '商品状态', name: 'productFlag', index: 'product_flag', width: 80 ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>已上架</span>";
                    }else{
                        return "<span class='label label-danger radius'>已下架</span>";
                    }
                }},
			{ label: '热销标记', name: 'productHot', index: 'product_hot', width: 80  ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>已推荐</span>";
                    }else{
                        return "未推荐";
                    }
                }},
			{ label: '加购标记', name: 'productExtra', index: 'product_extra', width: 80  ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>已添加</span>";
                    }else{
                        return "未添加";
                    }
                }},
			{ label: '所属门店', name: 'shopName', index: 'shop_name', width: 80 }
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
		product: {},
        cateList:[],
        shopList:[],
        bannerArr:[],
        productDetailList:[]
	},
    // created: function(){
    //     //如果没有这句代码，select中初始化会是空白的，默认选中就无法实现
    //     if(JSON.stringify(this.product) === '{}'){
    //         this.product.productCateid = -1;
    //     }else{
    //         this.product = this.cateList[0].id;
    //     }
    // },
    mounted: function(){
        var that = this;
        that.getCateList();
        that.getShopList();
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

                var content = "";
                ue.ready(function () {
                    content = ue.getContent();
                });

                vm.product.productInfo = content;

                var banner_imgs = [];
                var banner_imgsObj = $(".banner img[class='up-img']");
                var banner_imgsLen = banner_imgsObj.length;
                for(var i=0;i<banner_imgsLen;i++){
                    var bannersPath = banner_imgsObj[i].src;
                    banner_imgs.push(bannersPath);
                }
                vm.product.productBanner = banner_imgs.toString();

                vm.product.productImg = $("#imgProDetail100").attr("src");

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.product),
                    headers: {
                        auth: "ueditor"
                    },
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
                vm.bannerArr = r.bannerArr;

                var content = r.product.productInfo;
                ue.ready(function () {
                    ue.setContent(content);
                });
            });
		},
        getCateList: function (event) {
            $.get(baseURL + "sys/productcategory/findAll", function(r){
                vm.cateList = r.arrayData;
            });
        },
        getShopList: function(e){
            $.get(baseURL + "sys/shop/all", function(r){
                vm.shopList = r.arrayData;
            });
        },
        checkType: function (e) {
            var lev = e.target.dataset.lev;
            console.log(e)
            console.log("改变事件" + lev)
            if(lev === 0){
                $(this).val(-1);
                layer.msg("不能选择一级目录！",{icon:7,time:3000})
            }
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        prodetail_addOrUpdate:function (title, url, width, height) {
            var idx = $('.Xcontent34').attr('data-idx')
            url = idx === undefined ? url : url + "?id=" + idx;
            layer_show(title,url,width,height);
        },
	}
});



