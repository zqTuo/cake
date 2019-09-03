package io.renren.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 获取UUID编码工具类
 *
 * @author will
 */
public class GUID {
    public static String getGUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getOrderIdByUUId() {
        int first = new Random(8).nextInt(12) + 1;
        System.out.println("First:" + first);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%07d", hashCodeV);
    }
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String orderingID= getOrderIdByUUId();
            System.out.println(orderingID);
        }

    }
}
