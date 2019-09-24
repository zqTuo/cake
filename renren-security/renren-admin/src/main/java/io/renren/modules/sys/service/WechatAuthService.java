package io.renren.modules.sys.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.config.WechatConfig;
import io.renren.common.utils.*;
import io.renren.modules.sys.entity.wxMenu.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
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
//        Map<String,String> params = new HashMap<>();
//        params.put("access_token","24_hha9zgCBHad9CkEAHkVj263lfNopubgQzZHjIPqB5doLGIL5xUuCPEvq007bny8g2w-zqkpxR7x2onAbnUny6NHTFZbTRbZPu28kPuXbeSUZsadSbYXxtQDWEyY8pwgZXaM-7gGeqUlfVLbNRKDhAFAKDW");
//        params.put("type", "jsapi");
//        String ticketJson = HttpClientTool.getData("https://api.weixin.qq.com/cgi-bin/ticket/getticket", params);
//        System.out.println(ticketJson);

        ViewButton btn11 = new ViewButton();
        btn11.setName("蛋糕预订");
        btn11.setType("view");
        btn11.setUrl("/cake.html");

        ViewButton btn21 = new ViewButton();
        btn11.setName("课程预订");
        btn11.setType("view");
        btn11.setUrl("/course/index.html");

        CommonButton btn31 = new CommonButton();
        btn31.setName("我的客服");
        btn31.setType("click");
        btn31.setKey("31");

//        CommonButton btn32 = new CommonButton();
//        btn32.setName("售前售后");
//        btn32.setType("click");
//        btn32.setKey("32");

        //主菜单
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("咨询");
        mainBtn3.setSub_button(new CommonButton[] { btn31});

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { btn11, btn21, mainBtn3 });


        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(menu));
        System.out.println(params);
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

    /**
     * 自定义菜单的创建方法
     * @param menu
     * @return
     */
    public int createMenu(Menu menu) {
        int result = 0;
        String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", getLastAccessToken());
        // 将菜单对象转换成map字符串
        JSONObject params = JSONObject.parseObject(JSONObject.toJSONString(menu));
        log.info("创建菜单参数：" + params);
        // 调用接口创建菜单
        String res = HttpClientTool.postHttp(url,params);
        JSONObject jsonObject = JSONObject.parseObject(res);

        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }

    public JSONObject getMaterialOnlineByID(String mid) {
        String material_get_url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";

        // 拼装创建菜单的url
        String url = material_get_url.replace("ACCESS_TOKEN", getLastAccessToken());
        // 调用接口创建菜单
        Map<String, String> params = new HashMap<String, String>();
        params.put("media_id", mid);
        String result = HttpClientTool.postData(url, params, "UTF-8","POST");
        JSONObject jsonObject=new JSONObject();
        if (null != result) {
            try {
                jsonObject= JSON.parseObject(result);
                if (0 != jsonObject.getIntValue("errcode")) {
                    log.error("即时获取素材失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
                }
            } catch (Exception e) {
                jsonObject.put("image",result);
            }
        }

        return jsonObject;
    }

    public JSONObject addMaterialEver(String fileurl, String type, int mediaType) {
        JSONObject resultJSON=new JSONObject();
        try {
            File file = new File(fileurl);
            String token = getLastAccessToken();
            //上传素材
            String path = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + token + "&type=" + type;
            if(mediaType == 1){
                path = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + token + "&type=" + type;
            }
            String result = HttpClientTool.connectHttpsByPost(path, file);
            result = result.replaceAll("[\\\\]", "");
            log.info("result:" + result);
            if(StringUtils.isEmpty(result)){
                resultJSON = JSONObject.parseObject(result);
                if (resultJSON.get("media_id") != null) {
                    log.info("上传" + type + "永久素材成功");
                } else {
                    Integer errcode = (Integer) resultJSON.get("errcode");
                    log.error("即时上传永久素材失败 errcode:{} errmsg:{}", errcode, resultJSON.getString("errmsg"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSON;
    }

    public JSONObject pushMenu(String requestJSON) {
        String material_get_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

        // 拼装创建菜单的url
        String url = material_get_url.replace("ACCESS_TOKEN", getLastAccessToken());

        JSONObject params = JSONObject.parseObject(requestJSON);
        log.info("创建菜单参数：" + params);
        // 调用接口创建菜单
        String res = HttpClientTool.postHttp(url,params);
        log.info("创建菜单请求结果：" + res);
        return JSONObject.parseObject(res);
    }

    public JSONObject getMenuOnline() {
        String menu_get_url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";

        // 拼装创建菜单的url
        String url = menu_get_url.replace("ACCESS_TOKEN", getLastAccessToken());
        // 调用接口创建菜单
        String res = HttpClientTool.postData(url, null, "UTF-8","POST");
        log.info("菜单请求结果：" + res);

        JSONObject result = JSONObject.parseObject(res);
        if (null != result) {
            if (0 != result.getIntValue("errcode")) {
                log.error("即时获取菜单失败 errcode:{} errmsg:{}", result.getIntValue("errcode"), result.getString("errmsg"));
            }
        }

        return result;
    }

    public JSONObject getMaterialOnline(String type, String offset, String count) {
        String material_get_url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

        // 拼装创建菜单的url
        String url = material_get_url.replace("ACCESS_TOKEN", getLastAccessToken());
        // 调用接口创建菜单
        Map<String, String> params = new HashMap<String, String>();
        params.put("type",type);
        params.put("offset", offset);
        params.put("count", count);

        String result = HttpClientTool.postData(url, params, "UTF-8","POST");
        JSONObject jsonObject=new JSONObject();
        if (null != result) {
            jsonObject= JSON.parseObject(result);
            if (0 != jsonObject.getIntValue("errcode")) {
                log.error("即时获取素材失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return jsonObject;
    }


}
