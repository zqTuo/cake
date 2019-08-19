/**
 * Copyright (c) 2016-2019 炫酷游开源 All rights reserved.
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren.controller;


import com.alibaba.fastjson.JSONObject;
import io.renren.annotation.Login;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.config.LoginConfig;
import io.renren.entity.WxuserEntity;
import io.renren.form.LoginForm;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import io.renren.service.WxuserService;
import io.renren.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
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
            return null;
        }

        //*************************   获取openid   ***********************************
        JSONObject openidJson = getWebAccessTokenJson(loginConfig.getWeCatAppId(), loginConfig.getWeCatAppSecret(), code);
        if(!openidJson.containsKey("openid")) {
            log.error("未请求到openid，用户授权失败，" + openidJson);
            return "redirect:" + url_pre;
        }
        String openid = openidJson.getString("openid");//获取openid
        String accessToken = openidJson.getString("access_token");


        //***********************************获取用户个人信息********************************************

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

        //***********************************写入用户信息到数据库********************************************
        WxuserEntity user = userService.selectUserByOpenId(openid);
        String ip = HttpUtil.getIpAddr(request);


        int subscribe = 1; //默认已关注
        if(unConcern == 1){
            subscribe = 0; //静默登录 说明已关注  如果是授权登陆 说明用户没有关注
        }

        if (null == user) {
            //通过unionid查该用户是否已经在小程序登录过
            User userMini = userService.selectUserByUnionid(unionid);
            if(userMini != null){//合并
                userMini.setUserName(userName);
                userMini.setUserOpenid(openid);
                userMini.setUserImgUrl(headimgurl);
                userMini.setCreateTime(new Date());
                userMini.setUserLastLoginTime(new Date());
                userMini.setUserFirstFlag(0);
                userMini.setLastIp(ip);
                userMini.setStatus(1);
                userMini.setPtNewFlag(0);
                userMini.setFollowFlag(subscribe);
                userService.updateUser(userMini);
                log.info("已将小程序用户："+userMini.getId()+"统一到公众号信息");
                user = userMini;
            }else{
                user = new User();
                user.setUserName(userName);
                user.setUserOpenid(openid);
                user.setUserImgUrl(headimgurl);
                user.setCreateTime(new Date());
                user.setUserLastLoginTime(new Date());
                user.setUserFirstFlag(0);
                user.setLastIp(ip);
                user.setStatus(1);
                user.setPtNewFlag(0);
                user.setUnionid(unionid);
                user.setFollowFlag(subscribe);

                userService.createUser(user);
            }
        }else {
            //通过unionid查该用户是否已经在小程序登录过
            user.setUserName(userName);
            user.setUserImgUrl(headimgurl);
            user.setUserLastLoginTime(new Date());
            user.setLastIp(ip);
            user.setUnionid(unionid);
            user.setFollowFlag(subscribe);
            userService.updateUser(user);
        }

        if(a == 1){
            userName = userinfoJson.getString("nickname");
        }

        SessionUser sessionUser = new SessionUser(user.getId(), userName,null, user.getUserImgUrl(),openid,subscribe,a);
        request.getSession().setAttribute("sessionUser", sessionUser);

        if(tourl.contains("paramCode")){
            String encodeParam = tourl.split("=")[1];
            log.info("参数密文：" + encodeParam);
            encodeParam = AESHelper.AESDecode(encodeParam);
            log.info("参数明文：" + encodeParam);
            tourl = tourl.split("[?]")[0] + "?" + encodeParam;
        }
        log.info("最终跳转地址：" + tourl);
        tourl = URLDecoder.decode(tourl, "UTF-8");
        if (!StringUtils.isEmpty(tourl)) {
            if(user.getStatus() ==0){
                //黑名单用户
                log.info("用户："+user.getId()+"已被禁用");
                response.setContentType("text/plain; charset=utf-8");
                response.setContentType("text/html; charset=utf-8");
                response.getWriter().write("   <span style=\"font-size:18px;color:red;\">您已被加入猫迹黑名单！禁止进入！</span>");
                return;
            }
            if(a == 0){
                response.sendRedirect(tourl);
            }else{
                try {

                    tourl = tourl + "?subscribe="+DESHelper.encrypt(String.valueOf(subscribe))+
                            "&userName=" + DESHelper.encrypt(name) +
                            "&userImgUrl=" + DESHelper.encrypt(user.getUserImgUrl()) +
                            "&openid=" + DESHelper.encrypt(openid)+
                            "&reurl="+DESHelper.encrypt(reurl);
                } catch (Exception e) {
                    log.error("砸金蛋DES加密失败："+e.getMessage());
                    e.printStackTrace();
                }
                log.info("砸金蛋活动跳转中.....  " +tourl);
                response.sendRedirect(tourl);
            }
        }else {
            return;
        }
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
        if(unConcern == 0){
            accessToken = getAccessTokenJson(AppID, Configure.getAppSecret(),request).getString("access_token");//获取静默登录的accesstoken
            userinfoJson = WeixinUtil.getBaseUserInfo(accessToken, openid);
            if(userinfoJson.getInteger("errcode") != null && userinfoJson.getInteger("errcode") != 0){
                if(userinfoJson.getInteger("errcode") == 40001){
                    accessToken = getAccessTokenBySimple(Configure.getAppid(), Configure.getAppSecret(),request).getString("access_token");
                    userinfoJson = WeixinUtil.getBaseUserInfo(accessToken, openid);
                }
            }
        }else{
            accessToken = openidJson.getString("access_token");//获取accessToken
            userinfoJson = WeixinUtil.getWebUserInfo(accessToken, openid);
        }


        String url = loginConfig.getWeCatUserInfoUrl();

        Map<String,String> param = new HashMap<>();
        param.put("access_token",access_token);
        param.put("openid",openid);
        param.put("lang","zh_CN");
        String resultStr = HttpClientTool.postData(url, param, "UTF-8", "GET");

        return JSONObject.parseObject(resultStr);
    }
}
