package io.renren.dto;

import io.renren.entity.Image;
import io.renren.entity.TransInfo;
import io.renren.entity.item;

import java.util.List;

/**
 * Created by 24537 on 2017/9/5.
 */
public class WeixinMessageDto {
    // 接收方帐号（收到的OpenID）
    private String ToUserOpenID;
    // 接收方帐号（用户名）
    private String ToUserName;
    // 开发者微信号
    private String FromUserName;
    // 消息创建时间 （整型）
    private String CreateTime;
    // 消息类型（text/music/news）
    private String MsgType;
    // 文本消息 消息内容
    private String Content;
    //图文消息 发布显示的条数
    private int ArticleCount;
    private Image Image;
    private TransInfo TransInfo;
    private List<item> Articles;

    public String getToUserOpenID() {
        return ToUserOpenID;
    }

    public void setToUserOpenID(String toUserOpenID) {
        ToUserOpenID = toUserOpenID;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }

    public TransInfo getTransInfo() {
        return TransInfo;
    }

    public void setTransInfo(TransInfo transInfo) {
        TransInfo = transInfo;
    }

    public List<item> getArticles() {
        return Articles;
    }

    public void setArticles(List<item> articles) {
        Articles = articles;
    }

    public WeixinMessageDto() {
    }

    public WeixinMessageDto(String toUserOpenID, String toUserName, String fromUserName, String createTime, String msgType, String content, int articleCount, Image image, TransInfo transInfo, List<item> articles) {
        ToUserOpenID = toUserOpenID;
        ToUserName = toUserName;
        FromUserName = fromUserName;
        CreateTime = createTime;
        MsgType = msgType;
        Content = content;
        ArticleCount = articleCount;
        Image = image;
        TransInfo = transInfo;
        Articles = articles;
    }
}
