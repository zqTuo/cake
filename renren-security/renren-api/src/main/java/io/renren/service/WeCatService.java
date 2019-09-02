package io.renren.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/9/2 22:49.
 */
public interface WeCatService {
    /**
     * 对微信公众号发过来的消息进行处理
     * @param request
     * @return
     */
    String weixinPost(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
