package io.renren.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.jdbc.StringUtils;
import io.renren.ApiApplication;
import io.renren.common.utils.R;
import io.renren.entity.Driver;
import io.renren.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/12 13:19
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping("/get")
    public R get() throws IOException {
        List<Driver> driverList = userService.queryDrver();

        for (Driver driver:driverList){
            String driverName = driver.getCountry(); // Canada
            String driverPreName = driver.getRemark(); // CA 国家缩写
            String driverPro = driver.getProv(); // 省
            String driverPath = "D:/driver/" + driverName; // 文件路径名 D:/driver/Canada
            judeDirExists(new File(driverPath)); // 以国家区分 生成文件夹

            String driverFileName = "D:" + driver.getTempPic(); // 原始驾照模板图片路径 D:/upload/201904/27/201904271751127196.jpg
            logger.info("原始驾照图片地址：" + driverFileName);

            if(!redisTemplate.opsForSet().isMember("o_driver",driver.getTempPic())){ //该驾照是新的
                redisTemplate.opsForSet().add("o_driver",driver.getTempPic());

                int temp = 0;
                String driver_suffix = driverFileName.substring(driverFileName.lastIndexOf("."));
                StringBuilder driverNewFileName = new StringBuilder(driverPreName); //新驾照模板文件名 CA
                if(!StringUtils.isNullOrEmpty(driverPro)){
                    driverNewFileName.append("_").append(driverPro);
                }
                logger.info("新驾照名称路径：" + driverPath + "/" + driverNewFileName  + "_" + temp + driver_suffix);
                while (isExist(new File(driverPath + "/" + driverNewFileName + "_" + temp + driver_suffix))){ // 如果文件存在 就加一
                    logger.info("该路径已存在，数量+1," + temp);
                    if(temp == 20){
                        break;
                    }
                    temp++;
                }

                driverNewFileName.append("_").append(temp).append(driver_suffix);
                String newFilePath = driverPath + "/" + driverNewFileName;
                logger.info("最终驾照新文件：" + newFilePath);
                saveFile(driverFileName,newFilePath);
            }else{
                logger.info("驾照文件已存在，跳过");
            }


            if(!StringUtils.isNullOrEmpty(driver.getForgeryPic())){
                if(!redisTemplate.opsForSet().isMember("o_driver_m",driver.getForgeryPic())) { //该驾照防伪是新的
                    redisTemplate.opsForSet().add("o_driver_m",driver.getForgeryPic());

                    String forgeryPicName = "D:" + driver.getForgeryPic(); //防伪图原路径
                    logger.info("原始驾照防伪图片地址：" + forgeryPicName);

                    int temp1 = 0;
                    String forgery_suffix = forgeryPicName.substring(forgeryPicName.lastIndexOf("."));
                    StringBuilder forgeryNewFileName = new StringBuilder(driverPreName); //新驾照防伪图片文件名 CA
                    if(!StringUtils.isNullOrEmpty(driverPro)){
                        forgeryNewFileName.append("_").append(driverPro);
                    }
                    logger.info("新驾照防伪名称路径：" + driverPath + "/" +  forgeryNewFileName  + "_m_" + temp1 + forgery_suffix);
                    while (isExist(new File(driverPath + "/" + forgeryNewFileName + "_m_" + temp1 + forgery_suffix))){ // 如果文件存在 就加一
                        logger.info("该路径已存在，数量+1");
                        temp1++;
                    }

                    forgeryNewFileName.append("_m_").append(temp1).append(forgery_suffix);
                    String newForgeryFilePath = driverPath + "/" + forgeryNewFileName;
                    logger.info("最终驾照防伪新文件：" + newForgeryFilePath);
                    saveFile(forgeryPicName,newForgeryFilePath);
                }else{
                    logger.info("驾照防伪文件已存在，跳过");
                }
            }
        }
        return R.ok();
    }

    private void saveFile(String filePath,String newFilePath) throws IOException {
        File sourceFile = new File(filePath);
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff=new BufferedInputStream(input);

        File targetFile = new File(newFilePath);
        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff=new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len =inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    // 判断文件夹是否存在
    private void judeDirExists(File file) {
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    // 判断文件是否存在
    private boolean judeFileExists(File file) throws IOException {
        logger.info(file.getName());
        if (file.exists()) {
            logger.info("file exists");
            return true;
        } else {
            logger.info("file not exists, create it ...");
            file.createNewFile();
            return false;
        }
    }

    private boolean isExist(File file){
        return file.exists() || file.isDirectory();
    }
}
