package com.wanjianhua.stock.act.base;

import android.app.Application;

import com.wanjianhua.stock.R;

import cn.bmob.v3.Bmob;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class BaseApplication extends Application {
    private static final String APPID = "f5496a61a6f0d59fe4a8530450fb4782";
    public static String[] TABLE_NAMES = null;

    @Override
    public void onCreate() {
        super.onCreate();
        TABLE_NAMES = new String[]{getString(R.string.site), getString(R.string.mine)};
        Bmob.initialize(this, APPID, "stock");
    }
}
