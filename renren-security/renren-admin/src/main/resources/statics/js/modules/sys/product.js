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
                    }else if(cellValue === 2){
                        return "<span class='label label-success radius'>已上架但不显示</span>";
                    }else{
                        return "<span class='label label-danger radius'>已下架</span>";
                    }
                }},
			{ label: '热销标记', name: 'productHotId', index: 'product_hot_id', width: 80  ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>已展示</span>";
                    }else{
                        return "不展示";
                    }
                }},
			{ label: '加购标记', name: 'productExtra', index: 'product_extra', width: 80  ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>已添加</span>";
                    }else{
                        return "未添加";
                    }
                }},
			{ label: '热门标记', name: 'productHotFlag', index: 'product_hot_flag', width: 80  ,formatter:function (cellValue) {
                    if(cellValue === 1){
                        return "<span class='label label-success radius'>已推荐</span>";
                    }else{
                        return "未推荐";
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
        showDetail:false,
		title: null,
		product: {},
        cateList:[],
        hotList:[],
        shopList:[],
        bannerArr:[],
        tag: null,
        productDetail: {},
        sizeList:[],
        tasteList:[],
        productDetailList:[]
	},
    mounted: function(){
        var that = this;
        that.getCateList();
        that.getShopList();
        that.getHotList();

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
                if(vm.product.productImg === "/cake-admin/statics/img/a11.png"){
                    layer.alert("请上传商品主图！");
                    return;
                }

                var data ={
                    product:vm.product,
                    detailList:vm.productDetailList,
                    sizeList:vm.sizeList,
                    tasteList:vm.tasteList
                };

                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    headers: {
                        auth: "ueditor"
                    },
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
                vm.showDetail = true;
			    vm.product = r.product;
                vm.bannerArr = r.bannerArr;
                vm.productDetailList = r.productDetailEntityList;
                vm.sizeList = r.sizeList;
                vm.tasteList = r.tasteList;
                vm.productDetail = vm.productDetailList[0];
                console.log("获取商品详情：" + vm.productDetailList)

                $(".Xcontent06").find('img').attr('src',vm.productDetail.detailCover);
                $(".Xcontent14").find('p').text(vm.productDetail.detailName);
                $(".Xcontent19").find('span').text(vm.productDetail.detailPrice);
                $(".pdsize").text(vm.productDetail.detailSize);
                $(".pdtaste").text(vm.productDetail.detailTaste);
                $(".pdsku").text(vm.productDetail.detailSku);
                $(".Xcontent").attr('style','display:block');
                var len = $(".Xcontent_R").length;
                $('.Xcontent34').attr('data-idx',len)
                $('.Xcontent35').attr('data-idx',len)

                var detailname = $(".Xcontent_R").eq(0).attr('data-detailname');
                var detailprice = $(".Xcontent_R").eq(0).attr('data-detailprice');
                var detailsize = $(".Xcontent_R").eq(0).attr('data-detailsize');
                var detailsku = $(".Xcontent_R").eq(0).attr('data-detailsku');
                var detailtaste = $(".Xcontent_R").eq(0).attr('data-detailtaste');
                var detailCover = $(".Xcontent_R").eq(0).find('img').attr('src');

                $(".Xcontent06 img").attr('src',detailCover);
                $(".Xcontent06").find('img').attr('src',detailCover);
                $(".Xcontent14").find('p').text(detailname);
                $(".Xcontent19").find('span').text(detailprice);
                $(".pdsize").text(detailsize);
                $(".pdtaste").text(detailtaste);
                $(".pdsku").text(detailsku);
                $('.Xcontent35').attr('data-idx',vm.productDetailList.length - 1);
                $('.Xcontent34').attr('data-idx',vm.productDetailList.length - 1);

                vm.productDetail = {};

                var content = r.product.productInfo;
                ue.ready(function () {
                    ue.setContent(content);
                });
            });
		},
        getCateList: function (event) {
            $.get(baseURL + "sys/productcategory/findAll?showFlag=1", function(r){
                vm.cateList = r.arrayData;
                var cate = {};
                cate.id = 0;
                cate.lev = 0;
                cate.name = '请选择类型';
                vm.cateList.unshift(cate)
            });
        },
        getHotList: function (event) {
            $.get(baseURL + "sys/hotcolumn/findAll", function(r){
                vm.hotList = r.hotList;
                var cate = {};
                cate.id = 0;
                cate.hotTitle = '---- 不推荐到首页 ----';
                vm.hotList.unshift(cate)
            });
        },
        getShopList: function(e){
            $.get(baseURL + "sys/shop/all", function(r){
                vm.shopList = r.arrayData;
            });
        },
        checkType: function (e) {
		    var idx = e.target.options.selectedIndex;
		    if(idx > 0){
                var depth = vm.cateList[idx].lev;
                if(depth === 0){
                    $(this).val(-1);
                    $("#cateType option[value='-1']").prop("selected",true);
                    layer.msg("不能选择一级目录！",{icon:7,time:3000})
                }
            }

        },
		reload: function (event) {
			vm.showList = true;

            vm.productDetail = {};
            vm.tasteList = [];
            vm.sizeList = [];
            vm.productDetailList = [];

			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        getDataList:function () {
            $.get(baseURL + "sys/productdetail/sizeList", function(r){
                vm.sizeList = r.sizeList;
            });
            $.get(baseURL + "sys/productdetail/tasteList", function(r){
                vm.tasteList = r.tasteList;
            });
        },
        getData:function (e) {
            var value = $(this).val();
            $(this).closest('input').val(value)
        },
        prodetail_addOrUpdate:function (title, width, height,opt) {
            vm.productDetail = {};

            var detailCover = "/cake-admin/statics/img/a11.png";
		    var detailName = "";
		    var detailPrice = "";
		    var detailSize = "";
		    var detailTaste = "";
		    var detailSku = "";
		    var id = $('.Xcontent08').find('.selected').attr('data-id');

            var idx = $('.Xcontent34').attr('data-idx')
            if(opt === 'update'){
                detailCover = $(".Xcontent06").find('img').attr('src');
                detailName = $(".Xcontent_R").eq(idx).attr('data-detailname');
                detailSize = $(".Xcontent_R").eq(idx).attr('data-detailsize');
                detailTaste = $(".Xcontent_R").eq(idx).attr('data-detailtaste');
                detailPrice = $(".Xcontent_R").eq(idx).attr('data-detailprice');
                detailSku = $(".Xcontent_R").eq(idx).attr('data-detailsku');
            }

            var detailHtml = '<form class="form-horizontal">' +
                '            <div class="form-group">' +
                '                <div class="col-sm-2 control-label">商品规格图片</div>' +
                '                <div class="col-sm-10">' +
                '                    <div class="proDetailPic" style="width:230px;">' +
                '                        <section class=" img-section">' +
                '                            <div class="z_photo upimg-div clear proDetail" style="border: 2px dashed #E7E6E6; width: 230px;height: 220px;padding: 18px;">' +
                '                                <div id="preview1" style="width: 190px;height: 180px;">' +
                '                                    <img id="imgProDetail1" border="0" src="'+detailCover+'" width="190" height="180" onclick="$(\'#previewImg1\').click();">' +
                '                                </div>' +
                '                                <input type="file" onchange="onePicUpLoad(this,1,190,180)" style="display: none;" id="previewImg1">' +
                '                            </div>' +
                '                        </section>' +
                '                    </div>' +
                '                </div>' +
                '            </div>' +
                '            <div class="form-group">' +
                '                <div class="col-sm-2 control-label">商品规格名称</div>' +
                '                <div class="col-sm-10">' +
                '                    <input type="text" class="form-control" id="detailName" value="'+detailName+'" placeholder="商品规格名称"/>' +
                '                </div>' +
                '            </div>' +
                '            <div class="form-group">' +
                '                <div class="col-sm-2 control-label">商品规格SKU</div>' +
                '                <div class="col-sm-10">' +
                '                    <input type="text" class="form-control" id="detailSku" value="'+detailSku+'" placeholder="商品规格SKU，匹配美团/大众点评商品，设置商品名称#+sku+#结束,其中sku如果有多个以英文逗号隔开"/>' +
                '                </div>' +
                '            </div>' +
                '            <div class="form-group">' +
                '                <div class="col-sm-2 control-label">商品规格价格</div>' +
                '                <div class="col-sm-10">' +
                '                    <input type="text" class="form-control" id="detailPrice" value="'+detailPrice+'" placeholder="商品规格价格"/>' +
                '                </div>' +
                '            </div>' +
                '            <div class="form-group">' +
                '                <div class="col-sm-2 control-label">商品尺寸</div>' +
                '                <div class="col-sm-10">' +
                '                    <input type="text" class="form-control" id="detailSize" value="'+detailSize+'" placeholder="商品尺寸" style="width: 120px;float: left;" />' +
                '' +
                '                    <select class="form-control" onchange="getData()" style="width: 170px;float: right;">' +
                '                        <option value="-1" selected >查看已有尺寸</option>';

                for (var i = 0; i < vm.sizeList.length; i++) {
                    detailHtml += '<option value="'+ vm.sizeList[i] +'" >'+ vm.sizeList[i] +'</option>';
                }

                detailHtml += '     </select>' +
                '                </div>' +
                '            </div>' +
                '            <div class="form-group">' +
                '                <div class="col-sm-2 control-label">商品口味</div>' +
                '                <div class="col-sm-10">' +
                '                    <input type="text" class="form-control" id="detailTaste" value="'+detailTaste+'" placeholder="商品口味" style="width: 120px;float: left;" />' +
                '' +
                '                    <select class="form-control" onchange="getData()" style="width: 170px;float: right;">' +
                '                        <option value="-1" selected >查看已有口味</option>';
                for (var j = 0; j < vm.tasteList.length; j++) {
                    detailHtml += '<option value="'+ vm.tasteList[j] +'" >'+ vm.tasteList[j] +'</option>';
                }

                detailHtml += '      </select>' +
                '                </div>' +
                '            </div>' +
                '        </form>';

                var that = this;

            layer.confirm(detailHtml,{title:title,area:[width,height]},function () {

                detailCover = $("#imgProDetail1").attr("src");
                detailName = $("#detailName").val();
                detailPrice = $("#detailPrice").val();
                detailSize = $("#detailSize").val();
                detailTaste = $("#detailTaste").val();
                detailSku = $("#detailSku").val();

                if(detailCover == "/cake-admin/statics/img/a11.png" || detailName=="" ||  detailPrice==""
                    || detailSize=="" || detailTaste==""){
                    alert("产品属性存在空值，请认真填写！")
                    return;
                }

                vm.productDetail.detailCover = detailCover
                vm.productDetail.detailName = detailName
                vm.productDetail.detailPrice = detailPrice
                vm.productDetail.detailSize = detailSize
                vm.productDetail.detailTaste = detailTaste
                vm.productDetail.detailSku = detailSku
                vm.productDetail.id = id
                console.log(vm.productDetail);

                if(opt === 'update'){
                    that.updateDetail(idx);
                }else{
                    that.addDetail();
                }


                vm.showDetail = true;

                layer.close(layer.index);
            })
        },
        addDetail: function (event) {
            vm.productDetailList.push(vm.productDetail);

            if(vm.tasteList.indexOf(vm.productDetail.detailTaste) <= -1){
                vm.tasteList.push(vm.productDetail.detailTaste)
            }

            if(vm.sizeList.indexOf(vm.productDetail.detailSize) <= -1){
                vm.sizeList.push(vm.productDetail.detailSize)
            }

            //右侧规格详情设置数据
            $(".Xcontent06").find('img').attr('src',vm.productDetail.detailCover);
            // $(".Xcontent08").find('.Xcontent_R').removeClass('selected');
            // $(".Xcontent08").find('.Xcontent_R').eq(-1).addClass('selected');
            $(".Xcontent14").find('p').text(vm.productDetail.detailName);
            $(".Xcontent19").find('span').text(vm.productDetail.detailPrice);
            $(".pdsize").text(vm.productDetail.detailSize);
            $(".pdtaste").text(vm.productDetail.detailTaste);
            $(".pdsku").text(vm.productDetail.detailSku);
            $(".Xcontent").attr('style','display:block');
            var len = $(".Xcontent_R").length;
            $('.Xcontent34').attr('data-idx',len)
            $('.Xcontent35').attr('data-idx',len)

            vm.productDetail = {};
        },
        updateDetail: function (idx) {
		    console.log(vm.productDetailList);
		    console.log("正在修改：" + idx);

		    vm.productDetailList[idx] = vm.productDetail;

            if(vm.tasteList.indexOf(vm.productDetail.detailTaste) <= -1){
                vm.tasteList.push(vm.productDetail.detailTaste)
            }

            if(vm.sizeList.indexOf(vm.productDetail.detailSize) <= -1){
                vm.sizeList.push(vm.productDetail.detailSize)
            }

            $(".Xcontent06").find('img').attr('src',vm.productDetail.detailCover);
            $(".Xcontent14").find('p').text(vm.productDetail.detailName);
            $(".Xcontent19").find('span').text(vm.productDetail.detailPrice);
            $(".pdsize").text(vm.productDetail.detailSize);
            $(".pdtaste").text(vm.productDetail.detailTaste);
            $(".pdsku").text(vm.productDetail.detailSku);


            $(".Xcontent_R").eq(idx).attr('data-detailname',vm.productDetail.detailName);
            $(".Xcontent_R").eq(idx).attr('data-detailprice',vm.productDetail.detailPrice);
            $(".Xcontent_R").eq(idx).attr('data-detailsize',vm.productDetail.detailSize);
            $(".Xcontent_R").eq(idx).attr('data-detailtaste',vm.productDetail.detailTaste);
            $(".Xcontent_R").eq(idx).attr('data-detailsku',vm.productDetail.detailSku);
            $(".Xcontent_R").eq(idx).find('img').attr('src',vm.productDetail.detailCover);
            $(".Xcontent06").find('img').attr('src',vm.productDetail.detailCover);
        },
        delProdetail:function () {
		    var that = this;

            if($(".Xcontent_R").length == 1){
                layer.msg("手下留情，留一个商品吧！",{icon:7,time:2000})
            }else{
                layer.confirm('确认要删除吗？',function(index){
                    var id = $(".Xcontent08").find('.selected').attr('data-id');
                    if(id != undefined && id > 0){
                        var ids = [];
                        ids.push(id)

                        $.ajax({
                            type: "POST",
                            url: baseURL + "sys/productdetail/delete",
                            contentType: "application/json",
                            data: JSON.stringify(ids),
                            success: function(r){
                                if(r.code == 0){
                                    layer.msg("操作成功", {icon: 1});

                                    that.delHtml();
                                    layer.closeAll('dialog');
                                }else{
                                    layer.alert(r.msg);
                                }
                            }
                        });
                    }else{
                        that.delHtml();
                        layer.closeAll('dialog');
                    }
                });
            }
        },
        delHtml:function () {
            var idx = $('.Xcontent35').attr('data-idx');
            $(".Xcontent_R").eq(idx).remove();

            $(".Xcontent08").find('.Xcontent_R').eq(0).addClass('selected');
            var detailsku = $(".Xcontent_R").eq(0).attr('data-detailsku');
            var detailname = $(".Xcontent_R").eq(0).attr('data-detailname');
            var detailsize = $(".Xcontent_R").eq(0).attr('data-detailsize');
            var detailtaste = $(".Xcontent_R").eq(0).attr('data-detailtaste');
            var price = $(".Xcontent_R").eq(0).attr('data-detailprice');
            var imgPath = $(".Xcontent_R").eq(0).find('img').attr('src');


            $(".Xcontent14 p").text(detailname)
            $(".pdsize").text(detailsize)
            $(".pdtaste").text(detailtaste)
            $(".pdsku").text(detailsku);
            $(".Xcontent19 span").text(price)
            $(".Xcontent06 img").attr('src',imgPath);

            $('.Xcontent35').attr('data-idx',0);
            $('.Xcontent34').attr('data-idx',0);


        }
	}
});

$(document).on('mouseover','.Xcontent08>div',function (event){
    $('.Xcontent08>div').removeClass('selected');
    if(event.type == "mouseover"){
        $(this).addClass('selected');
        $('.Xcontent34').attr('data-idx',$(this).index())
        $('.Xcontent35').attr('data-idx',$(this).index())
    }
    $('.Xcontent06').find('img').attr('src',$(this).find('img').attr('src'))

    $(".Xcontent14").find('p').text($(this).attr('data-detailname'));
    $(".Xcontent19").find('span').text($(this).attr('data-detailprice'));
    $(".pdsize").text($(this).attr('data-detailsize'));
    $(".pdtaste").text($(this).attr('data-detailtaste'));
    $(".pdsku").text($(this).attr('data-detailsku'));
})



