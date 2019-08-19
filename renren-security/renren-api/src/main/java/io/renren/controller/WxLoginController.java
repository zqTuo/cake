/**
 * Copyright (c) 2016-2019 炫酷游开源 All rights reserved.
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren.controller;


import com.alibaba.fastjson.JSONObject;
import io.renren.config.LoginConfig;
import io.renren.entity.TokenEntity;
import io.renren.entity.WxuserEntity;
import io.renren.service.TokenService;
import io.renren.service.WxuserService;
import io.renren.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/wx")
@Api(tags="微信登录接口")
public class WxLoginController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Value("${project.url_pre}")
    private String url_pre;
    @Resource
    private LoginConfig loginConfig;

    @Autowired
    private WxuserService userService;
    @Autowired
    private TokenService tokenService;


    @ApiOperation(value = "请求微信登录")
    @GetMapping("/mallAuth")
    public String auth() throws IOException {
        return "redirect:" + getUrl();
    }

    private String getUrl() throws UnsupportedEncodingException {
        String content = Constant.WECHAT_LOGIN_SALT + DateUtil.getYYYYMMdd();
        MD5 md5 = new MD5();
        String cotent2Aes = md5.toDigest(content);

        String url = URLEncoder.encode(url_pre + "/wx/wxAuthRedirect","UTF-8");
        cotent2Aes = URLEncoder.encode(cotent2Aes,"UTF-8");
        String tourl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + loginConfig.getWeCatAppId() + "&redirect_uri=" + url
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

        log.info("state是：" + state);
        log.info("code是：" + code);
        if (org.springframework.util.StringUtils.isEmpty(code) || org.springframework.util.StringUtils.isEmpty(state)) {
            log.error("接收code错误，code为空");
            return "";
        }

        //*************************   获取openid   ***********************************
        JSONObject openidJson = getWebAccessTokenJson(loginConfig.getWeCatAppId(), loginConfig.getWeCatAppSecret(), code);
        if(!openidJson.containsKey("openid")) {
            log.error("未请求到openid，用户授权失败，" + openidJson);
            return "redirect:" + url_pre;
        }
        String openid = openidJson.getString("openid");//获取openid
        String accessToken = openidJson.getString("access_token");


        //******************** 获取用户个人信息 ************************

        JSONObject userinfoJson = getBaseUserInfo(accessToken, openid,unConcern);
        log.info("获取用户个人信息："+userinfoJson);

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
            user = WxuserEntity.builder().userName(userName).userHead(headimgurl).userMember(0).userUnionid(unionid)
                    .userLastip(ip).userLastlogintime(new Date()).userState(1).createTime(new Date()).build();

            userService.save(user);

        }else {
            user.setUserName(userName);
            user.setUserHead(headimgurl);
            user.setUserLastlogintime(new Date());
            user.setUserLastip(ip);
            userService.updateById(user);
        }

//        SessionUser sessionUser = SessionUser.builder().id(user.getId()).build();
//        request.getSession().setAttribute("sessionUser", sessionUser);

        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());
        String token = tokenEntity.getToken();
        //存入cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setDomain(url_pre);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:" + url_pre;
    }

    /**
     * 通过code获取网页access_token
     * @param appID
     * @param appSecret
     * @param code
     * @return
     */
    private JSONObject getWebAccessTokenJson(String appID, String appSecret, String code){
        String url = loginConfig.getWeCatAccessTokenUrl();

        Map<String,String> param = new HashMap<>();
        param.put("appid",appID);
        param.put("secret",appSecret);
        param.put("code",code);
        param.put("grant_type","authorization_code");
        String resultStr = HttpClientTool.postData(url, param, "UTF-8", "GET");

        return JSONObject.parseObject(resultStr);
    }

    /**
     *
     * 通过静默获取用户信息
     *
     * @param access_token
     * @param openid
     * @return
     */
    private JSONObject getBaseUserInfo(String access_token, String openid,int unConcern) {
        String url = loginConfig.getWeCatUserInfoUrl();

        Map<String,String> param = new HashMap<>();
        param.put("access_token",access_token);
        param.put("openid",openid);
        param.put("lang","zh_CN");
        String resultStr = HttpClientTool.postData(url, param, "UTF-8", "GET");

        return JSONObject.parseObject(resultStr);
    }
}
