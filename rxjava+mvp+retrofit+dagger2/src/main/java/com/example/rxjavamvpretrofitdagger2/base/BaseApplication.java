package com.example.rxjavamvpretrofitdagger2.base;

import android.app.Application;

import com.example.rxjavamvpretrofitdagger2.service.BaseUrl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
