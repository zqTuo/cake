layui.use('element', function(){
    var $ = layui.jquery
        ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
});


var index=$("input[name='hello']:checked").val();
if(index == 1){
    $("#edit").css("display","block");
    $("#url").css("display","none");
}else{
    $("#edit").css("display","none");
    $("#url").css("display","block");
}
$("#menuList li").find("div").css("display",'none');
$("#menuList li").eq(0).find('div').css('display','block')
if( $("#menuList li").length >1){
    var curName=$("#menuList li").eq(0).find('.menu_a').find('span').text()
    $(".js_menu_name").val(curName);
    var curType=$("#menuList li").eq(0).attr('data-type');
    var curUrl=$("#menuList li").eq(0).attr('data-url');
    var pagepath=$("#menuList li").eq(0).attr('data-pagepath');
    if(curType == 'TYPE_view'){ //跳转页面
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(1).checked=true;
        $("#urlText").val(curUrl)
        $("#edit").css('display','none');
        $("#sendDiy").css('display','none');
        $("#url").css('display','block');
    }else if(curType == 'TYPE_media_id'){ //发送消息
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(0).checked=true;
        $("#edit").css('display','block');
        $("#url").css('display','none');
        $("#sendDiy").css('display','none');
    }else if(curType == 'TYPE_'){ //类型为空 有子菜单
        $(".typeIsShow").css('display','none');
    }else if(curType == 'TYPE_click'){ //类型为空 有子菜单
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(2).checked=true;
        $("#edit").css('display','none');
        $("#url").css('display','none');
        $("#sendDiy").css('display','block');
    }else if(curType == 'TYPE_miniprogram'){
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(3).checked=true;
        $("#edit").css('display','none');
        $("#sendDiy").css('display','none');
        $("#url").css('display','none');
        $("#mini").css('display','block');
        $("#miniurlText").val(pagepath)
    }
}
//修改菜单类型按钮
$(".frm_radio").on("click",function () {
    var index=$("input[name='hello']:checked").val();
    if(index == 1){
        $("#edit").css("display","block");
        $("#url").css("display","none");
        $("#sendDiy").css("display","none");
        $("#mini").css("display","none");
        $("#menuList").find(".current").attr('data-type','TYPE_media_id');
    }else if(index == 2){
        $("#edit").css("display","none");
        $("#sendDiy").css("display","none");
        $("#url").css("display","block");
        $("#mini").css("display","none");
        $("#menuList").find(".current").attr('data-type','TYPE_view');
        var newUrl=$("#urlText").val();
        $("#menuList").find(".current").attr('data-url',newUrl);
    }else if(index == 3){
        $("#edit").css("display","none");
        $("#sendDiy").css("display","block");
        $("#url").css("display","none");
        $("#mini").css("display","none");
        $("#menuList").find(".current").attr('data-type','TYPE_click');
        var menuKEY=$("#menuKEY").val();
        $("#menuList").find(".current").attr('data-key',menuKEY);
    }else{
        $("#edit").css("display","none");
        $("#url").css("display","none");
        $("#sendDiy").css("display","none");
        $("#mini").css("display","block");
        $("#menuList").find(".current").attr('data-type','TYPE_miniprogram');
        var miniUrl=$("#miniurlText").val();
        $("#menuList").find(".current").attr('data-url',miniUrl);
    }
})

var subLen=$("#menuList li").eq(0).find('.sub_pre_menu_list li').length;
if(subLen >1){
    $("#menuList li").eq(0).find('.sub_pre_menu_list li').eq(0).addClass('current');
}else{
    $("#menuList li").eq(0).addClass('current');
}


