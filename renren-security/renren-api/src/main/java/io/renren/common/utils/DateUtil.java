package io.renren.common.utils;

import java.text.ParseException;
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
    private static SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf_yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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

    /**
     *
     * @param selectedTime yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static boolean isToday(String selectedTime) throws ParseException {
        Calendar today = Calendar.getInstance();
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(sdf_yyyyMMdd.parse(selectedTime));

        return selectedDate.equals(today);
    }

    /**
     *
     * @param delay
     * @param selectedTime yyyy-MM-dd HH:mm
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static boolean isAfter(int delay, String selectedTime, String start, String end) throws ParseException {
        //往后推迟的时间
        Calendar delayTime = Calendar.getInstance();


        // 当前时间
        String cur = sdf_yyyyMMdd.format(new Date());

        //营业开始时间
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(sdf_yyyyMMddHHmm.parse(cur + " " + start));
        //营业结束时间
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(sdf_yyyyMMddHHmm.parse(cur + " " + end));

        //判断当前时间是否在营业时间范围外
        Calendar now = Calendar.getInstance();
        if(now.before(startDate) || now.after(endDate)){// 还没有开始营业
            cur = cur + " " + start;
            delayTime.add(Calendar.HOUR_OF_DAY,delay);
        }else{
//            cur = cur + " " + startTime;
        }



        Calendar curTime = Calendar.getInstance();
        curTime.setTime(sdf_yyyyMMddHHmm.parse(cur));




        return curTime.after(delayTime);
    }


    /**
     * 根据日期获取当天是周几
     * @param datetime 日期
     * @return 周几
     */
    public static String dateToWeek(String datetime) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = sdf_yyyyMMddHHmm.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w];
    }


    public static void main(String[] args) throws ParseException {
        String temp = "2019-09-12 22:27";
        System.out.println(dateToWeek(temp));
//        System.out.println(isToday("2019-08-26"));
//        System.out.println(isAfter(4,"20:49", start, end));
    }


}
