package com.wanjianhua.stock.act.base;

import android.app.Application;

import com.wanjianhua.stock.R;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class BaseApplication extends Application {
    public static String[] TABLE_NAMES = null;

    @Override
    public void onCreate() {
        super.onCreate();
        TABLE_NAMES = new String[]{getString(R.string.site), getString(R.string.mine)};
    }
}
