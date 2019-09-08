package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.sys.service.IRedisService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Clarence
 * @Description: 美团/大众点评 验券和销券工具类
 * @Date: 2019/8/31 18:46.
 */
@Component
public class MeiTuanUtil {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Value("${meituan.appKey}")
    private String appKey;
    @Value("${meituan.appSecret}")
    private String appSecret;
    @Value("${project.url_pre}")
    private String url_pre;
    @Value("${meituan.redirect_url}")
    private String redirect_url;
    @Value("${meituan.sessionUrl}")
    private String sessionUrl;
    @Value("${meituan.checkCodeUrl}")
    private String checkCodeUrl;
    @Resource
    private IRedisService redisService;

    public String getSessionUrl() throws UnsupportedEncodingException {

        String content = Constant.MEITUAN_SECCESS_SALT + DateUtil.getYYYYMMdd();
        MD5 md5 = new MD5();
        String cotent2Aes = md5.toDigest(content);
        cotent2Aes = URLEncoder.encode(cotent2Aes,"UTF-8");

        String redirect = url_pre + redirect_url;

        redirect = URLEncoder.encode(redirect,"UTF-8");
        return "https://e.dianping.com/dz-open/merchant/auth" +
                "?app_key=" + appKey + "&state=" + cotent2Aes + "&redirect_url=" + redirect;
    }

    public String getSession(){
        Object session = redisService.get("meituan_session");
        if(session != null){
            log.info("======== 已从redis从获取meituan_session：" + session);
            return session.toString();
        }
        return null;
    }

    /**
     * 通过code获取Session
     * @param code
     * @return
     */
    public JSONObject getWebSession(String code){

        Map<String,String> param = new HashMap<>();
        param.put("app_key",appKey);
        param.put("app_secret",appSecret);
        param.put("auth_code",code);
        param.put("grant_type","authorization_code");
        param.put("redirect_url",url_pre + redirect_url);

        log.info("换取session参数：" + param);
        String resultStr = HttpClientTool.getData(sessionUrl, param);

        return JSONObject.parseObject(resultStr);
    }


}
