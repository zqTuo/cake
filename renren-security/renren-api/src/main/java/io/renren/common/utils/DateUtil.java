package io.renren.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/1/2 10:49.
 */
public class DateUtil {
    private static SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateUtil(){}

    /**
     * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
     * @return String
     * @throws Exception
     */
    public static String getNowPlusTime() throws Exception {
        String nowDate = "";
        try
        {
            java.sql.Date date = null;
            date = new java.sql.Date(new Date().getTime());
            nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public static String generateTimeStamp() {
        String timestamp = null;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        timestamp = sdf.format(date);

        return timestamp;
    }

    public static String getYYYYMMdd() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static Date getDayAfterMonth(int month, Date startTime){
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);

        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    public static Date getDayAfterDay(int day, Date startTime){

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);

        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    public static void main(String[] args){
        System.out.println(getDayAfterDay(5,new Date()));
        System.out.println(getDayAfterDay(20,new Date()));
    }
}
