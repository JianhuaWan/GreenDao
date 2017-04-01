package com.example.wanjianhua.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.wanjianhua.gen.DaoMaster;
import com.example.wanjianhua.gen.DaoSession;

/**
 * Created by wanjianhua on 2017/3/22.
 */

public class BaseApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setDatabase();
    }

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "notes_db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}
