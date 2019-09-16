package io.renren.common.utils.notice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.entity.notice.CakeSelfNotice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by 24537 on 2017/9/2.
 */
@Component
public class CreateJsonUtil {
    @Value("${weCat.cake_self_templateid}")
    private String cake_self_templateid;
    @Value("${project.url_pre}")
    private String url_pre;


    //获取蛋糕预订自提提醒json;
    public JSONObject cake_self_Json(String orderNo, String productName, String sendTime, String shopName,String addrReceiver,String addrPhone, String touser) {

        //点击模板后的链接地址
        String pagepath = url_pre + "" + orderNo;
        //模板的主题颜色
        String topcolor = "#008000";
        //构造json包
        CakeSelfNotice wn = new CakeSelfNotice(orderNo,productName,sendTime,shopName,addrReceiver,addrPhone, cake_self_templateid, pagepath, topcolor, touser);
        String str = JSON.toJSONString(wn.getMap());
        return JSONObject.parseObject(str);
    }


    //发送模板;
    public String send_Json(String params, String accessToken) {
        StringBuffer bufferRes = new StringBuffer();
        try {
            URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            // 请求方式
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();

            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

            // 发送请求参数
            //out.write(URLEncoder.encode(params,"UTF-8"));
            //发送json包
            try {
                out.write(params);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream in = conn.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String valueString = null;
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            //输出返回的json
            System.out.println(bufferRes);
            in.close();
            if (conn != null) {
                // 关闭连接
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferRes.toString();
    }
}
