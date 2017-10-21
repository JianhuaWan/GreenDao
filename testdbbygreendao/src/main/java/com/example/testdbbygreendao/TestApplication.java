package com.example.testdbbygreendao;

import android.app.Application;


public class TestApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        // GreenDao初始化
        GreenDao.init(getApplicationContext());
        GreenDao.getDaoSession();
    }
}
