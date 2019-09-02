/**
 * Copyright (c) 2016-2019 炫酷游开源 All rights reserved.
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren.controller;


import com.alibaba.fastjson.JSONObject;
import io.renren.common.config.WechatConfig;
import io.renren.common.utils.*;
import io.renren.entity.TokenEntity;
import io.renren.entity.WxuserEntity;
import io.renren.service.TokenService;
import io.renren.service.WechatAuthService;
import io.renren.service.WxuserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@Controller
@RequestMapping("/wx")
@Api(tags="微信登录接口")
public class WxLoginController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Value("${project.url_pre}")
    private String url_pre;
    @Resource
    private WechatConfig wechatConfig;
    @Resource
    private AESHelper aesHelper;

    @Autowired
    private WxuserService userService;
    @Autowired
    private TokenService tokenService;
    @Resource
    private WechatAuthService wechatAuthService;


    @ApiOperation(value = "请求微信登录")
    @GetMapping("/mallAuth")
    public String auth(HttpServletRequest request) throws IOException {
        String url = ServletRequestUtils.getStringParameter(request,"p","");
        return "redirect:" + getUrl(url);
    }

    private String getUrl(String o_url) throws UnsupportedEncodingException {
        String content = Constant.WECHAT_LOGIN_SALT + DateUtil.getYYYYMMdd();
        MD5 md5 = new MD5();
        String cotent2Aes = md5.toDigest(content);
        if(StringUtils.isNotBlank(o_url)){
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

    //授权回调地址
    @RequestMapping("/wxAuthRedirect")
    public String wxAuthRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int unConcern = ServletRequestUtils.getIntParameter(request, "unConcern", 0); //是否关注 0:关注  1：未关注
        String code = ServletRequestUtils.getStringParameter(request, "code", "");//微信返回回调地址带的code
        String state = ServletRequestUtils.getStringParameter(request, "state", "");//微信返回回调地址带的state
        String url = ServletRequestUtils.getStringParameter(request, "url", "");//微信返回回调地址带的state

        log.info("url是：" + url);
        log.info("state是：" + state);
        log.info("code是：" + code);
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(state)) {
            log.error("接收code错误，code为空");
            return "";
        }

        // 校验 防止跨站请求伪造攻击
        String o_state = Constant.WECHAT_LOGIN_SALT + DateUtil.getYYYYMMdd();
        MD5 md5 = new MD5();
        String cotent2Aes = md5.toDigest(o_state);

        log.info("微信登录，原state：" + cotent2Aes);
        if(!(cotent2Aes).equals(state)){
            log.info("信息不安全：" + cotent2Aes);
            return "redirect:" + url_pre;
        }

        //*************************   获取openid   ***********************************
        JSONObject openidJson = getWebAccessTokenJson(wechatConfig.getWeCatAppId(), wechatConfig.getWeCatAppSecret(), code);
        log.info("换取openid结果：" + openidJson);
        if(!openidJson.containsKey("openid")) {
            log.error("未请求到openid，用户授权失败，" + openidJson);
            return "redirect:" + url_pre;
        }
        String openid = openidJson.getString("openid");//获取openid
        String accessToken = openidJson.getString("access_token");


        //******************** 获取用户个人信息 ************************

        JSONObject userinfoJson = wechatAuthService.getBaseUserInfo(accessToken, openid);
        log.info("获取用户个人信息：" + userinfoJson);

        if (!userinfoJson.containsKey("nickname")) {
            log.error("未请求到用户信息：" + userinfoJson);
            return "redirect:" + url_pre;
        }

        String name = new SafeHtml().convert(userinfoJson.getString("nickname"));
        String userName = StringEscapeUtils.escapeJava(name);
        String headimgurl = userinfoJson.getString("headimgurl");
        String unionid = userinfoJson.getString("unionid");

        //************************ 写入用户信息到数据库 **********************
        WxuserEntity user = userService.selectUserByOpenId(openid);
        String ip = HttpUtil.getIpAddr(request);

        if (null == user) {
            user = WxuserEntity.builder().userName(userName).userHead(headimgurl).userMember(0).userOpenid(openid)
                    .userUnionid(unionid)
                    .userLastip(ip).userLastlogintime(new Date()).userState(1)
                    .createTime(new Date()).build();

            userService.save(user);

        }else {
            user.setUserName(userName);
            user.setUserHead(headimgurl);
            user.setUserLastlogintime(new Date());
            user.setUserLastip(ip);
            userService.updateById(user);
        }

        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());
        String token = tokenEntity.getToken();
        //存入cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setDomain(url_pre.replaceAll("http://","").replaceAll("https://",""));
        cookie.setPath("/");
        cookie.setMaxAge(36000);
        response.addCookie(cookie);

        if(StringUtils.isNotBlank(url)){
            if(user.getUserState() == 0){
                //黑名单用户
                log.info("用户："+user.getId()+"已被禁用");
                response.setContentType("text/plain; charset=utf-8");
                response.setContentType("text/html; charset=utf-8");
                response.getWriter().write("   <span style=\"font-size:18px;color:red;\">您已被加入猫迹黑名单！禁止进入！</span>");
                return "";
            }

            if(url.contains("paramCode")){
                String encodeParam = url.split("=")[1];
                log.info("参数密文：" + encodeParam);
                encodeParam = aesHelper.AESDecode(encodeParam);
                log.info("参数明文：" + encodeParam);
                url = url.split("[?]")[0] + "?" + encodeParam;
            }
            log.info("最终跳转地址：" + url);
            url = URLDecoder.decode(url, "UTF-8");
        }else{
            url = url_pre;
        }

        return "redirect:" + url;
    }

    /**
     * 通过code获取网页access_token
     * @param appID
     * @param appSecret
     * @param code
     * @return
     */
    private JSONObject getWebAccessTokenJson(String appID, String appSecret, String code){
        String url = wechatConfig.getAccessTokenByCodeUrl();

        Map<String,String> param = new HashMap<>();
        param.put("appid",appID);
        param.put("secret",appSecret);
        param.put("code",code);
        param.put("grant_type","authorization_code");

        log.info("请求参数：" + param);
        String resultStr = HttpClientTool.postData(url, param, "UTF-8", "GET");
        log.info("请求结果：" + resultStr);
        return JSONObject.parseObject(resultStr);
    }


}
