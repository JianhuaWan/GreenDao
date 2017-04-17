package com.example.rxjavamvpretrofitdagger2.base;

import android.app.Application;

import butterknife.ButterKnife;

/**
 * Created by wanjianhua on 2017/4/17.
 */

public class BaseApplication extends Application {

    private static BaseApplication myApplication = null;

    public static BaseApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }
}
