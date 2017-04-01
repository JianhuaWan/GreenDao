package com.xiangyue.weight;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @author mWX217269 Email: meiliang1@huawei.com
 *         create date: 2014年5月8日 下午5:11:07
 *         日期格式化处理
 */
public class DateUtils {
    public static final String FORMAT_MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static final String FORMAT_MM_DD_HH_MM = "MM-dd HH:mm";
    public static final String FORMART_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMART_YYYY_MM_DD_ = "yyyyMMdd";
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_HH_MM_YYYY_MM_DD = "HH:mm yyyy-MM-dd";

    /**
     * 将时间戳转化成05-08 17:14:00
     *
     * @param time
     * @return
     */
    public static String formatDateMMDDHHMMSS(int time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return formatDate(c.getTime(), FORMAT_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化时间戳为20140714
     *
     * @param time
     * @return
     */
    public static String formatDateYYYYMMDD(int time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return formatDate(c.getTime(), FORMART_YYYY_MM_DD_);
    }


    public static String formatDateYYYYMMDDHHMM(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return formatDate(c.getTime(), "yyyy-MM-dd HH:mm");
    }

    /**
     * 将时间戳转化成05-08 17:14
     *
     * @param time
     * @return
     */
    public static String formatDateMMDDHHMM(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return formatDate(c.getTime(), FORMAT_MM_DD_HH_MM);
    }

    /**
     * 将时间戳转date
     *
     * @param time
     * @return
     */
    public static Date formatLongToDate(long time, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        String dateStr = formatDate(c.getTime(), format);
        return formatStringToDate(dateStr, format);
    }

    /**
     * 获取当前的日期
     * 如：2014-05-08
     *
     * @return
     */
    public static String formatCurrentDate() {
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(FORMART_YYYY_MM_DD);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Date d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    /**
     * 方法作用及简要逻辑描述： 字符串转日期
     * zWX239748
     * 2015-6-29
     * String
     */
    @SuppressLint("SimpleDateFormat")
    public static Date formatStringToDate(String d, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getOneYearBefore() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(d.getTime() - 365 * 24 * 60 * 60 * 1000));
        //System.out.println("三天后的日期：" + df.format(new Date(d.getTime() + 3 * 24 * 60 * 60 * 1000)));
    }

    public static String getOneMothBefore() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(d.getTime() - 30 * 24 * 60 * 60 * 1000));
    }
}
