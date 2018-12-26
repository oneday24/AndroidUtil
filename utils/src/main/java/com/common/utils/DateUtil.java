package com.common.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zengjing on 17/11/23.
 */

public class DateUtil {
    public static SimpleDateFormat yyyyMMddChina=
            new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    public static SimpleDateFormat yyyyMMdd=
            new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    public static SimpleDateFormat yyyyMMdd_italic=
            new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
    public static SimpleDateFormat yyyyMMdd_line=
            new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static SimpleDateFormat yyyyMMdd_dot=
            new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
    private static final SimpleDateFormat YMDHMS
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat HM
            = new SimpleDateFormat("HH:mm", Locale.getDefault());

    /**
     *
     * @param dateStr 2017-09-10
     * @return 2017年09月10日
     */
    public static String lineToChina(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        String first = dateStr.replaceFirst("-", "年");
        String second = first.replaceFirst("-", "月");
        return second + "日";
    }

    /**
     *
     * @param dateStr 2017年09月10日
     * @return 2017-09-10
     */
    public static String chinaToLine(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        String first = dateStr.replaceFirst("年", "-");
        String second = first.replaceFirst("月", "-");
        return second.replaceFirst("日", "");
    }
    /**
     * @param dateStr 2018.10.10
     * @return timestamp
     */
    public static long dotToTimestamp(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return 0;
        try {
            Date date = yyyyMMdd_dot.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }
    /**
     *
     * @param timestamp
     * @return 2018.10.10
     */
    public static String timestampToDot(long timestamp){
        Date date = new Date();
        date.setTime(timestamp);
        return yyyyMMdd_dot.format(date);
    }
    /**
     *
     * @param dateStr 2017-09-10
     * @return 2017/09/10日
     */
    public static String lineToItalic(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        return dateStr.replace("-", "/");
    }

    /**
     *
     * @param dateStr 20180909
     * @return timestamp
     */
    public static long toTimestamp(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return 0;
        try {
            Date date = DateUtil.yyyyMMdd.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            return 0l;
        }
    }

    public static String getYMDHM(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        Calendar today = Calendar.getInstance();
        long timeInMillis = Long.parseLong(dateStr);
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(timeInMillis);
        if(today.get(Calendar.YEAR)==temp.get(Calendar.YEAR)
                && today.get(Calendar.MONTH)==temp.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH)==temp.get(Calendar.DAY_OF_MONTH)){
            int hour1 = today.get(Calendar.HOUR_OF_DAY);
            int hour2 = temp.get(Calendar.HOUR_OF_DAY);
            if(hour1==hour2){
                int diff = today.get(Calendar.MINUTE) - temp.get(Calendar.MINUTE);
                return diff+"分钟前";
            }else{
                int diff = hour1 - hour2;
                return diff+"小时前";
            }
        }
        return yyyyMMddChina.format(temp.getTime());
    }

    public static String getYMD(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        Calendar today = Calendar.getInstance();
        long timeInMillis = Long.parseLong(dateStr);
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(timeInMillis);
        if(today.get(Calendar.YEAR)==temp.get(Calendar.YEAR)
                && today.get(Calendar.MONTH)==temp.get(Calendar.MONTH)){
            int day1 = today.get(Calendar.DAY_OF_MONTH);
            int day2 = temp.get(Calendar.DAY_OF_MONTH);
            if(day1==day2){
                return "今天稍早";
            }else if(day1-day2==1){
                return "昨天";
            }
        }
        return yyyyMMddChina.format(temp.getTime());
    }

    public static String getHM(String dateStr){
        if(TextUtils.isEmpty(dateStr)) return dateStr;
        long timeInMillis = Long.parseLong(dateStr);
        Date date = new Date(timeInMillis);
        return HM.format(date);
    }

}
