package io.renren.common.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by will on 2015/8/25.
 */
public class RandomStringGenerator {
    /** 自定义进制（选择你想要的进制数，不能重复且最好不要0、1这些容易混淆的字符） */
    private static final char[] r = new char[]{'Q', 'W', 'E', '8', 'S', '2', 'D', 'Z', 'X', '9', 'C', '7', 'P', '5', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'T', 'N', '6', 'B', 'G', 'H'};

    /** 定义一个字符用来补全邀请码长度（该字符前面是计算出来的邀请码，后面是用来补全用的） */
    private static final char b = 'A';

    /** 进制长度 */
    private static final int binLen = r.length;

    /** 邀请码长度 */
    private static final int s = 6;

    /** 补位字符串 */
    private static final String e = "ATGSGHJ";

    /**
     * 根据ID生成随机码
     * @param id ID
     *  该方法同一个id生成的邀请码不唯一
     * @return 随机码
     */
    public static String toSerialCodeUnUnique(long id) {
        char[] buf=new char[32];
        int charPos=32;

        while((id / binLen) > 0) {
            int ind=(int)(id % binLen);
            buf[--charPos]=r[ind];
            id /= binLen;
        }
        buf[--charPos]=r[(int)(id % binLen)];
        String str=new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < s) {
            StringBuilder sb=new StringBuilder();
            sb.append(b);
            Random rnd=new Random();
            for(int i=1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str+=sb.toString();
        }
        return str;
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        int min = 1;
        int max = 5;
        int o_max = max;
        max = max - min + 1;

        for (int i = 0; i < 40; i++) {
            double randomPrice = Math.random()*(max) + min; // Math.random()*(max-min+1) + min
            if(randomPrice > o_max){
                randomPrice = o_max;
            }
            BigDecimal price = new BigDecimal(randomPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            System.out.println(price);
        }
    }
}
