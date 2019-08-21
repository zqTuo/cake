package io.renren.controller;

import io.renren.common.utils.R;
import io.renren.common.config.WechatConfig;
import io.renren.entity.JsWechatConfig;
import io.renren.service.WechatAuthService;
import io.renren.common.utils.ShareSignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 功能描述: <br>
 * 微信公众号获取票据控制器 JS-SDK
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 12:34
 */
@RestController
@RequestMapping("/api/wx")
@Api(tags="微信接口控制器")
public class WxTicketController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Resource
    private WechatConfig wechatConfig;
    @Resource
    private WechatAuthService wechatAuthService;

//    @Login
    @GetMapping("/getTicket")
    @ApiOperation(value = "获取微信票据接口",response = JsWechatConfig.class)
    public R getTicket(HttpServletRequest request){
        String ticket = wechatAuthService.getTicket();
        Map<String,String> map = ShareSignUtil.sign(ticket, request.getRequestURL().toString());

        JsWechatConfig config = JsWechatConfig.builder().appId(wechatConfig.getWeCatAppId())
                .noncestr(map.get("nonceStr")).timestamp(map.get("timestamp"))
                .appId(wechatConfig.getWeCatAppId()).signature(map.get("signature"))
                .url(request.getRequestURL().toString()).build();

        return R.ok().put("data",config);
    }


}
