package com.example.testdbbygreendao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

@SuppressWarnings("unchecked")
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper
{

    public MySQLiteOpenHelper(Context context)
    {
        super(context, "test.db", null);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // 先创建原来的表结构，再创建新的表结构
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.e("onUpgrade", "newVersion:" + newVersion + ",oldVersion:"
                + oldVersion);
        //任务视图
        TaskViewEntityDao.createTable(db, false);
    }


    public void executeSql(SQLiteDatabase db, String sqlString)
    {
        db.execSQL(sqlString);
    }

    /**
     * 返回表是否存在
     *
     * @return true 表存在，false 表不存在
     */
    public boolean isTableExist(SQLiteDatabase db, String tableName)
    {
        boolean result = false;
        if(tableName == null)
        {
            return result;
        }
        String sql = "select count(*) as c from sqlite_master where type='table' and name = '"
                + tableName.trim() + "'";
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToFirst())
            {
                int count = cursor.getInt(0);
                if(count > 0)
                {
                    result = true;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(cursor != null)
            {
                cursor.close();
            }
        }
        return result;
    }
}
