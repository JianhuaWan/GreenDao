package com.example.testdbbygreendao;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try
        {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if(time1 < time2)
            {
                diff = time2 - time1;
            }
            else
            {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTimes(String str1, String str2)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try
        {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if(time1 < time2)
            {
                diff = time2 - time1;
            }
            else
            {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms)
    {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0)
        {
            sb.append(day + "天");
        }
        if(hour > 0)
        {
            sb.append(hour + "小时");
        }
        if(minute > 0)
        {
            sb.append(minute + "分");
        }
        if(second > 0)
        {
            sb.append(second + "秒");
        }
        if(milliSecond > 0)
        {
            sb.append(milliSecond + "毫秒");
        }
        return sb.toString();
    }

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String longDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static Date parseLongDate(String dateString)
    {
        Date parse = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            parse = sdf.parse(dateString);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return parse;
    }

    public static String longDate(Date longDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(longDate);
    }

    public static String curTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * @return yyyy-MM-dd
     */
    public static String shortDate()
    {
        return shortDate(new Date());
    }

    public static String shortDate(Date date)
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date parseShortDate(String dateString)
    {
        Date parse = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            parse = sdf.parse(dateString);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return parse;
    }

    public static Date parseLongToShortDate(String dateString)
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = sdf.parse(dateString, pos);
        return strtodate;
    }

    public static String formatShortDate(long dateString)
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dateString);
    }

    public static String formatShortDate(String dateString)
    {
        String formatDateString = "";

        if(dateString != null)
        {
            if(!dateString.contains(":"))
            {
                return dateString;
            }

            Date date = parseShortDate(dateString);
            formatDateString = shortDate(date);
        }
        return formatDateString;
    }

    public static String formatDate(String dateString, String format)
    {
        if(dateString == null)
        {
            return "";
        }
        else
        {
            Date date = parseLongDate(dateString);
            if(date == null)
            {
                return "";
            }

            DateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
    }

    @Deprecated
    public static void setDateDialog(final Context context, final EditText txtDateEdit,
                                     final OnDateSetFinishListener listener)
    {
        txtDateEdit.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    Calendar calendar = Calendar.getInstance();
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            StringBuffer sBuffer = new StringBuffer();
                            sBuffer.append(year).append("-")
                                    .append((monthOfYear + 1) < 10 ?
                                            "0" + (monthOfYear + 1) : (monthOfYear + 1))
                                    .append("-").append(
                                    dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                            String string = sBuffer.toString();
                            txtDateEdit.setText(string);
                            if(listener != null)
                            {
                                listener.OnDateSetListener(string);
                            }
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return true;
            }
        });
    }


    public static void setDateDialog(final Context context, View txtDateEdit, final OnDateSetFinishListener listener)
    {
        txtDateEdit.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    Calendar calendar = Calendar.getInstance();
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            StringBuffer sBuffer = new StringBuffer();
                            sBuffer.append(year).append("-")
                                    .append((monthOfYear + 1) < 10 ?
                                            "0" + (monthOfYear + 1) : (monthOfYear + 1))
                                    .append("-").append(
                                    dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                            String string = sBuffer.toString();
                            if(listener != null)
                            {
                                listener.OnDateSetListener(string);
                            }
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return true;
            }
        });
    }

    public static void setTimeDialog(final Context context, final EditText txtDateEdit,
                                     final OnDateSetFinishListener listener)
    {
        txtDateEdit.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    Calendar calendar = Calendar.getInstance();
                    new TimePickerDialog(context, new OnTimeSetListener()
                    {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                        {
                            StringBuffer sBuffer = new StringBuffer();
                            sBuffer.append(hourOfDay).append(":").append(
                                    (minute) < 10 ? "0" + (minute) : (minute));
                            String string = sBuffer.toString();
                            txtDateEdit.setText(string);
                            if(listener != null)
                                listener.OnDateSetListener(string);
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
                return true;
            }
        });
    }

    /**
     * 昨日日期
     */
    public static String getYesterday()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
        return yesterday;
    }

    public interface OnDateSetFinishListener
    {
        void OnDateSetListener(String text);
    }

    public static String getTodayDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    /**
     * 获取要查询的历史签到日期
     *
     * @return
     */
    public static String getSignHistoryTime()
    {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = calendar.get(Calendar.MONTH) + 1 + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        return year + "/" + month + "/" + day;
    }

    // 获得当前应该显示的年月
    public static String getYearAndmonthAndDay(Date nowDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        String monStr = "" + month;
        if(month < 10)
        {
            monStr = "0" + month;
        }
        String dateStr = "" + day;
        if(day < 10)
        {
            dateStr = "0" + day;
        }
        return year + "-" + monStr + "-" + dateStr;
    }

    // 前一天
    public static String clickLeftDay(Date nowDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        nowDate = calendar.getTime();
        return getYearAndmonthAndDay(nowDate);
    }

    // 后一天
    public static String clickRightDay(Date nowDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        nowDate = calendar.getTime();
        return getYearAndmonthAndDay(nowDate);
    }

    // 签到时，gps时间与提交时间的间隔不得大于3分钟
    public static boolean isRefreshGPSTime(String gpsTime, String submintTime)
    {
        if(DateUtil.parseLongDate(submintTime) == null ||
                DateUtil.parseLongDate(gpsTime) == null)
        {
            return false;
        }

        if(DateUtil.parseLongDate(submintTime).getTime() -
                DateUtil.parseLongDate(gpsTime).getTime() > 180000)
        {
            return true;
        }
        return false;
    }

    // 选择时间不能提前与当前时间
    public static boolean isBeforeCurrentDate(String date)
    {
        Date currentDate = parseShortDate(shortDate());
        if(parseShortDate(date).compareTo(currentDate) > 0 ||
                parseShortDate(date).compareTo(currentDate) == 0)
        {
            return false;
        }
        return true;
    }


    /**
     * 获取months个月前/后的日期
     *
     * @param months -months为当前日期months个月前的后一天，+months为当前日期months个月后的前一天
     * @return
     */
    public final static Date addMonthsToDate(int months)
    {
        Calendar calendar = Calendar.getInstance();
        // 得到前/后months个月
        calendar.add(Calendar.MONTH, months);
        if(months > 0)
        {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        else if(months < 0)
        {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return calendar.getTime();
    }

    /**
     * 日期格式判断（yyMMDD）
     *
     * @param date
     * @return
     */
    public static boolean isData(String date)
    {
        boolean convertSuccess = true;
        // 指定日期格式为两位年/两位月份/两位日期；
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        try
        {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(date);
        }
        catch(ParseException e)
        {
            //格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

}
