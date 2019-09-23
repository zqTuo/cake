/**
 *
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren.interceptor;


import io.renren.annotation.Login;
import io.renren.common.exception.RRException;
import io.renren.entity.TokenEntity;
import io.renren.service.TokenService;
import io.renren.service.WechatAuthService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private TokenService tokenService;
    @Value("${project.url_pre}")
    private String url_pre;
    @Resource
    private WechatAuthService wechatAuthService;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }


        //token为空
        boolean isRedirectToLogin = false;
        if(StringUtils.isBlank(token)){
            //判断是否为ajax请求，默认不是
            boolean isAjaxRequest = false;
            if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
                isAjaxRequest = true;
            }
            if(isAjaxRequest){
                throw new RRException("token不能为空",-4);
            }else{
                isRedirectToLogin = true;
            }
        }

        //查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            //判断是否为ajax请求，默认不是
            boolean isAjaxRequest = false;
            if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
                isAjaxRequest = true;
            }
            if(isAjaxRequest){
                throw new RRException("token失效，请重新登录",-4);
            }else{
                //重定向登录
                isRedirectToLogin = true;
            }
        }

        if(isRedirectToLogin){
            //重定向登录
            String url = ServletRequestUtils.getStringParameter(request, "p", "");
            if (StringUtils.isBlank(url)) {
                url = url_pre + request.getServletPath(); //可以获得拦截的链接原路径 不含参数
            }

            String queryurl=request.getQueryString(); //获得拦截链接后的参数
            if(null!=queryurl){
                url+="?"+queryurl;
            }

            log.info("未登录重定向原始链接：" + url);
            url = wechatAuthService.getUrl(url);
            log.info("微信授权登录链接：" + url);
            response.sendRedirect(url);
            return false;
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
