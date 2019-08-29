package io.renren.common.utils.wepay;


import io.renren.common.utils.HttpUtil;
import io.renren.common.utils.MD5;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: Clarence
 * @Description:微信提现 xml数据 签名等
 * @Date: 2018/10/8 23:17.
 */
@Component
public class WeixinpayUtil {
    private static Logger LOG = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Value("${weCat.appId}")
    private String weCatAppId;
    @Value("${weCat.mchId}")
    private String mchId;
    @Value("${weCat.key}")
    private String key;

    public static String createDocumentForEnterprisesPayment(EnterprisesPayment enterprisesPayment) {
        final StringBuffer result = new StringBuffer();
        result.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><xml>");
        result.append("<mch_appid>" + enterprisesPayment.getMch_appid() + "</mch_appid>");
        result.append("<mchid>" + enterprisesPayment.getMchid() + "</mchid>");
        result.append("<nonce_str>" + enterprisesPayment.getNonce_str() + "</nonce_str>");
        result.append("<partner_trade_no>" + enterprisesPayment.getPartner_trade_no() + "</partner_trade_no>");
        result.append("<openid>" + enterprisesPayment.getOpenid() + "</openid>");
        result.append("<check_name>" + enterprisesPayment.getCheck_name() + "</check_name>");
        result.append("<re_user_name>" + enterprisesPayment.getRe_user_name() + "</re_user_name>");
        result.append("<amount>" +enterprisesPayment.getAmount()+ "</amount>");
        result.append("<desc>" + enterprisesPayment.getDesc() + "</desc>");
        result.append("<spbill_create_ip>" + enterprisesPayment.getSpbill_create_ip() + "</spbill_create_ip>");
        result.append("<sign>" + enterprisesPayment.getSign() + "</sign>");
        result.append("</xml>");
        return result.toString();
    }




    public static String getSignCode(Map<String, String> map, String keyValue) {
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }

            }
            sb.append("key="+keyValue);
            result = sb.toString();

            //进行MD5加密
            result = MD5.MD5Encode(result).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * 生成32位编码
     * @return string
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    /**
     *  生成提现订单号
     * @return string
     */
    public static String getOrderId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        return machineId+String.format("%015d", hashCodeV);
    }

    /**
     *  md5加密
     * @param text
     * @return
     */
    public static String md5(final String text) {
        return Md5Encrypt.md5(text).toUpperCase();
    }


    public EnterprisesPayment addEnterprisesPayment(SortedMap<String, String> sortedMap, String openid, Integer amount, String sginCode,String desc, HttpServletRequest request){
        EnterprisesPayment enterprisesPayment = new EnterprisesPayment();
        enterprisesPayment.setMch_appid(weCatAppId);//商户号appid
        enterprisesPayment.setMchid(mchId);//商户号
        // enterprisesPayment.setDevice_info(null);//设备号 非必填
        enterprisesPayment.setNonce_str(sortedMap.get("nonce_str"));//随机字符串
        enterprisesPayment.setSign(sginCode);//签名
        enterprisesPayment.setPartner_trade_no(sortedMap.get("partner_trade_no"));//商户订单号
        enterprisesPayment.setOpenid(openid);
        enterprisesPayment.setCheck_name("NO_CHECK");
        enterprisesPayment.setRe_user_name(null); //根据checkName字段判断是否需要
        enterprisesPayment.setAmount(amount);//金额
        enterprisesPayment.setDesc(desc);//描述
        enterprisesPayment.setSpbill_create_ip(HttpUtil.getIpAddr(request));//ip地址
        return enterprisesPayment;
    }

    public String getSgin(SortedMap<String, String> sortedMap, String openid, Integer amount, HttpServletRequest request, String orderNo, String appid, String desc) {
        sortedMap.put("mch_appid", appid);
        sortedMap.put("mchid",mchId);
        sortedMap.put("nonce_str", WeixinpayUtil.getUUID());
        sortedMap.put("partner_trade_no", orderNo);
        sortedMap.put("openid", openid);
        sortedMap.put("check_name", "NO_CHECK"); //不校验真实姓名
        sortedMap.put("amount", amount.toString());
        sortedMap.put("desc", desc);
        sortedMap.put("spbill_create_ip", HttpUtil.getIpAddr(request));
        sortedMap.put("re_user_name", "null"); //收款用户真实姓名。如果check_name设置为FORCE_CHECK，则必填用户真实姓名
        return WeixinpayUtil.getSignCode(sortedMap,key);
    }
}
