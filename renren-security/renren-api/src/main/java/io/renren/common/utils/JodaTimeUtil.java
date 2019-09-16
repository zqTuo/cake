package io.renren.common.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Clarence
 * @Description:
 * @Date: 2019/3/19 21:32.
 */
public class JodaTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat STANDARD_DATE_FORMAT = new SimpleDateFormat(STANDARD_FORMAT);
    public static final String IDCARD_FORMAT = "yyyyMMdd";
    private static final SimpleDateFormat sdf_yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat sdf_HHmm = new SimpleDateFormat("HH:mm");


    public static Date strToDate(String dateTimeStr,String formatStr){
        if(StringUtils.isEmpty(formatStr)){
            formatStr = STANDARD_FORMAT;
        }
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
            DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
            return dateTime.toDate();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    public static boolean isExpired(Date startDate,Date endDate){
        if(startDate == null || endDate == null){
            return false;
        }

        DateTime start = new DateTime(startDate);
        DateTime end = new DateTime(endDate);
        Duration duration = new Duration(start,end);
        return duration.getStandardSeconds() <= 0;
    }

    public static boolean isExpired(Date startDate,String endDate){
        if(startDate == null || StringUtils.isEmpty(endDate)){
            return false;
        }
        if("长期".equals(endDate)){
            return false;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(IDCARD_FORMAT);
        DateTime start = new DateTime(startDate);
        DateTime end = dateTimeFormatter.parseDateTime(endDate);
        Duration duration = new Duration(start,end);
        return duration.getStandardSeconds() <= 0;
    }

    /**
     *
     * @param endDateStr yyyy-MM-dd HH:mm
     * @return
     */
    public static boolean isExpiredCur(String endDateStr) throws ParseException {
        Date endDate = sdf_yyyyMMddHHmm.parse(endDateStr);
        DateTime end = new DateTime(endDate);

        DateTime cur = new DateTime();

        Duration duration = new Duration(cur,end);

        return duration.getStandardSeconds() <= 0;
    }

    /**
     *
     * @param selectedDate yyyy-MM-dd
     * @return
     */
    public static long isAfterToday(String selectedDate) throws ParseException {
        Date date = DAY_FORMAT.parse(selectedDate);
        DateTime start = new DateTime(date);

        DateTime cur = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);

        Duration duration = new Duration(cur,start);
        return duration.getStandardSeconds() / 3600;
    }

    /**
     *
     * @param selectedDate yyyy-MM-dd HH:mm
     * @return
     */
    public static long duringHours(String selectedDate,Date curDate) throws ParseException {
        Date date = sdf_yyyyMMddHHmm.parse(selectedDate);
        DateTime start = new DateTime(date);

        DateTime cur = new DateTime(curDate);

        Duration duration = new Duration(cur,start);
        return duration.getStandardSeconds() / 3600;
    }
    /**
     *
     * @param startStr yyyy-MM-dd HH:mm
     * @return
     */
    public static long duringHours(String startStr,String end) throws ParseException {
        Date startDate = sdf_yyyyMMddHHmm.parse(startStr);
        DateTime start = new DateTime(startDate);
        start.dayOfMonth().addToCopy(1);

        Date endDate = sdf_yyyyMMddHHmm.parse(end);

        DateTime cur = new DateTime(endDate);

        Duration duration = new Duration(start,cur);
        return duration.getStandardSeconds() / 3600;
    }

    public static long dateCompare(Date startDate,Date endDate){
        if(startDate == null || endDate == null){
            return 0;
        }

        DateTime start = new DateTime(DAY_FORMAT.format(startDate));
        DateTime end = new DateTime(DAY_FORMAT.format(endDate));
        Duration duration = new Duration(start,end);
        return duration.getStandardSeconds();
    }

    public static long getSeconds(Date startDate,Date endDate){
        if(startDate == null || endDate == null){
            return 0;
        }

        DateTime start = new DateTime(STANDARD_DATE_FORMAT.format(startDate));
        DateTime end = new DateTime(STANDARD_DATE_FORMAT.format(endDate));
        Duration duration = new Duration(start,end);
        return duration.getStandardSeconds();
    }

    public static Date dateToDate(String dateStr,String format,String newFormat){
        Date date = strToDate(dateStr,format);
        SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(newFormat);
        DateTime newDate = new DateTime(DAY_FORMAT.format(date));
        return newDate.toDate();
    }

    public static String dateToNewDate(String dateStr,String format,String newFormat){
        Date date = strToDate(dateStr,format);
        SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(newFormat);
        DateTime newDate = new DateTime(DAY_FORMAT.format(date));
        return newDate.toString(newFormat);
    }


    public static BigDecimal getRate(Date startDate,Date endDate){
        int days = (int) (dateCompare(startDate,new Date())/(3600*24));
        int memNem = (int) Math.ceil(days / 30);//这个是天数

        return new BigDecimal(days - (30 * memNem)).divide(new BigDecimal("30"), 2, BigDecimal.ROUND_HALF_UP);
    }



    public static void main(String[] args) throws ParseException {
        System.out.println(getDateFromToday(6));
    }

    public static String getTimeStrForHHss(String sendTime) throws ParseException {
        return sdf_HHmm.format(sdf_yyyyMMddHHmm.parse(sendTime));
    }

    public static String getDateFromToday(int validPeriod) {
        String validDate = sdf_yyyyMMdd.format(new Date());
        validDate += "至" + sdf_yyyyMMdd.format(DateUtil.getDayAfterMonth(validPeriod,new Date()));

        return validDate;
    }
}
