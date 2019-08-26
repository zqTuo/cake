//详情图片上传预览    IE是用了滤镜。
function onePicUpLoad(file,picNum,width,height) {
    onePicUpload.previewImage(file,picNum,width,height);
}

var onePicUpload={
    previewImage:function (file,picNum,width,height) {
        var MAXWIDTH  = width;
        var MAXHEIGHT = height;
        var num=picNum;
        var div = $("#preview"+num);
        var self=this;
        if (file.files && file.files[0])
        {
            div.innerHTML ='<img id="imgProDetail'+num+'" onclick=$("#previewImg'+num+'").click()>';
            var img = document.getElementById("imgProDetail"+num);
            img.onload = function(){
                var rect = self.clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width  =  rect.width;
                img.height =  rect.height;
                img.style.marginTop = rect.top+'px';
            }
            var file=file.files[0];
            self.uploadImg("/cake-admin/admin/upload/uploadFile.do",file,img);
        }
        else //兼容IE
        {
            var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
            file.select();
            var src = document.selection.createRange().text;
            div.innerHTML = '<img id=imgProDetail'+num+'>';
            var img = document.getElementById('imgProDetail'+num);
            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
            div.innerHTML = "<div id=divhead"+num+" style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
        }
    },
    clacImgZoomParam:function (maxWidth, maxHeight, width, height ) {
        var param = {top:0, left:0, width:width, height:height};
        if( width>maxWidth || height>maxHeight ){
            rateWidth = width / maxWidth;
            rateHeight = height / maxHeight;

            if( rateWidth > rateHeight ){
                param.width =  maxWidth;
                param.height = Math.round(height / rateWidth);
            }else{
                param.width = Math.round(width / rateHeight);
                param.height = maxHeight;
            }
        }
        param.left = Math.round((maxWidth - param.width) / 2);
        param.top = Math.round((maxHeight - param.height) / 2);
        return param;
    },
    uploadImg:function (opt, file,img) {
        // 验证通过图片异步上传
        var url = opt;
        var data = new FormData();
        data.append("file", file);
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                img.src = data.result.fileUrl;
            },
            error: function (e) {
                alert("上传失败");
            }
        })
    }
}
