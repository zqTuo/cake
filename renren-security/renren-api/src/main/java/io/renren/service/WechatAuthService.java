package io.renren.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.config.WechatConfig;
import io.renren.common.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 13:38
 */
@Service
public class WechatAuthService {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private WechatConfig wechatConfig;
    @Resource
    private IRedisService redisService;
    @Resource
    private AESHelper aesHelper;
    @Value("${project.url_pre}")
    private String url_pre;

    /**
     * 获取jsapi_ticket 调用微信JS接口的临时票据
     * @return
     */
    public String getTicket() {
        Map<String,String> params = new HashMap<>();
        params.put("access_token",getLastAccessToken());
        params.put("type", "jsapi");
        String ticket = HttpClientTool.getData("https://api.weixin.qq.com/cgi-bin/ticket/getticket", params);
        JSONObject ticketJson = JSONObject.parseObject(ticket);
        log.info("========== 微信ticket请求结果：" + ticketJson);
        if (ticketJson.get("errcode")!= null && ticketJson.getInteger("errcode") == 0){
            return ticketJson.getString("ticket");
        }
        return null;
    }

    public String getLastAccessToken(){
        Object access_token = redisService.get("accessToken");
        if(access_token != null){
            log.info("======== 已从redis从获取access_token：" + access_token);
            return access_token.toString();
        }

        String url = wechatConfig.getAccessTokenUrl();

        Map<String,String> param = new HashMap<>();
        param.put("appid",wechatConfig.getWeCatAppId());
        param.put("secret",wechatConfig.getWeCatAppSecret());
        param.put("grant_type","client_credential");
        String resultStr = HttpClientTool.postData(url, param, "UTF-8", "GET");
        JSONObject res = JSONObject.parseObject(resultStr);
        log.info("====== 新access_token获取结果：" + res);
        if(res != null && res.containsKey("access_token")){
            // 存入redis持久化
            redisService.set("accessToken",res.getString("access_token"),5000L);
            return res.getString("access_token");
        }
        return "";
    }

    public String getUrl(String o_url) throws UnsupportedEncodingException {
        String content = Constant.WECHAT_LOGIN_SALT + DateUtil.getYYYYMMdd();
        MD5 md5 = new MD5();
        String cotent2Aes = md5.toDigest(content);
        if(org.apache.commons.lang.StringUtils.isNotBlank(o_url)){
            log.info("原路径：" + o_url);
            if(o_url.contains("&")){
                String param = o_url.split("[?]")[1];
                log.info("原链接参数：" + param);
                param = aesHelper.AESEncode(param);
                log.info("原链接参数密文：" + param);
                o_url = o_url.split("[?]")[0] + "?paramCode=" + URLEncoder.encode(param,"UTF-8");
                log.info("加密后原链接：" + o_url);
            }
            o_url = "?url=" + o_url;
        }
        String url = URLEncoder.encode(url_pre + "/cake-api/wx/wxAuthRedirect" + o_url,"UTF-8");
        cotent2Aes = URLEncoder.encode(cotent2Aes,"UTF-8");
        String tourl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wechatConfig.getWeCatAppId() + "&redirect_uri=" + url
                + "&response_type=code&scope=snsapi_userinfo&state=" + cotent2Aes + "&connect_redirect=1#wechat_redirect";

        log.info("tourl:" + tourl);

        return tourl;
    }

    /**
     *
     * 通过静默获取用户信息
     *
     * @param access_token
     * @param openid
     * @return
     */
    public JSONObject getBaseUserInfo(String access_token, String openid) {
        String url = wechatConfig.getWeCatUserInfoUrl();

        Map<String,String> param = new HashMap<>();
        param.put("access_token",access_token);
        param.put("openid",openid);
        param.put("lang","zh_CN");
        String resultStr = HttpClientTool.postData(url, param, "UTF-8", "GET");

        return JSONObject.parseObject(resultStr);
    }

    /**
     * 从微信服务器下载多媒体文件
     *
     * @author qincd
     * @date Nov 6, 2014 4:32:12 PM
     */
    public String downloadMediaFromWx(String accessToken, String mediaId, String fileSavePath, String fileName) throws IOException {
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(mediaId)) return null;

        String requestUrl = wechatConfig.getDownload_media_url().replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        InputStream in = conn.getInputStream();

        File dir = new File(fileSavePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!fileSavePath.endsWith(File.separator)) {
            fileSavePath += File.separator;
        }

        fileSavePath += fileName;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileSavePath));
        byte[] data = new byte[1024];
        int len = -1;

        while ((len = in.read(data)) != -1) {
            bos.write(data,0,len);
        }

        bos.close();
        in.close();
        conn.disconnect();

        return fileSavePath;
    }

    public static void main(String[] args) {
        JSONObject msgJson = new JSONObject();
        msgJson.put("touser", "oSAxEwolwMog2nOrX0Rb24lSVGF0");
        msgJson.put("msgtype", "text");
        JSONObject json = new JSONObject();
        json.put("content", "(｡･∀･)ﾉﾞ嗨！今天又是元气满满的一天！\n" +
                "系统已为您安排客服对接，请耐心等待...");
        msgJson.put("text", json);


        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=25_y4AHodL1QuzqnsPVzktH6zMYQMtOptV27B-EGQaWzxrNOMFZO8F2HtejkbhAh2gs9v_3Z4F6zdJlNrQQz-1oOy0eC64j9jrrm0JxqFk_qVx-zTtrM8n8Mh_1OKD-wKEeCr4IAl9NHwwzI2WUPOEfAFAUTJ";
        JSONObject result = HttpClientTool.httpRequest(url,"POST",msgJson.toString());
        log.info("发送消息result:" + result);
    }

    /**
     * 获取在线客服会话状态列表
     * @return
     */
    public JSONArray getOnlineKf() {
        String url = wechatConfig.getOnline_kf_list();

        Map<String,String> params = new HashMap<>();
        params.put("access_token",getLastAccessToken());

        String resultStr = HttpClientTool.getData(url, params);
        JSONObject res = JSONObject.parseObject(resultStr);
        log.info("========== 微信在线客服会话状态列表请求结果：" + res);
        return res.getJSONArray("kf_online_list");
    }

    public void sendMessageToUser(JSONObject params) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getLastAccessToken();

        log.info("发送消息参数:" + params);
        JSONObject result = HttpClientTool.httpRequest(url,"POST",params.toString());
        log.info("发送消息result:" + result);
    }
}