//添加一级按钮
$(".js_addL1Btn").on('click',function () {
    var html='<li class="MenuLi publi jsMenu pre_menu_item grid_item ui-sortable ui-sortable-disabled size1of3 selected current">' +
        ' <a href="javascript:void(0);" class="pre_menu_link menu_a" draggable="false"> ' +
        '<i class="icon_menu_dot js_icon_menu_dot dn" style="display: none;"></i> ' +
        '<i class="icon20_common sort_gray"></i> <span class="js_l1Title js_Title">菜单名称</span> ' +
        '</a> <div class="sub_pre_menu_box js_l2TitleBox" style=""> ' +
        '<ul class="sub_pre_menu_list"> ' +
        '<li class="js_addMenuBox">' +
        '<a href="javascript:void(0);" class="jsSubView js_addL2Btn" title="最多添加5个子菜单" draggable="false">' +
        '<span class="sub_pre_menu_inner js_sub_pre_menu_inner">' +
        '<i class="icon14_menu_add"></i></span></a></li>' +
        ' </ul> <i class="arrow arrow_out"></i>' +
        ' <i class="arrow arrow_in"></i> </div> </li>';
    $(".sub_pre_menu_box").css('display','none');
    $("#menuList li").removeClass('current');
    $(".typeIsShow").css('display','block');
    $(this).parent().before(html);
})
//选中一级菜单
$(document).on('click',".menu_a",function () {
    $("li").removeClass('current');
    $(".sub_pre_menu_box").css('display','none');
    $(this).parent().addClass('current')
    $(this).parent().find('div').css('display','block')

    var name=$(this).find('span').text();
    $(".js_menu_name").val(name);

})
//选中子菜单
$(document).on('click',".jsSubView",function () {
    $("li").removeClass('current');
    $(this).parent().addClass('current')
    var name=$(this).find('span').find('span').text();
    $(".js_menu_name").val(name);
    $(".typeIsShow").css('display','block');
})

Date.prototype.Format = function(fmt) { //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

//按照菜单类型设置显示与隐藏编辑区域
$(document).on('click',"#menuList li a",function () {
    var type=$(this).parent().attr('data-type');
    var url=$(this).parent().attr('data-url');
    var pagepath=$(this).parent().attr('data-pagepath');
    var mid=$(this).parent().attr('data-mid');
    var mkey=$(this).parent().attr('data-key');
    if(type == 'TYPE_view'){ //跳转页面
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(1).checked=true;
        $("#urlText").val(url)
        $("#edit").css('display','none');
        $("#url").css('display','block');
        $("#mini").css('display','none');
        $("#sendDiy").css("display","none");
    }else if(type == 'TYPE_click'){ //发送消息
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(2).checked=true;
        $("#menuKEY").val(mkey)
        $("#edit").css('display','none');
        $("#url").css('display','none');
        $("#mini").css('display','none');
        $("#sendDiy").css("display","block");
    }else if(type == 'TYPE_media_id'){ //发送图文消息
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(0).checked=true;
        $("#edit").css('display','block');
        $("#url").css('display','none');
        $("#sendDiy").css("display","none");
        $("#mini").css('display','none');

        //根据mid获取图文素材
        postData("getMediaByID",{mid:mid},function (result) {
            if(result.status == "1"){
                var newsItemLen=result.result.news_item.length;
                var mediaHtml='';
                for(var j =0;j < newsItemLen ;j++){
                    var newsItem=result.result.news_item[j];
                    var updateTime=result.result.update_time;
                    if(newsItemLen >1 ){
                        if(j ==0){
                            mediaHtml+='<div class="appmsgLeft">' +
                                '<div class="appmsg multi has_first_cover" > ' +
                                '<div class="appmsg_content"> ' +

                                '<div class="appmsg_info"> ' +
                                '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +

                                '<div class="cover_appmsg_item"> <h4 class="appmsg_title js_title">' +
                                ' <a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a> </h4> ' +
                                '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div></div>';
                        }else{
                            mediaHtml+='<div class="appmsg_item has_cover">' +
                                '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+');"></div>' +
                                '<h4 class="appmsg_title js_title">' +
                                '<a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a></h4>' +
                                '</div>';
                            if(j==newsItemLen-1){
                                mediaHtml +='<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i>' +
                                    '</div></div></div><a href="javascript:;" class="jsmsgSenderDelBt link_dele" data-type="10" onclick="return false;">删除</a></div>';
                            }

                        }
                    }else{
                        mediaHtml +='<div class="appmsgLeft"> ' +
                            '<div class="appmsg single has_first_cover" data-completed="1"> ' +
                            '<div class="appmsg_content"> ' +

                            '<div class="appmsg_info"> ' +
                            '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +
                            '<div class="appmsg_item"> <h4 class="appmsg_title js_title"> ' +
                            '<a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+' </a> </h4> ' +
                            '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div> ' +
                            '<p class="appmsg_desc">'+newsItem.digest+'</p> </div> </div> ' +
                            '<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i> </div> ' +
                            '</div> <a href="javascript:;" class="jsmsgSenderDelBt link_dele" data-type="10" onclick="return false;">删除</a></div>'
                    }
                }

                $(".send").find('.tab_content').eq(0).find('.jsMsgSendTab').css('display','none');
                $(".send").find('.tab_content').eq(0).find('.appmsgLeft').remove();
                $(".send").find('.tab_content').eq(0).find('.inner').append(mediaHtml);
            }else if(result == "-1"){
                layer.msg('参数有误....',{icon:7,time:2000});
            }
        })


    }else if(type == 'TYPE_'){ //类型为空
        if($(this).parent().find('.sub_pre_menu_list li').length > 1){//有子菜单
            $(".typeIsShow").css('display','none');
        }else{
            $(".typeIsShow").css('display','block');
        }
    }else if(type == 'TYPE_miniprogram'){
        $(".typeIsShow").css('display','block');
        $("input[name='hello']").get(3).checked=true;
        $("#edit").css('display','none');
        $("#url").css('display','none');
        $("#mini").css('display','block');
        $("#sendDiy").css("display","none");
        $("#miniurlText").val(pagepath)
    }
})

