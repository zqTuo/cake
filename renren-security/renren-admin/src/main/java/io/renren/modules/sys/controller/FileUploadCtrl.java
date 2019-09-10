package io.renren.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.ActionEnter;
import io.renren.common.utils.FileFolderUtil;
import io.renren.common.utils.GUID;
import io.renren.common.utils.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Clarence
 * @Description: 文件上传控制器
 * @Date: 2019/3/9 19:14.
 */
@Controller
@RequestMapping("admin/upload")
public class FileUploadCtrl {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Value("${project.pic_pre}")
    private String pic_pre;
    @Value("${project.video_pre}")
    private String video_pre;

    @Value("${project.uploadFilePath}")
    private String uploadFilePath;

    @RequestMapping("/uploadFile.do")
    @ResponseBody
    public R uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(file == null || file.getSize() == 0){
            return R.error(-3,"请选择图片");
        }
        JSONObject res = FileFolderUtil.upload(file,pic_pre,uploadFilePath);
        return R.ok().put("result",res);
    }

    /**
     * ueditor自定义图片上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/action")
    @ResponseBody
    public Map<String, Object> action(@RequestParam(value = "upfile", required = false) MultipartFile file) throws IOException {
        Map<String, Object> rs = new HashMap<String, Object>();

        //原始数据
        String originalFileName = file.getOriginalFilename();
        //图片文件夹名称
        String path = FileFolderUtil.getFileUrl();
        //图片文件夹路径
        String rpath = uploadFilePath + path;
        logger.info("Ueditor图片上传路径：" + rpath);
        //生成图片文件夹
        FileFolderUtil.isExists(rpath);
        //根据uid生成图片名称
        String uid = GUID.getGUID();
        String temp = file.getContentType();
        String suffix = temp.substring(temp.lastIndexOf("/") + 1);

        String fileName = uid + "." + suffix;
        //根据路径和文件名生成新的空白文件
        logger.info("Ueditor图片保存路径：" + rpath + "/" + fileName);
        File targetFile = new File(rpath + "/" + fileName);
        boolean res = targetFile.createNewFile();
        logger.info("上传结果：" + res);
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rs.put("state", "SUCCESS");// UEDITOR的规则:不为SUCCESS则显示state的内容
        rs.put("url", pic_pre + path + "/"  + fileName);         //能访问到你现在图片的路径
        rs.put("title", originalFileName);
        rs.put("original", originalFileName);

        return rs;
    }


    /**
     * 上传视频
     * @param file
     * @return
     */
    @RequestMapping(value = "/videoUpload",method = RequestMethod.POST)
    @ResponseBody
    public R videoUpload(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null || file.getSize() <= 0) {
            return R.error("文件不能为空");
        }
        if (file.getSize() > 50 * 1024 * 1024) {
            return R.error("上传视频大小不能超过50Mb！");
        }

        try {
            JSONObject res = FileFolderUtil.upload(file,video_pre,uploadFilePath);
            logger.debug("视频路径{}",res);

            return R.ok().put("data",res);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }


}
