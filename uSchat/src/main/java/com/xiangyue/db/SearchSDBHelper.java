package com.xiangyue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class SearchSDBHelper {
    // 数据库名称
    public static String DATABASE_NAME;
    // 数据库版本号
    public static final int DATABASE_VERSION = 3;
    // 表名
    public static final String TABLE_NAME = "search_info";
    public static final String TABLE_NAME_DATA = "search_total";

    // 字段名
    // public static final String SEARCH_ITEM = "search_msg";
    public static final String[] SEARCH_COL = {DBSearch.Search.SEARCH_MSG};

    private SQLiteDatabase db;
    private SearchDBOpenHelper mHelper;

    public SearchSDBHelper(Context context, String username) {
        DATABASE_NAME = "search_history" + username;
        this.mHelper = new SearchDBOpenHelper(context, DATABASE_NAME);
        //打开数据库
        establishDB();
    }

    private void establishDB() {
        if (this.db == null) {
            this.db = this.mHelper.getWritableDatabase();
        }
    }

    //添加一条搜索记录
    public long insertOrUpdate(String search_msg) {
        boolean isUpdate = false;
        String[] searchInfos = queryAllSearch();
        //判断搜索记录是否重复
        for (int i = 0; i < searchInfos.length; i++) {
            if (search_msg.equals(searchInfos[i])) {
                isUpdate = true;
                break;
            }
        }
        long id = -1;
        if (isUpdate) {
            id = update(search_msg);
        } else {
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put(DBSearch.Search.SEARCH_MSG, search_msg);
                id = db.insert(TABLE_NAME, null, values);
            }
        }
        return id;
    }

    //替换一条搜索记录(去重问题)
    public long update(String search_msg) {
//		long id = db.delete(TABLE_NAME, Search.SEARCH_MSG + "='" +search_msg +"'", null);
//		long id = db.delete(TABLE_NAME, Search.SEARCH_MSG + "=?", new String[]{search_msg});

        String whereClause = DBSearch.Search.SEARCH_MSG + "=?";
        String[] whereArgs = new String[]{search_msg};
        long id = db.delete(TABLE_NAME, whereClause, whereArgs);

        ContentValues values = new ContentValues();
        values.put(DBSearch.Search.SEARCH_MSG, search_msg);
        id = db.insert(TABLE_NAME, null, values);
        return id;
    }


    //后插入所有数据
    public void insert(ArrayList<String> search_msg, ArrayList<String> search_msg1) {
        //批量添加
        db.beginTransaction();// 开启事物,让处理效率提高
        ContentValues values = new ContentValues();
        for (int i = 0; i < search_msg.size(); i++) {
            values.put(DBSearch.Search.SEARCH_MSG, search_msg.get(i));
            db.insert(TABLE_NAME_DATA, null, values);
        }
        for (int j = 0; j < search_msg1.size(); j++) {
            values.put(DBSearch.Search.SEARCH_MSG, search_msg1.get(j));
            db.insert(TABLE_NAME_DATA, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void deldata() {
        String sql2 = "DELETE FROM " + TABLE_NAME_DATA + ";";
        db.execSQL(sql2);
    }

    //查询所有的搜索记录
    public String[] queryAllSearch_data(String query) {
        if (db != null) {
//			Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            String sql = "select * from " + TABLE_NAME_DATA + " where " + DBSearch.Search.SEARCH_MSG + " like" + "'%" + query + "%'";
//            String sql = "select * from " + TABLE_NAME_DATA;
            Cursor cursor = db.rawQuery(sql, null);
            int count = cursor.getCount();
            String[] searchInfos = new String[count];
            if (count > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < count; i++) {
                    searchInfos[i] = cursor.getString(cursor.getColumnIndex(DBSearch.Search.SEARCH_MSG));
                    cursor.moveToNext();
                }

                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
            return searchInfos;
        } else {
            return new String[0];
        }
    }

    //查询所有的搜索记录
    public String[] queryAllSearch() {
        if (db != null) {
//			Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            String sql = "select * from " + TABLE_NAME + " order by " + DBSearch.Search._ID + " desc;";
            Cursor cursor = db.rawQuery(sql, null);
            int count = cursor.getCount();
            String[] searchInfos = new String[count];
            if (count > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < count; i++) {
                    searchInfos[i] = cursor.getString(cursor.getColumnIndex(DBSearch.Search.SEARCH_MSG));
                    cursor.moveToNext();
                }

                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
            if (searchInfos.length > 5) {
                String[] searchInfos_2 = new String[5];
                System.arraycopy(searchInfos, 0, searchInfos_2, 0, 5);
                return searchInfos_2;
            } else {
                return searchInfos;
            }
        } else {
            return new String[0];
        }
    }


    //清空数据库
    public void deleteAll() {
        String sql = "DELETE FROM " + TABLE_NAME + ";";
        db.execSQL(sql);
    }

    //关闭数据库
    public void cleanup() {
        if (this.db != null) {
            this.db.close();
            this.db = null;
        }
    }

    private static class SearchDBOpenHelper extends SQLiteOpenHelper {

        public SearchDBOpenHelper(Context context, String dataName) {
            super(context, dataName, null,
                    DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // sql语句
            String sql = "create table " + TABLE_NAME + " ( "
                    + DBSearch.Search._ID + " integer primary key,"
                    + DBSearch.Search.SEARCH_MSG + " text not null " + ")";
            String sql2 = "create table " + TABLE_NAME_DATA + " ( "
                    + DBSearch.Search._ID + " integer primary key,"
                    + DBSearch.Search.SEARCH_MSG + " text not null " + ")";
            db.execSQL(sql);
            db.execSQL(sql2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DATA + ";");
            onCreate(db);
        }
    }
}
