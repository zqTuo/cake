package io.renren.service;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.config.WechatConfig;
import io.renren.common.utils.HttpClientTool;
import io.renren.common.utils.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        JSONObject params = new JSONObject();
        params.put("access_token",getLastAccessToken());
        params.put("type", "jsapi");
        JSONObject ticketJson = HttpUtil.postByJson(wechatConfig.getTicketUrl(), params);
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
}