//添加子菜单
$(document).on('click',".js_addMenuBox",function () {
    var idx=$(this).parent().find('li').length;
    var html='<li data-dev="'+(idx-1)+'" class="publi subLi jslevel2">' +
        '<a href="javascript:void(0);" class="jsSubView" draggable="false">' +
        '<span class="sub_pre_menu_inner js_sub_pre_menu_inner">' +
        '<i class="icon20_common sort_gray"></i><span class="js_l2Title js_Title">子菜单名称</span>' +
        '</span></a></li>';
    $(this).before(html);
    $(".typeIsShow").css('display','block');
    $("#menuList li").removeClass('current');
    $(this).parent().find('li').eq(-2).addClass('current');
    var name=$(this).parent().find('li').eq(-2).find('span').find('span').text();
    $(".js_menu_name").val(name);
    $(this).parent().parent().parent().attr('data-type',"TYPE_");
    var length=$(this).parent().find('li').length;
    if(length == 6){
        $(this).parent().find('li').eq(-1).css('display','none');
    }
})

//删除菜单
$("#jsDelBt").on('click',function () {
    var length=$("#menuList").find(".current").length;
    if(length <=0){
        layer.msg('请选中要删除的菜单....',{icon:7,time:2000});
    }else{
        layer.confirm('确定要删除该菜单？',function () {
            layer.closeAll('dialog');
            $("#menuList").find(".current").remove()
        });
    }
})

//修改菜单名称
$(".js_menu_name").blur(function () {
    var newName=$(this).val();
    if(newName != ""){
        $("#menuList").find(".current .js_Title").eq(0).text(newName);
    }
})

//修改跳转链接
$("#urlText").blur(function () {
    var newUrl=$(this).val();
    if(newUrl != ""){
        $("#menuList").find(".current").attr('data-url',newUrl);
        $("#menuList").find(".current").attr('data-type','TYPE_view');
    }
})

//自定义
$("#menuKEY").blur(function () {
    var mKEY=$(this).val();
    if(mKEY != ""){
        $("#menuList").find(".current").attr('data-key',mKEY);
        $("#menuList").find(".current").attr('data-type','TYPE_click');
    }
})

//小程序
$("#miniurlText").blur(function () {
    var newminiUrl=$(this).val();
    if(newminiUrl != ""){
        $("#menuList").find(".current").attr('data-pagepath',newminiUrl);
        $("#menuList").find(".current").attr('data-type','TYPE_miniprogram');
    }
})

