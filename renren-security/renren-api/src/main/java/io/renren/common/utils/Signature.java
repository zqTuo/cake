package io.renren.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by will on 2015/8/25.
 */
@Component
public class Signature {
    @Value("${weCat.key}")
    private String key;

    /**
     * 签名算法
     * @param map 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public String getSign(Map<String,Object> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

    /**
     * 验证签名
     *
     * @param token 微信服务器token，在env.properties文件中配置的和在开发者中心配置的必须一致
     * @param signature 微信服务器传过来sha1加密的证书签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return
     */
    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
        String[] arr = new String[] { token, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);

        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String tmpStr = SHA1.encode(arr[0] + arr[1] + arr[2]);

        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }


    /**
     * 美团签名
     * @param params
     * @param appSecret
     * @param signMethod
     * @return
     */
    public static String generateSign(Map<String, String> params, String appSecret, String signMethod) {
        // 第一步：参数排序
        List<String> keys = Lists.newArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isNotEmpty(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        Collections.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(appSecret)) {
            sb.append(appSecret);
        }
        for (String key : keys) {
            sb.append(key).append(params.get(key).trim());
        }
        if (StringUtils.isNotEmpty(appSecret)) {
            sb.append(appSecret);
        }
        String encryptionKey = sb.toString().trim();
        // 第三步：加签
        if (signMethod.equals("MD5")){
            try {
                return genMd5(encryptionKey);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }else{
            //开发者暂不需支持，支持MD5即可
            return "";
        }
    }

    public static String genMd5(String info) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] infoBytes = info.getBytes();
        md5.update(infoBytes);
        byte[] sign = md5.digest();
        return byteArrayToHex(sign);
    }

    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toLowerCase());
        }
        return sign.toString();
    }
}
