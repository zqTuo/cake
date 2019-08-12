package io.renren.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.jdbc.StringUtils;
import io.renren.common.utils.R;
import io.renren.entity.Driver;
import io.renren.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping("/get")
    public R get() throws IOException {
        List<Driver> driverList = userService.queryDrver();
        for (Driver driver:driverList){
            String driverName = driver.getCountry(); // Canada
            String driverPath = "D:/driver/" + driverName; // 文件路径名 D:/driver/Canada
            judeDirExists(new File(driverPath)); // 以国家区分 生成文件夹

            String driverFileName = "D:" + driver.getTempPic(); // 原始驾照模板图片路径 D:/upload/201904/27/201904271751127196.jpg

            int temp = 0;
            String driver_suffix = driverFileName.substring(driverFileName.lastIndexOf("."));
            String driverNewFileName = driverName + "_" + temp + driver_suffix; //新驾照模板文件名 Canada_0.jpg
            if(judeFileExists(new File(driverNewFileName))){

            }

            String forgeryPicName = ""; //防伪图原路径
            if(StringUtils.isNullOrEmpty(driver.getForgeryPic())){
                forgeryPicName = "D:" + driver.getForgeryPic();
            }


        }
        return R.ok();
    }

    // 判断文件夹是否存在
    public static void judeDirExists(File file) {
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    // 判断文件是否存在
    public static boolean judeFileExists(File file) throws IOException {
        System.out.println(file.getName());
        if (file.exists()) {
            System.out.println("file exists");
            return true;
        } else {
            System.out.println("file not exists, create it ...");
            file.createNewFile();
            return false;
        }

    }
}
