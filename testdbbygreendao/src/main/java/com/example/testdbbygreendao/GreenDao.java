package com.example.testdbbygreendao;

import android.content.Context;

import java.util.Collection;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * GreenDao辅助工具类
 */
public class GreenDao
{

    private static Context mContext;

    private static DaoMaster daoMaster;

    private static DaoSession newSession;

    private static MySQLiteOpenHelper devOpenHelper;

    public static void init(Context context)
    {
        if(context == null)
        {
            throw new IllegalArgumentException("context can't be null!");
        }
        mContext = context.getApplicationContext();
    }

    public static MySQLiteOpenHelper getSqLiteOpenHelper()
    {
        if(devOpenHelper == null)
        {
            synchronized(GreenDao.class)
            {
                devOpenHelper = new MySQLiteOpenHelper(mContext);
            }
        }
        return devOpenHelper;
    }

    /**
     * 获取DaoMaster实例
     */
    public static DaoMaster getDaoMaster()
    {
        if(daoMaster == null)
        {
            MySQLiteOpenHelper devOpenHelper = getSqLiteOpenHelper();
            daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 获取DaoSession实例
     */
    public static DaoSession getDaoSession()
    {
        if(newSession == null)
        {
            if(daoMaster == null)
            {
                daoMaster = getDaoMaster();
            }
            newSession = daoMaster.newSession();
        }
        return newSession;
    }

    /**
     * 删除所有表数据
     */
    public static void deleteAllDatas()
    {
        Collection<AbstractDao<?, ?>> allDaos = getDaoSession().getAllDaos();
        for(AbstractDao<?, ?> abstractDao : allDaos)
        {
            abstractDao.deleteAll();
        }
    }

    /**
     * 释放数据及资源
     */
    public static void release()
    {
        releaseBussinessData();
        devOpenHelper = null;
        daoMaster = null;
        newSession = null;
        mContext = null;
    }

    /**
     * 打开查询时日志记录
     */
    public static void enableQueryBuilderLog()
    {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 数据库相关业务数据释放
     */
    public static void releaseBussinessData()
    {
    }
}
