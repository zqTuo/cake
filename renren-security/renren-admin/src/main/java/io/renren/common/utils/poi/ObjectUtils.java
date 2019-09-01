package io.renren.common.utils.poi;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by will on 2016/11/24.
 * version : 1.0.
 * Author will.
 */
public class ObjectUtils {

    public static Object getObjectPropertyValue(Object object,String property) throws Exception{
        Object returnValue = null;
        try {
            returnValue = PropertyUtils.getNestedProperty(object,property);
            if(returnValue == null || returnValue.equals("")){
                returnValue = "";
            }
        }catch (Exception e) {
            returnValue = "";
        }
        return returnValue;
    }

    public static Object getVlaueByType(Object object, String type, String dateFormat, int numberFormat, String defaultValue) throws ClassCastException{
        if(object == null || object.equals("")){
            if(defaultValue != null && !"".equals(defaultValue)){
                return defaultValue;
            }else {
                return "";
            }
        }

        if(type.equalsIgnoreCase("Integer")){
            if(numberFormat > 0){
                return (((Integer) object) / numberFormat);
            }else{
                return ((Integer) object).toString();
            }
        }

        if(type.equalsIgnoreCase("Date")){
            return parseDate2String((Date) object,dateFormat);
        }

        if(type.equalsIgnoreCase("Float")){
            return ((Float) object).toString();
        }

        if(type.equalsIgnoreCase("Double")){
            return ((Double) object).toString();
        }

        if(type.equalsIgnoreCase("String")){
            return object;
        }

        if(type.equalsIgnoreCase("Boolean")){
            return ((Boolean) object).toString();
        }

        if(type.equalsIgnoreCase("Long")){
            return ((Long) object).toString();
        }

        if(type.equalsIgnoreCase("Short")){
            return ((Short) object).toString();
        }

        if(type.equalsIgnoreCase("BigDecimal")){
            return ((BigDecimal) object).toString();
        }

        return object.toString();
    }

    private static String parseDate2String(Date date, String dateFormat) {
        if(StringUtils.isEmpty(dateFormat)){
            dateFormat = "yyy-MM-dd";
        }

        if(date == null){
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
