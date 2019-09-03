package io.renren.entity;

/**
 * Created by kuro on 2017/9/5.
 * 名称必须小写 否则微信客服消息收不到
 */
public class item {
    //标题
    private String Title;
    //描述
    private String Description;
    //图片链接
    private String PicUrl;
    //点击跳转链接
    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
