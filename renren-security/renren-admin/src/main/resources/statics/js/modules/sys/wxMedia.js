$.get(baseURL + "sys/wxpublicmenu/getOnlineWxMaterial?type=news", function(r){
    if(r.code === 0){
        $(".inner").html('');
        var len = r.result.item.length;
        for(var i =0;i < len;i++){
            var content = r.result.item[i].content;
            var updateTime = r.result.item[i].content.update_time;
            var mediaID = r.result.item[i].media_id;
            var newsItemLen = r.result.item[i].content.news_item.length;
            var html='';
            for(var j =0;j < newsItemLen ;j++){
                var newsItem=content.news_item[j];
                if(newsItemLen >1 ){
                    if(j ==0){
                        html+='<div class="appmsgLeft">' +
                            '<div data-mid="'+mediaID+'" class="appmsg multi has_first_cover" > ' +
                            '<div class="appmsg_content"> ' +

                            '<div class="appmsg_info"> ' +
                            '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +

                            '<div class="cover_appmsg_item"> <h4 class="appmsg_title js_title">' +
                            ' <a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a> </h4> ' +
                            '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div></div>';
                    }else{
                        html+='<div class="appmsg_item has_cover">' +
                            '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+');"></div>' +
                            '<h4 class="appmsg_title js_title">' +
                            '<a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a></h4>' +
                            '</div>';
                        if(j==newsItemLen-1){
                            html +='<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i>' +
                                '</div></div></div></div>';
                        }

                    }
                }else{
                    html +='<div class="appmsgLeft"> ' +
                        '<div data-mid="'+mediaID+'"  class="appmsg single has_first_cover" data-completed="1"> ' +
                        '<div class="appmsg_content"> ' +

                        '<div class="appmsg_info"> ' +
                        '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +
                        '<div class="appmsg_item"> <h4 class="appmsg_title js_title"> ' +
                        '<a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+' </a> </h4> ' +
                        '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div> ' +
                        '<p class="appmsg_desc">'+newsItem.digest+'</p> </div>' +
                        '<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i> </div> ' +
                        '</div> </div></div>'
                }

            }
            if(i%2 == 0){
                $(".appmsg_col").eq(0).find('.inner').append(html);
            }else{
                $(".appmsg_col").eq(1).find('.inner').append(html);
            }
        }
        var count=r.result.total_count;
        var itemCount=r.result.item_count;
        var totalPage=Math.ceil(count/10);
        if(totalPage > 1){
            $(".page_num").find('label').eq(1).text(totalPage);
            $(".pageNavigator").css('display','block');
        }
    }
});

