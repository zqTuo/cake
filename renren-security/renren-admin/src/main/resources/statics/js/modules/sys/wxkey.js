layui.use('element', function(){
    var $ = layui.jquery
        ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
});
$.emoticons({
    'activeCls':'trigger-active'
},function(api){
//        $(".edit_area").html(api.format($(".edit_area").val()))
});

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/wxkey/list',
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '关键字', name: 'keyword', index: 'keyword', width: 80 },
			{ label: '消息类型', name: 'msgType', index: 'msg_type', width: 80 ,formatter:function (cellValue) {
                    if(cellValue === 'text'){
                        return "文本消息";
                    }else if(cellValue === 'news'){
                        return "图文消息";
                    }else{
                        return '图片消息';
                    }
                } },
            { label: '状态', name: 'status', index: 'status', width: 80,  formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }},
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
        q:{name: null},
		showList: true,
		title: null,
		wxKey: {},
        keyList: [],
        resMsgArr:[],
        msg:'',
        resText:''
	},
	methods: {
	    //根据关键字进行查询
		query: function () {
			vm.reload();
		},
        addMessage:function(){
		    //将关键字以键值对的方式存到数组对象里面  转化成数值类型的数组  在将数组转化成字符串
		    var a =new Array()

            for(var index in vm.keyList){
                var item = vm.keyList[index]

                a[index]=item.name
            }
            console.log(a)
            vm.wxKey.keyword =a.join(",")
            console.log(vm.wxKey.keyword)
        },
        //添加输入框
        addKey:function(){
		  this.keyList.push({})

        },
        //删除输入框
        delKey:function(index){
		   this.keyList.splice(index,1)
        },
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.wxKey = {};
			vm.keyList=[{}];
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
		    this.addMessage()
            console.log(vm.wxKey)
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.wxKey.id == null ? "sys/wxkey/save" : "sys/wxkey/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.wxKey),
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
                        url: baseURL + "sys/wxkey/delete",
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
			$.get(baseURL + "sys/wxkey/info/"+id, function(r){
                vm.wxKey = r.wxKey;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		}

	}
});
//shanch

$(document).on('click','.weui-desktop-msg-sender__tab',function () {
    $(this).parent().find('.weui-desktop-msg-sender__tab').removeClass('weui-desktop-msg-sender__tab_selected');
    $(this).addClass('weui-desktop-msg-sender__tab_selected');
    $(this).parent().parent().parent().find(".weui_tab").css('display','none');
    $(this).parent().parent().parent().find(".weui_tab").eq($(this).index()).css('display','block');

    var idx = $(this).index();
    if(idx === 0){
        $(this).closest('.row').attr('data-msgType','text');
    }else if(idx === 1){
        $(this).closest('.row').attr('data-msgType','image');
    }else{
        $(this).closest('.row').attr('data-msgType','news');
    }
})

//监听输入的字数  插入表情时无法立即执行BUG
$(document).on('input propertychange focus', '.edit_area', function () {
    var curLength = $(".edit_area").val().length;
    if (curLength > 300) {
        var text = $(".edit_area").val().substr(0, 300);
        $(".edit_area").val(text);
        $(".editor_toolbar p").css('color', 'red')
    } else {
        $(".editor_toolbar p").css('color', '#9A9A9A')
        $(".editor_tip").find("em").text(300 - $(".edit_area").val().length);
    }
})

//从素材库选择图文消息素材
$(document).on('click', '.chooseMedia', function () {
    var index = $(this).attr('data-resIdx');
    var url = "modules/sys/wxmedia.html?idx=" + index;
    layer_show('选择素材', url, '1000', '820');
});
//从素材库选择图片素材
$(document).on('click', '.choosePic', function () {
    var index = $(this).attr('data-resIdx');
    var url = "wxPicForm?idx=" + index;
    layer_show('选择素材', url, '800', '640');
});
//删除素材
$(document).on('click', '.jsmsgSenderDelBt', function () {
    $(this).parent().remove();
    $(".jsMsgSendTab").css('display', 'block');
})

