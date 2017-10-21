package com.example.testdbbygreendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoLog;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.CursorQuery;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import de.greenrobot.dao.query.WhereCondition.StringCondition;

/**
 * @param <T>Entity        type
 * @param <K>主键类型，如果没有主键使用 Void
 */
@SuppressWarnings("unchecked")
public class BaseDao<T, K>
{

    protected AbstractDao<T, K> mDao;

    @SuppressWarnings("rawtypes")
    public BaseDao(AbstractDao dao)
    {
        mDao = dao;
    }

    public void insert(T entity)
    {
        mDao.insert(entity);
    }

    public void insert(List<T> entities)
    {
        mDao.insertInTx(entities);
    }

    public void insertOrUpdate(T entity)
    {
        mDao.insertOrReplace(entity);
    }

    public void insertOrUpdate(List<T> entities)
    {
        mDao.insertOrReplaceInTx(entities);
    }

    public void deleteByKey(K key)
    {
        mDao.deleteByKey(key);
    }

    public void delete(T entity)
    {
        if(entity != null)
            mDao.delete(entity);
    }

    public void delete(T... entities)
    {
        mDao.deleteInTx(entities);
    }

    public void delete(List<T> entities)
    {
        mDao.deleteInTx(entities);
    }

    public void deleteAll()
    {
        mDao.deleteAll();
    }

    public void update(T entity)
    {
        mDao.update(entity);
    }

    public void update(T... entities)
    {
        mDao.updateInTx(entities);
    }

    public void update(List<T> entities)
    {
        mDao.updateInTx(entities);
    }

    public T query(K key)
    {
        return mDao.load(key);
    }

    public List<T> queryAll()
    {
        return mDao.loadAll();
    }

    public List<T> query(String where, String... params)
    {
        return mDao.queryRaw(where, params);
    }

    public QueryBuilder<T> queryBuilder()
    {
        return mDao.queryBuilder();
    }

    public long count()
    {
        return mDao.count();
    }

    public void refresh(T entity)
    {
        mDao.refresh(entity);
    }

    public void detach(T entity)
    {
        mDao.detach(entity);
    }

    public void execSQL(String sql)
    {
        checkLog(sql, null);
        getDatabase().execSQL(sql);
    }

    public void execSQL(String sql, Object[] bindArgs)
    {
        checkLog(sql, bindArgs);
        getDatabase().execSQL(sql, bindArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs)
    {
        checkLog(sql, selectionArgs);
        return getDatabase().rawQuery(sql, selectionArgs);
    }

    public SQLiteDatabase getDatabase()
    {
        return mDao.getDatabase();
    }

    /**
     * 查询时忽略大小写函数
     */
    public WhereCondition ignoreCase(Property property, String value)
    {
        return new StringCondition(property.columnName + " = \"" + value + "\" COLLATE NOCASE ");
    }

    /**
     * like查询使用这个，默认like查询需要指定全名称
     */
    public WhereCondition like(Property property, String value)
    {
        return new StringCondition(property.columnName + " like \"%" + value + "%\" ");
    }

    /**
     * 判断参数是否为空的处理
     *
     * @param property
     * @param param
     * @return 如果参数为null, 返回 isNull 条件，不为null,返回 eq 条件。
     */
    public WhereCondition eqIsNull(Property property, Object param)
    {
        WhereCondition sitewr = null;
        if(param == null)
            sitewr = property.isNull();
        else
            sitewr = property.eq(param);
        return sitewr;
    }

    /**
     * 判断参数是否为空的处理
     *
     * @param property
     * @param value
     * @return 如果参数为null, 返回 isNull 条件，不为null,返回 eq 条件。
     */
    public WhereCondition likeIsNull(Property property, String value)
    {
        WhereCondition titlewr = null;
        if(value == null)
            titlewr = property.isNull();
        else
            titlewr = like(property, value);
        return titlewr;
    }

    /**
     * 查询某列的最大值
     */
    public String queryLastUpdateData()
    {
        Property property = getLastUpdateColumn();
        if(property == null)
        {
            throw new RuntimeException("You must input the column property!");
        }
        String result = null;
        CursorQuery buildCursor = mDao.queryBuilder().orderDesc(property).buildCursor();
        Cursor query = buildCursor.query();
        if(query.moveToNext())
        {
            result = query.getString(query.getColumnIndex(property.columnName));
        }
        return result == null ? "1900-01-01 00:00:00" : result;
    }

    /**
     * 查询某列的最大值:返回此列的Property
     */
    public Property getLastUpdateColumn()
    {
        throw new RuntimeException("Please overide the method and return the lastUpdate column proterty!");
    }

    /**
     * <pre>
     * 1.查询唯一值:
     * 参考：com.test.dao.impl.project.ProjectUserRelateEntityDaoImpl.queryByProjID(String)
     *     ProjectUserRelateEntity unique = queryBuilder().where(ProjectUserRelateEntityDao.Properties.UserID.eq(queryCurrentUserAccount()),
     *                                                               ProjectUserRelateEntityDao.Properties.IsDefault.eq(true))
     *                                                        .unique()
     * 2.查询List集合
     * 参考：com.test.dao.impl.project.ProjectUserRelateEntityDaoImpl.queryCurrentUserRelateEntities()
     *  List<ProjectUserRelateEntity> list = queryBuilder().where(ProjectUserRelateEntityDao.Properties.UserID.eq(queryCurrentUserAccount()))
     *                                                            .build().list();
     *
     * 3.使用sqlite自定义条件查询:手动生成where条件,不使用自动生成
     * 参考：com.test.dao.impl.project.IssueRelateUserEntityDaoImpl.queryListByUserIdAndProjId(String)
     * 可使用StringCondition("");生成where后面的条件
     *   WhereCondition where1 = new StringCondition(IssueRelateUserEntityDao.Properties.UserID.columnName + " = '"
     *                                                     + queryCurrentUserID() + "' COLLATE NOCASE ");
     *   return queryBuilder().where(where1).build().list();
     *
     * 4.如果表结构类似联合主键，需要重写所有保存数据方法
     * 参考：com.test.dao.impl.project.ProjectUserRelateEntityDaoImpl
     *
     * 5.使用join查询时:源表列查询条件 ,目标表列查询条件
     * 参考：com.test.dao.impl.task.TemplateEntityDaoImpl.queryNormaList(String)
     *
     * 6. 查询时忽略大小写函数
     * com.test.BaseDao.ignoreCase(Property, String)
     *
     * 7.like查询
     * com.test.BaseDao.like(Property, String)
     * </pre>
     */
    private void checkLog(String sql, Object[] values)
    {
        if(QueryBuilder.LOG_SQL)
        {
            DaoLog.d("Built SQL for query: " + sql);
        }
        if(QueryBuilder.LOG_VALUES)
        {
            DaoLog.d(String.format("Values for query: %s", values));
        }
    }
}
