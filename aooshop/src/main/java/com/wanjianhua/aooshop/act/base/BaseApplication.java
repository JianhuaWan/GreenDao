package com.wanjianhua.aooshop.act.base;

import android.app.Application;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.utils.CrashHandler;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class BaseApplication extends Application
{
    private static final String APPID = "f5496a61a6f0d59fe4a8530450fb4782";
    public static String[] TABLE_NAMES = null;
    public static String[] PREFERREDMAIN = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        CrashHandler.init(getApplicationContext()); // 在Application里面设置我们的异常处理器为UncaughtExceptionHandler处理器
        TABLE_NAMES = new String[]{getString(R.string.preferred), getString(R.string.order), getString(R.string.mine)};
        PREFERREDMAIN = new String[]{getString(R.string.preferred), getString(R.string.order), getString(R.string.mine), getString(R.string.preferred), getString(R.string.order), getString(R.string.mine)};
    }
}
