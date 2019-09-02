package io.renren.controller;

import io.renren.common.utils.Signature;
import io.renren.service.WeCatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/2 22:45.
 */
@Controller
@RequestMapping("wx")
public class WxMsgController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Value("${weCat.dnbx_token}")
    private String dnbx_token;
    @Resource
    private WeCatService weCatService;

    @RequestMapping("/tokenAuth") //token认证控制器
    public void tokenAuth(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");  //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        boolean isGet = request.getMethod().toLowerCase().equals("get");

        try (PrintWriter out = response.getWriter()) {
            if (isGet) {
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");//随机字符串

                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (Signature.checkSignature(dnbx_token, signature, timestamp, nonce)) {
                    log.info("*********** Connect the weixin server is successful.************ ");
                    response.getWriter().write(echostr);
                } else {
                    log.error("======== Failed to verify the signature! ========== ");
                }
            } else {
                try {
                    String respMessage = weCatService.weixinPost(request, response);
                    out.write(respMessage);
                    log.info("The request completed successfully");
                } catch (Exception e) {
                    log.error("Failed to convert the message from weixin!" + e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Connect the weixin server is error.");
        }
    }
}
