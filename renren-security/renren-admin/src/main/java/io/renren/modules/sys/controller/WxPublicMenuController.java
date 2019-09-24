package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.MenuManagerService;
import io.renren.modules.sys.service.WechatAuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/9/23 9:43
 */
@RestController
@RequestMapping("sys/wxpublicmenu")
public class WxPublicMenuController {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Resource
    private MenuManagerService menuManagerService;
    @Resource
    private WechatAuthService wechatAuthService;

    /**
     * 创建并发布已经制定好的菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/createWxMenu")
    public R createWxMenu(){
        int result = wechatAuthService.createMenu(menuManagerService.getMenu());

        // 判断菜单创建结果
        if (0 == result) {
            log.info("菜单创建成功！");
            return R.ok();
        }else {
            log.info("菜单创建失败，错误码：" + result);
            return R.error();
        }
    }

    /**
     * 即时获取线上菜单
     * @param request
     * @return
     */
    @RequestMapping("/getMenu")
    @ResponseBody
    public R getOnlineWxMenu(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        // 调用接口创建菜单
        try {
            jsonObject = wechatAuthService.getMenuOnline();

            if(jsonObject.containsKey("errcode")){
                return R.error(jsonObject.getString("errmsg"));
            }
        } catch (Exception e) {
            log.error("获取线上菜单失败：" + e.getMessage());
            e.printStackTrace();
        }
        return R.ok().put("menu",jsonObject.getJSONObject("selfmenu_info").getJSONArray("button"));
    }

    /**
     * 进入获取线上素材页面
     * @return
     */
    @RequestMapping("/wxMaterialForm")
    public String wxMaterialForm (HttpServletRequest request,Model model){
        int idx = ServletRequestUtils.getIntParameter(request,"idx",0);
        model.addAttribute("idx",idx);
        return "admin/manager/wxMedia";
    }

    /**
     * 进入获取线上图片页面
     * @return
     */
    @RequestMapping("/wxPicForm")
    public String wxPicForm (HttpServletRequest request,Model model){
        int idx = ServletRequestUtils.getIntParameter(request,"idx",0);
        model.addAttribute("idx",idx);
        return "admin/manager/wxPicForm";
    }


    /**
     * 即时获取线上素材
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOnlineWxMaterial")
    public R getOnlineWxMaterial(@RequestParam("type")String type, HttpServletRequest request){
        int pageNo= ServletRequestUtils.getIntParameter(request,"pageNo",1);
        pageNo=(pageNo-1)*10;

        String count="10";
        if(type.equals("image")){
            count = "20";
        }
        JSONObject jsonObject = new JSONObject();
        // 调用接口获取所有图文素材
        try {
            jsonObject = wechatAuthService.getMaterialOnline(type,String.valueOf(pageNo),count);
            if(jsonObject.get("errcode") != null){
                log.info("获取线上素材失败：" + jsonObject);
                return R.error(jsonObject.toJSONString());
            }else{
                log.info("获取线上素材成功：" + jsonObject);
                return R.ok().put("result",jsonObject);
            }
        } catch (Exception e) {
            log.error("获取线上素材失败：" + e.getMessage());
            e.printStackTrace();
            return R.error(jsonObject.toJSONString());
        }
    }

    /**
     * 通过id获取图文素材
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMediaByID")
    public R getMediaByID(@RequestParam("mid")String mid){

        // 调用接口获取所有图文素材
        try {
            JSONObject jsonObject = wechatAuthService.getMaterialOnlineByID(mid);
            if(jsonObject.get("errcode") != null){
                log.info("获取图文素材成功：" + jsonObject);
                return R.error();
            }else{
                log.info("获取图文素材成功：" + jsonObject);
                return R.ok().put("result",jsonObject);
            }
        } catch (Exception e) {
            log.error("获取图文素材失败：" + e.getMessage());
            e.printStackTrace();
            return R.error();
        }
    }


    /**
     * 发布菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/pushMenu")
    public R pushMenu(@RequestParam("jsonStr")String requestJSON,  HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();

        // 调用接口发布菜单
        try {
            if(requestJSON.contains("pagepath")){
                return R.error("暂不支持跳转小程序");
//                requestJSON = requestJSON.replaceAll("pagepath","appid\":\"" + miniappid +"\",\"pagepath");
            }
            jsonObject = wechatAuthService.pushMenu(requestJSON);

            if(jsonObject.getIntValue("errcode") != 0){
                log.info("发布参数：" + requestJSON);
                log.info("发布失败：" + jsonObject);
                return R.error(jsonObject.getString("errmsg"));
            }else{
                log.info("发布成功：" + jsonObject);
                return R.ok();
            }
        } catch (Exception e) {
            log.error("发布失败：" + e.getMessage());
            e.printStackTrace();
            return R.error(jsonObject.toJSONString());
        }
    }

    /**
     * 微信公众号多媒体上传永久素材--图片
     * @param file
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/wxMediaUpload")
    public R wxMediaUpload(@RequestParam("file") MultipartFile file, @RequestParam("mediaType")int mediaType, HttpServletRequest request, HttpServletResponse response){
        if(file.isEmpty()){
            return R.error();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            String fileName = file.getOriginalFilename();
            fileName = URLEncoder.encode(fileName, "UTF-8");// 进行中文处理
            String UPLOAD_PATH = File.separator + "upload" + File.separator + "img" + File.separator;
            String path = request.getSession().getServletContext().getRealPath(UPLOAD_PATH);
            String pathFileName = path + File.separator + fileName;
            file.transferTo(new File(pathFileName));

            jsonObject = wechatAuthService.addMaterialEver(pathFileName, "image",mediaType);
            if(jsonObject.get("errcode") != null){
                log.info("发布失败：" + jsonObject);
                return R.error();
            }else{
                log.info("发布成功：" + jsonObject);
                return R.ok().put("result",jsonObject);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.info("发布失败：" + jsonObject);
            return R.error();
        }
    }
}
