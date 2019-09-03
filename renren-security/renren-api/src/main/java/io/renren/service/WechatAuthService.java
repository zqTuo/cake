package io.renren.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.config.WechatConfig;
import io.renren.common.utils.HttpClientTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
        Map<String,String> params = new HashMap<>();
        params.put("access_token","24_hha9zgCBHad9CkEAHkVj263lfNopubgQzZHjIPqB5doLGIL5xUuCPEvq007bny8g2w-zqkpxR7x2onAbnUny6NHTFZbTRbZPu28kPuXbeSUZsadSbYXxtQDWEyY8pwgZXaM-7gGeqUlfVLbNRKDhAFAKDW");
        params.put("type", "jsapi");
        String ticketJson = HttpClientTool.getData("https://api.weixin.qq.com/cgi-bin/ticket/getticket", params);
        System.out.println(ticketJson);
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

    public void sendMessageToUser(Map<String,Object> params) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getLastAccessToken();

        log.info("发送消息参数:" + params);
        String result = HttpClientTool.postData(url, params, "UTF-8","POST");
        log.info("发送消息result:" + result);
    }
}
