package io.renren.common.utils.poi;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileUtil {
    public FileUtil() {
    }

    public static String fileSizeBytesString(String filePath) {
        File file = new File(filePath);
        String fileSize = null;
        if (file.exists()) {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(file);
                fileSize = fis.available() + " bytes";
            } catch (Exception var5) {
                fileSize = null;
            }
        }

        return fileSize;
    }

    public static byte[] readFile(String filePath) {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            byte[] fileByte = new byte[fileIn.available()];
            fileIn.read(fileByte);
            fileIn.close();
            return fileByte;
        } catch (Exception var3) {
            return null;
        }
    }

    /**
     * 对文件fileUrl进行编码 类似于浏览器的encodeURI编码
     * 例子：编码前：http://www.baidu.com/api/resource/robot/word/2/婚前协议.doc
     *      编码后：http://www.baidu.com/api/resource/robot/word/2/%E5%A9%9A%E5%89%8D%E5%8D%8F%E8%AE%AE.doc
     * @param url
     * @return
     */
    public static String encodeURI(String url) throws Exception
    {
        String encode = URLEncoder.encode(url, "UTF-8");

        return encode.replace("%3A",":").replace("%2F","/");
    }

    /**
     * 根据fileUrl获取 带后缀的文件名称
     *
     * @param url
     * @return
     */
    public static String getNameByUrl(String url)
    {
        if (StringUtils.isEmpty(url))
        {
            return null;
        }
        return url.substring(url.lastIndexOf("/")+1);
    }

    public static boolean writeFile(String filePath, byte[] content) {
        if (filePath != null && content != null) {
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(new File(filePath));
                fos.write(content);
                fos.close();
                return true;
            } catch (Exception var4) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String encodeDownloadFileName(String fileName,String agent) throws IOException {
        if(agent.contains("Firefox")){
            fileName = "=?UTF-8" + Base64.encodeBase64String(fileName.getBytes("utf-8")) + "?=";
            fileName = fileName.replaceAll("\r\n","");
        }else{
            fileName = URLEncoder.encode(fileName,"utf-8");
            fileName = fileName.replace("+","");
        }

        return fileName;
    }
}