//上一页
$(document).on('click','.page_prev',function () {
    var curPage=$(".page_num").find('label').eq(0).text();
    curPage--;
    if(curPage === 1){
        $(this).css('display','none');
    }

    $(".page_num").find('label').eq(0).text(curPage);

    $(".page_next").attr('style','');


    $.get(baseURL + "sys/wxpublicmenu/getOnlineWxMaterial?type=news&pageNo=" + curPage, function(r){
        if(r.code === 0){
            $(".inner").html('');
            var len = r.result.item.length;
            for(var i =0;i < len;i++){
                var content = r.result.item[i].content;
                var updateTime = r.result.item[i].content.update_time;
                var newsItemLen = r.result.item[i].content.news_item.length;
                var html='';
                for(var j =0;j < newsItemLen ;j++){
                    var newsItem=content.news_item[j];
                    if(newsItemLen >1 ){
                        if(j ==0){
                            html+='<div class="appmsgLeft">' +
                                '<div class="appmsg multi has_first_cover" > ' +
                                '<div class="appmsg_content"> ' +

                                '<div class="appmsg_info"> ' +
                                '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +

                                '<div class="cover_appmsg_item"> <h4 class="appmsg_title js_title">' +
                                ' <a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a> </h4> ' +
                                '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div></div>';
                        }else{
                            html+='<div class="appmsg_item has_cover">' +
                                '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+');"></div>' +
                                '<h4 class="appmsg_title js_title">' +
                                '<a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a></h4>' +
                                '</div>';
                            if(j==newsItemLen-1){
                                html +='<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i>' +
                                    '</div></div></div></div>';
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
                            '<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i> </div> ' +
                            '</div> </div>'
                    }

                }
                if(i%2 == 0){
                    $(".appmsg_col").eq(0).find('.inner').append(html);
                }else{
                    $(".appmsg_col").eq(1).find('.inner').append(html);
                }
            }
        }
    })
})

//下一页
$(document).on('click','.page_next',function () {
    var curPage=$(".page_num").find('label').eq(0).text();
    var totalPage=$(".page_num").find('label').eq(1).text();
    curPage++;
    $(".page_prev").attr('style','');
    if(curPage == totalPage){
        $(this).css('display','none');
    }

    $(".page_num").find('label').eq(0).text(curPage);

    $.get(baseURL + "sys/wxpublicmenu/getOnlineWxMaterial?type=news&pageNo=" + curPage, function(r){
        if(r.code === 0){
            $(".inner").html('');
            var len = r.result.item.length;
            for(var i =0;i < len;i++){
                var content = r.result.item[i].content;
                var updateTime = r.result.item[i].content.update_time;
                var newsItemLen = r.result.item[i].content.news_item.length;
                var html='';
                for(var j =0;j < newsItemLen ;j++){
                    var newsItem=content.news_item[j];
                    if(newsItemLen >1 ){
                        if(j ==0){
                            html+='<div class="appmsgLeft">' +
                                '<div class="appmsg multi has_first_cover" > ' +
                                '<div class="appmsg_content"> ' +

                                '<div class="appmsg_info"> ' +
                                '<em class="appmsg_date">更新于 '+(new Date(parseInt(updateTime)*1000)).Format('yyyy年MM月dd日')+'</em> </div> ' +

                                '<div class="cover_appmsg_item"> <h4 class="appmsg_title js_title">' +
                                ' <a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a> </h4> ' +
                                '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+')"></div></div>';
                        }else{
                            html+='<div class="appmsg_item has_cover">' +
                                '<div class="appmsg_thumb_wrp" style="background-image:url('+newsItem.thumb_url+');"></div>' +
                                '<h4 class="appmsg_title js_title">' +
                                '<a href="" target="_blank" data-idx="'+j+'">'+newsItem.title+'</a></h4>' +
                                '</div>';
                            if(j==newsItemLen-1){
                                html +='<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i>' +
                                    '</div></div></div></div>';
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
                            '<div class="edit_mask appmsg_mask"><i class="icon_card_selected">已选择</i> </div> ' +
                            '</div> </div>'
                    }

                }
                if(i%2 == 0){
                    $(".appmsg_col").eq(0).find('.inner').append(html);
                }else{
                    $(".appmsg_col").eq(1).find('.inner').append(html);
                }
            }
        }
    })
})

//点击素材 选中唯一一个
$(document).on('click','.appmsg_mask',function () {
    $(".has_first_cover").removeClass('selected');
    $(this).parent().parent().addClass('selected');
//        $(this).css('display','block');
})

$(".js_btn").click(function () {
    var index=$(this).attr('data-index');
    if(index == 1){
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);//关闭窗口
    }else{
        var mid=$(".appmsgLeft").find('.selected').attr('data-mid');
        var mediaHtml = '<div class="msg_sender_media" data-mid="'+mid+'" data-msgType="news">';
        mediaHtml += $(".appmsgLeft").find('.selected').parent().html();
        mediaHtml += '<a href="javascript:;" class="jsmsgSenderDelBt link_dele" data-type="10" onclick="return false;">删除</a></div>'
        parent.$(".send").find('.tab_content').eq(0).find('.jsMsgSendTab').css('display','none');
        parent.$(".send").css('display','block');
        parent.$(".msg_sender_area").css('display','none');

        parent.$(".resPicAndText").eq("${idx}").find('.jsMsgSendTab').css('display','none');
        parent.$(".send").find('.tab_content').eq(0).find('.inner').append(mediaHtml);
        parent.$(".resPicAndText").eq("${idx}").append(mediaHtml);
        parent.$(".msg_sender_media").find(".has_first_cover").removeClass("selected")
        parent.$("#menuList").find(".current").attr('data-mid',mid);
        parent.$("#menuList").find(".current").attr('data-type','TYPE_media_id');
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);//关闭窗口
    }
})