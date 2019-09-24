package io.renren.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.*;
import io.renren.dto.WeixinMessageDto;
import io.renren.entity.TransInfo;
import io.renren.entity.WxuserEntity;
import io.renren.service.WeCatService;
import io.renren.service.WechatAuthService;
import io.renren.service.WxuserService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

        log.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType
                +",picUrl is："+picUrl + "，MediaId：" + MediaId + ",MsgId:" + MsgId + ",content:" + content);

        log.info("正在查询该用户...");
        WxuserEntity user = wxuserService.selectUserByOpenId(fromUserName);

        String accessToken = wechatAuthService.getLastAccessToken();

        if (null == user) {
            log.info("新用户与公众号交互");
            //回复进入猫迹商城
            JSONObject res = wechatAuthService.getBaseUserInfo(accessToken,fromUserName);
            log.info("获取到用户信息：" + res.toString());
            if(res.containsKey("nickname")){
                String userName = res.getString("nickname");
                userName = StringEscapeUtils.escapeJava(userName);
                String headimgurl = res.getString("headimgurl");
                String unionid = res.getString("unionid");
                int subscribe = res.getInteger("subscribe");

                String name = new SafeHtml().convert(userName);
                userName = StringEscapeUtils.escapeJava(name);
                String ip = HttpUtil.getIpAddr(request);
                user = WxuserEntity.builder().userName(userName).userHead(headimgurl).userMember(0).userOpenid(fromUserName)
                        .userUnionid(unionid).subscribe(subscribe)
                        .userLastip(ip).userState(1)
                        .createTime(new Date()).build();

                wxuserService.save(user);
                log.info("新用户已写入数据库");
            }
        }

        boolean isGetPicAndText = false;
        String responseType="text";
        // 默认返回的文本消息内容

        //回复消息主体
        String respMessage = null;

        //自动回复 -消息主体
        WeixinMessageDto responseMsg = new WeixinMessageDto();
        responseMsg.setToUserName(fromUserName);
        responseMsg.setFromUserName(toUserName);
        responseMsg.setCreateTime(new Date().getTime() + "");
        responseMsg.setMsgType(msgType);

        // 文本消息
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            log.info("搜索文本：" + content);
            responseType = "picAndText";
            responseMsg.setMsgType("news");
//            WeixinKeyWord keyWord = keyWorldService.findByKeyWord("%" + content + "%"); //取该关键字匹配到的最新添加的记录

        }
//        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
//            respContent = "已收到您的图片，客服会第一时间查看的哦";
//            text.setContent(respContent);
//            text.setMsgType("text");
//        }
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) { // 事件推送
            String eventType = requestMap.get("Event");// 事件类型
            log.info("事件类型:" + eventType);

            // 自定义菜单点击事件
            if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

                // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                String eventKey = requestMap.get("EventKey");
                if (eventKey.equals("cakekf")) { //客服

                    //发送一条消息给用户
                    JSONObject msgJson = new JSONObject();
                    msgJson.put("touser", fromUserName);
                    msgJson.put("msgtype", "text");
                    JSONObject json = new JSONObject();

                    //判断是否在工作时间内
                    Calendar rightNow = Calendar.getInstance();
                    int hour = rightNow.get(Calendar.HOUR_OF_DAY);
                    if(hour >= 10 && hour < 22){ // 工作时间内
                        json.put("content", "(｡･∀･)ﾉﾞ嗨！今天又是元气满满的一天！\n" +
                                "系统已为您安排客服对接，请耐心等待...");
                    }else{ //非工作时间
                        json.put("content", "哈喽，客服仙女们工作时间为10：00-22：00，" +
                                "休息时间回复较慢请耐心哟！咨询量较大的时候，您的消息可能要慢一丢丢~(/ω＼)");
                    }

                    msgJson.put("text", json);
                    wechatAuthService.sendMessageToUser(msgJson);

                    //获取在线客服
                    String kfAccount = ""; // 分配客服账号

                    JSONArray onlineKf = wechatAuthService.getOnlineKf();
                    for (int i = 0; i < onlineKf.size(); i++) {
                        JSONObject kf = onlineKf.getJSONObject(i);
                        log.info("当前客服：" + kf);

                        if(kf.getInteger("accepted_case") == 0){
                            //空闲客服可以接待
                            // 判断该用户是否已经安排过客服对接 实现单一客服永久绑定
                            if(user != null && StringUtils.isNotBlank(user.getKfAccount())){
                                log.info("转发至专属客服：" + user.getKfAccount());
                                kfAccount = user.getKfAccount();
                            }else{
                                log.info("专属客服正忙，分配给在线空闲其他客服");
                                kfAccount = kf.getString("kf_account");
                            }

                            break;
                        }
                    }

                    //需要注意，如果指定的客服没有接入能力(不在线、没有开启自动接入或者自动接入已满)，
                    // 该用户会被直接接入到指定客服，不再通知其它客服，
                    // 不会被其他客服接待。建议在指定客服时，
                    // 先查询客服的接入能力（获取在线客服接待信息接口），
                    // 指定到有能力接入的客服，保证客户能够及时得到服务。
                    TransInfo transInfo = new TransInfo();
                    transInfo.setKfAccount(kfAccount);
                    responseMsg.setTransInfo(transInfo);

                    //转发至客服系统
                    responseMsg.setMsgType("transfer_customer_service");
                }
            }
        }

        try {
            respMessage = MessageUtil.picAndTextMessageToXml(responseMsg);

            if("text".equals(responseType)){
                respMessage = respMessage.replaceAll("<ArticleCount>0</ArticleCount>","");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自动回复的消息转码失败：" + e.getMessage());
        }


        log.info("正在回复:" + respMessage);
        return respMessage;
    }
}
