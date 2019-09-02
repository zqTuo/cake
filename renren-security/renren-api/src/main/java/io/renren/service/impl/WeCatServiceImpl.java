package io.renren.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.MessageUtil;
import io.renren.entity.WxuserEntity;
import io.renren.service.WeCatService;
import io.renren.service.WechatAuthService;
import io.renren.service.WxuserService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/2 22:49.
 */
@Service("weCatService")
public class WeCatServiceImpl implements WeCatService {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private WxuserService wxuserService;
    @Resource
    private WechatAuthService wechatAuthService;

    @Override
    public String weixinPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> requestMap = MessageUtil.parseXml(request);

        // 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        // 消息内容
        String content = requestMap.get("Content");
        //发起时间
        String timeStamp = requestMap.get("CreateTime");
        //接受的图片
        String picUrl = requestMap.get("PicUrl");
        String MediaId = requestMap.get("MediaId");
        //MsgId
        String MsgId = requestMap.get("MsgId");

        log.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType +",picUrl is："+picUrl + "，MediaId：" + MediaId);

        log.info("正在查询该用户...");
        WxuserEntity user = wxuserService.selectUserByOpenId(fromUserName);

        String accessToken = wechatAuthService.getLastAccessToken();


        String userName = "";
        String headimgurl = "";
        int subscribe = 0;
        String unionid = "";

        if (null != user) {
            log.info("用户：" + user.getUserName());
            userName = user.getUserName();
            headimgurl = user.getUserHead();
            unionid = user.getUserUnionid();
        }else{
            log.info("未知用户！");
            //回复进入猫迹商城
            userName="游客";
            try {
                JSONObject res = wechatAuthService.getBaseUserInfo(accessToken,fromUserName);
                log.info("获取到用户信息：" + res.toString());
                if(res != null && res.containsKey("nickname")){
                    userName = res.getString("nickname");
                    userName = StringEscapeUtils.escapeJava(userName);
                    headimgurl = res.getString("headimgurl");
                    unionid = res.getString("unionid");
                    subscribe = res.getInteger("subscribe");
                }
            } catch (Exception e) {
                userName="游客";
                log.error("=============新关注用户获取用户信息失败：" , e);
                e.printStackTrace();
            }
        }



    }
}
