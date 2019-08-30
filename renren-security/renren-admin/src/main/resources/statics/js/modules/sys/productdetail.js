
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		productDetail: {},
		sizeList:[],
		tasteList:[]
	},
	mounted: function(){
		var that = this;
		that.getDataList();
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.productDetail = {};
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
                var url = "sys/productdetail/saveSizeOrTaste";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.productDetail),
                    success: function(r){
                        if(r.code === 0){
                             layer.msg("操作成功", {icon: 1});
							vm.productDetail.detailCover = $("#imgProDetail0").attr("src");
							console.log(window.parent.vm)
							console.log(window.parent.vm.data.productDetailList)
							console.log(window.parent.vm.productDetailList)
							window.parent.vm.data.productDetailList.push(vm.productDetail);

							var pic_R = '<div class="Xcontent_R" data-detailName="'+ vm.productDetail.detailName
								+'" data-detailPrice="'+vm.productDetail.detailPrice+'" data-detailSize="'+vm.productDetail.detailSize
								+'" data-detailTaste="'+vm.productDetail.detailTaste+'">' +
								'<img src="'+ vm.productDetail.detailCover +'"></div>';

							parent.$(".Xcontent06").find('img').attr('src',vm.productDetail.detailCover);
							parent.$(".Xcontent08").append(pic_R);
							parent.$(".Xcontent08").find('.Xcontent_R').removeClass('selected');
							parent.$(".Xcontent08").find('.Xcontent_R').eq(-1).addClass('selected');
							parent.$(".Xcontent14").find('p').text(vm.productDetail.detailName);
							parent.$(".Xcontent19").find('span').text(vm.productDetail.detailPrice);
							// parent.$(".pdsku").text(sku);
							parent.$(".Xcontent").attr('style','display:block');
							var len = parent.$(".Xcontent_R").length;
							parent.$('.Xcontent34').attr('data-idx',len-1)
							parent.$('.Xcontent35').attr('data-idx',len-1)


                             vm.reload();
                             $('#btnSaveOrUpdate').button('reset');
                             $('#btnSaveOrUpdate').dequeue();

							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);//关闭窗口
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
                        url: baseURL + "sys/productdetail/delete",
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
			$.get(baseURL + "sys/productdetail/info/"+id, function(r){
                vm.productDetail = r.productDetail;
            });
		},
		reload: function (event) {
			vm.showList = true;
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
			var value = e.target.value;
			$(this).parent().find('input').val(value)
		}
	}
});