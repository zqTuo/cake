package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by will on 2016/7/7.
 */
public class FileFolderUtil {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static String getFileUrl() {
        String dateStr = DateUtil.generateTimeStamp();
        dateStr = dateStr.substring(0, 8);
        return dateStr;
    }

    public static void isExist(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
    }
    public static void isExists(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
//            file.setReadable(true);
            file.mkdirs();
        }
    }

    public static void isDeletedFile(String path) {
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            file.delete();
            System.out.println("文件已删除");
        }
    }

    public static JSONObject upload(MultipartFile file, String read_url, String outFileRootPath) throws IOException {
        //图片文件夹名称
        String path = FileFolderUtil.getFileUrl();
        //图片文件夹路径
        String rpath = outFileRootPath + path; //服务器上
//        String rpath = request.getSession().getServletContext().getRealPath("/images/" + path); //本地
        logger.info("文件上传路径：" + rpath);
        //生成图片文件夹
        FileFolderUtil.isExists(rpath);
        //根据uid生成图片名称
        String uid = GUID.getGUID();
        String temp = file.getContentType();
        String suffix = temp.substring(temp.lastIndexOf("/") + 1);

        String fileName = uid + "." + suffix;
        //根据路径和文件名生成新的空白文件
        File targetFile = new File(rpath + File.separator + fileName);
        targetFile.createNewFile();

        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileUrl", read_url + path + File.separator  + fileName);
        return jsonObject;
    }

}