//上传图片
$(document).on('change', '.wxPicUpload', function () {
    layer.msg('暂未开放此功能', {icon: 7, time: 2000});
//        var file=$(this).files;
//        var data = new FormData();
//        data.append("file", file);
//        $.ajax({
//            type: 'POST',
//            url: "wxMediaUpload",
//            data: data,
//            processData: false,
//            contentType: false,
//            dataType: 'json',
//            success: function (data) {
//
//            },
//            error: function (e) {
//                alert("上传失败");
//            }
//        })
})

function detail_format(data) {
    var html = '<div class="weui-desktop-table__fold-context"> ' +
        '<div class="keywords_reply_detail"> ' +

        '<div class="weui-desktop-form__control-group weui-desktop-form__control-group_offset"> ' +
        '<label class="weui-desktop-form__label">关键词</label> ' +
        '<div class="weui-desktop-form__controls"> ' +
        '<ul class="keywords_name_list"> ';
    $.each(data.keyword.split(','),function (index,item) {
        html += '<li class="keywords_name_item"> ';
        if(data.degree == 0){
            html += '<i class="keywords_name_status"> (半匹配) </i> ';
        }else{
            html += '<i class="keywords_name_status"> (全匹配) </i> ';
        }
        html += '<strong class="keywords_name">'+item+'</strong> </li> '
    })
    html += '</ul></div></div> ' +

        '<div class="weui-desktop-form__control-group weui-desktop-form__control-group_offset"> ' +
        '<label class="weui-desktop-form__label">回复内容</label> ' +
        '<div class="weui-desktop-form__controls"> ' ;
    if(data.msgType == "text"){
        html += '<div> <div class="weui-desktop-media-text"> <span>'+data.content+'</span> </div></div>'
    }else if(data.msgType == "news"){
        $.ajax({
            type: "post",
            async: false, //同步执行
            url: "getMediaByID",
            data: {mid:data.mediaID},
            dataType: "json", //返回数据形式为json
            success: function (result) {
                if(result.status == "1"){
                    var newsItemLen=result.result.news_item.length;
                    for(var j =0;j < newsItemLen ;j++){
                        var newsItem=result.result.news_item[j];
                        var updateTime=result.result.update_time;
                        if(newsItemLen >1 ){
                            if(j ==0){
                                html+='<div class="appmsgLeft" style="width: 320px;">' +
                                    '<div class="appmsg multi has_first_cover" > ' +
                                    '<div class="appmsg_content"> ' +

                                    '<div class="appmsg_info"> ' +
                                    '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +

                                    '<div class="cover_appmsg_item"> <h4 class="appmsg_title js_title">' +
                                    ' <a href="#"  data-idx="'+j+'">'+newsItem.title+'</a> </h4> ' +
                                    '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div></div>';
                            }else{
                                html+='<div class="appmsg_item has_cover">' +
                                    '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+');"></div>' +
                                    '<h4 class="appmsg_title js_title">' +
                                    '<a href="#" data-idx="'+j+'">'+newsItem.title+'</a></h4>' +
                                    '</div>';
                                if(j==newsItemLen-1){
                                    html +='</div></div></div>';
                                }

                            }
                        }else{
                            html +='<div class="appmsgLeft"> ' +
                                '<div class="appmsg single has_first_cover" data-completed="1"> ' +
                                '<div class="appmsg_content"> ' +

                                '<div class="appmsg_info"> ' +
                                '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +
                                '<div class="appmsg_item"> <h4 class="appmsg_title js_title"> ' +
                                '<a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+' </a> </h4> ' +
                                '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div> ' +
                                '<p class="appmsg_desc">'+newsItem.digest+'</p> </div> </div> ' +
                                '</div></div>'
                        }
                    }
                }else if(result == "-1"){
                    layer.msg('参数有误....',{icon:7,time:2000});
                }
            },
            error: function (errorMsg) {
                alert("请求数据失败啦!");
            }
        });

    }
    html += '</div></div></div></div>';

    return html;
}