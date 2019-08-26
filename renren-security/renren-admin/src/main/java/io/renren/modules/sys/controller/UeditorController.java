package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSONException;
import com.baidu.ueditor.ActionEnter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/26 18:17
 */
@Controller
@CrossOrigin
@RequestMapping("/ueditor")
public class UeditorController {

    /**
     * 用于处理关于ueditor插件相关的请求
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = "/exec")
    public String exec( HttpServletRequest request) throws UnsupportedEncodingException, JSONException {
        request.setCharacterEncoding("utf-8");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        return new ActionEnter( request, rootPath ).exec();
    }

    @RequestMapping("/config")
    public String config(String action, HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/statics/libs/ueditor/1.4.3/jsp/config.json";
    }

}