//从素材库选择图文消息素材
$('.chooseMedia').on( 'click', function () {
    var url="wxMaterialForm";
    layer_show('选择素材',url,'1000','820');
});
//从素材库选择图片素材
$('.choosePic').on( 'click', function () {
    var url="wxPicForm";
    layer_show('选择素材',url,'800','640');
});

//删除素材
$(document).on('click','.jsmsgSenderDelBt',function () {
    $(this).parent().remove();
    $("#menuList").find(".current").attr('data-mid',"");
    $(".jsMsgSendTab").css('display','block');
})

//上传图片
$(document).on('change','.wxPicUpload',function () {

    var stateHTML = '<br><select class="select_Status" id="mediaType"> ' +
        '<option value="0">------公众号------</option>' +
        '<option value="1">------小程序------</option>';
    layer.confirm('<label>请选择使用途径:</label>' + stateHTML, {title: "使用途径"}, function (index) {
        var mediaType = $("#mediaType").val();
        var file=$(this).files;
        var data = new FormData();
        data.append("file", file);
        data.append("mediaType", mediaType);
        $.ajax({
            type: 'POST',
            url: "wxMediaUpload",
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                alert("成功")
            },
            error: function (e) {
                alert("上传失败");
            }
        })
    });

})


//发布
$(".pubBt").click(function () {
    var len=$(".MenuLi").length;
    var isOk=true;
    for (var i=0;i< len;i++){
        var typeCheck=$(".MenuLi").eq(i).attr('data-type');
        var name_c=$(".MenuLi").eq(i).find('.js_l1Title').text();
        if(typeCheck == 'TYPE_click'){
            var key_c=$(".MenuLi").eq(i).attr('data-key');
            if(key_c == "" || name_c == ""){
                isOk=false;
                layer.msg('有菜单没有设置完整！',{icon:7,time:2000});
                break;
            }
        }else if(typeCheck == 'TYPE_media_id'){
            var mid_c=$(".MenuLi").eq(i).attr('data-mid');
            if(mid_c == "" || name_c == ""){
                isOk=false;
                layer.msg('有菜单没有设置完整！',{icon:7,time:2000});
                break;
            }
        }else if(typeCheck == 'TYPE_view'){
            var url_c=$(".MenuLi").eq(i).attr("data-url");
            if(url_c == "" || name_c == "") {
                isOk = false;
                layer.msg('有菜单没有设置完整！', {icon: 7, time: 2000});
                break;
            }
        }else if(typeCheck == 'TYPE_'){
            var subLen=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').length;
            if(subLen == 0){
                isOk = false;
                layer.msg('有菜单没有设置完整！', {icon: 7, time: 2000});
                break;
            }else{
                for (var j=0;j<subLen;j++){
                    var sub_name_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').eq(j).find('.js_Title').text();
                    var subType=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').eq(j).attr('data-type');

                    if(subType == 'TYPE_click'){
                        var sub_key_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').eq(j).attr('data-key');
                        if(sub_key_c == "" || sub_name_c == ""){
                            isOk=false;
                            layer.msg('有菜单没有设置完整！',{icon:7,time:2000});
                            break;
                        }
                    }else if(subType == 'TYPE_media_id'){
                        var sub_mid_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').eq(j).attr('data-mid');
                        if(sub_mid_c == "" || sub_name_c == ""){
                            isOk=false;
                            layer.msg('有菜单没有设置完整！',{icon:7,time:2000});
                            break;
                        }
                    }else if(subType == 'TYPE_view'){
                        var sub_url_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').eq(j).attr("data-url");
                        if(sub_url_c == "" || sub_name_c == "") {
                            isOk = false;
                            layer.msg('有菜单没有设置完整！', {icon: 7, time: 2000});
                            break;
                        }
                    }else if(subType == 'TYPE_miniprogram'){
                        var sub_url_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').eq(j).attr("data-pagepath");
                        if(sub_url_c == "" || sub_name_c == "") {
                            isOk = false;
                            layer.msg('有菜单没有设置完整！', {icon: 7, time: 2000});
                            break;
                        }
                    }else{
                        isOk=false;
                        layer.msg('其他类型请联系管理员开通！',{icon:7,time:2000});
                        break;
                    }
                }
            }
        }else if(typeCheck == 'TYPE_miniprogram'){
            var url_c=$(".MenuLi").eq(i).attr("data-url");
            if(url_c == "" || name_c == "") {
                isOk = false;
                layer.msg('有菜单没有设置完整！', {icon: 7, time: 2000});
                break;
            }
        }else{
            isOk=false;
            layer.msg('其他类型请联系管理员开通！',{icon:7,time:2000});
            break;
        }
    }
    if(isOk == false){
        return;
    }

    var menuLen=$(".MenuLi").length;
    var jsonStr='{"button":[';
    for (var i=0;i< menuLen;i++){
        var type=$(".MenuLi").eq(i).attr('data-type');
        var name=$(".MenuLi").eq(i).find('.js_l1Title').text();
        if(type == 'TYPE_click'){
            //点击用key值交互
            var key=$(".MenuLi").eq(i).attr('data-key');
            jsonStr += '{"type":"click","name":"'+name+'","key":"'+key+'"}';
        }else if(type == 'TYPE_media_id'){ //图文
            var mid=$(".MenuLi").eq(i).attr('data-mid');
            if(mid != ""){
                jsonStr += '{"type": "media_id","name": "'+name+'","media_id": "'+mid+'"}';
            }else{
                layer.msg('图文菜单没有设置完整！',{icon:7,time:2000});
                break;
            }
        }else if(type == 'TYPE_view'){
            var url=$(".MenuLi").eq(i).attr("data-url");
            if(url != ""){
                jsonStr += '{"type":"view","name":"'+name+'","url":"'+url+'"}';
            }else{
                layer.msg('跳转菜单没有设置完整！',{icon:7,time:2000});
                break;
            }
        }else if(type == 'TYPE_'){
            var subMenuLen=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('.subLi').length;
            jsonStr += '{"name": "'+name+'","sub_button": [';
            for(var j=0;j<subMenuLen;j++){

                var sub_name_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('li').eq(j).find('.js_Title').text();
                var subType=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('li').eq(j).attr('data-type');
                if(subType == 'TYPE_click'){
                    var sub_key_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('li').eq(j).attr('data-key');
                    jsonStr += '{"type":"click","name":"'+sub_name_c+'","key":"'+sub_key_c+'"}';
                }else if(subType == 'TYPE_media_id'){
                    var sub_mid_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('li').eq(j).attr('data-mid');
                    jsonStr += '{"type": "media_id","name": "'+sub_name_c+'","media_id": "'+sub_mid_c+'"}';
                }else if(subType == 'TYPE_view'){
                    var sub_url_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('li').eq(j).attr("data-url");
                    jsonStr += '{"type":"view","name":"'+sub_name_c+'","url":"'+sub_url_c+'"}';
                }else if(subType == 'TYPE_miniprogram'){
                    var sub_url_c=$(".MenuLi").eq(i).find('.sub_pre_menu_list').find('li').eq(j).attr("data-pagepath");
                    jsonStr += '{"type":"miniprogram","name":"'+sub_name_c+'","pagepath":"'+sub_url_c+'","url":"http://mp.weixin.qq.com"}';
                }
                if(j != subMenuLen-1){
                    jsonStr += ',';
                }
            }
            jsonStr += ']}';
        }else if(type == 'TYPE_miniprogram'){
            var url=$(".MenuLi").eq(i).attr("data-pagepath");
            if(url != ""){
                jsonStr += '{"type":"miniprogram","name":"'+name+'","pagepath":"'+url+'","url":"http://mp.weixin.qq.com"}';
            }else{
                layer.msg('跳转菜单没有设置完整！',{icon:7,time:2000});
                break;
            }
        }
        if(i != len-1){
            jsonStr += ',';
        }
    }
    jsonStr +=  ']}';
    postData('pushMenu',{jsonStr:jsonStr},function (result) {
        if(result.status == "1"){
            layer.msg('发布成功！将于24小时内生效...',{icon:1,time:3000});
        }else{
            layer.msg('发布失败：' + result.errmsg,{icon:7,time:5000});
        }

    })
})


