package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: <br>
 * 经纬度计算两个点的之间的距离
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/21 15:36
 */
@Component
public class LocationUtils {
    private static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Value("${baiduMap.ak}")
    private String ak;
    @Value("${baiduMap.locationUrl}")
    private String locationUrl;
    @Value("${baiduMap.distanceUrl}")
    private String distanceUrl;

    public JSONObject getLocation(String addrName){
        Map<String,String> param = new HashMap<>();
        param.put("query",addrName);
        param.put("region","全国");
        param.put("output","json");
        param.put("ak",ak);

        log.info("百度地图经纬度请求参数：" + param);
        JSONObject res = JSONObject.parseObject(HttpClientTool.getData(locationUrl,param));
        log.info("================== 百度地图经纬度请求结果：" + res);
        return res;
    }

    /**
     *
     * @param origin 起点经纬度  纬度,经度   40.056878,116.30815
     * @param destination 终点经纬度
     * @return
     */
    public JSONObject getDistance(String origin,String destination ){
        Map<String,String> param = new HashMap<>();
        param.put("origins",origin);
        param.put("destinations",destination);
        param.put("output","json");
        param.put("ak",ak);

        log.info("百度地图两点距离请求参数：" + param);

        JSONObject res = JSONObject.parseObject(HttpClientTool.getData(distanceUrl,param));
        log.info("============== 百度地图两点距离请求结果：" + res);
        return res;
    }

    public float getDistanceByBaiduMap(String addrName,String origin){
        JSONObject addrRes = getLocation(addrName);

        if(addrRes.getInteger("status") == 0){
            JSONObject locaJson = addrRes.getJSONArray("results").getJSONObject(0).getJSONObject("location");
            String destination = locaJson.getString("lat") + "," + locaJson.getString("lng");
            JSONObject res = getDistance(origin,destination);

            if(res.getInteger("status") == 0){
                //获取路线距离，duration也可以返回路线耗时  驾驶模式
                return res.getJSONArray("result").getJSONObject(0).getJSONObject("distance").getFloat("value");
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        Map<String,String> param = new HashMap<>();
        param.put("query","深圳市壹方城");
        param.put("region","全国");
        param.put("output","json");
        param.put("ak","SoQCCsGE9S8ILEvx1HCtV8S6oS4PYYXA");

        String locationUrl = "http://api.map.baidu.com/place/v2/search";
        log.info("百度地图经纬度请求参数：" + param);
//        JSONObject res = HttpUtil.postByJson(locationUrl,param);
        String res = HttpClientTool.getData(locationUrl,param);
        log.info("================== 百度地图经纬度请求结果：\n" + res);
    }
}
