package com.example.testdbbygreendao;


import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;


public class TaskViewEntityDaoImpl extends BaseDao<TaskViewEntity, String>
{
    public TaskViewEntityDaoImpl(AbstractDao dao)
    {
        super(dao);
    }

    /**
     * 查询列表
     *
     * @return
     */
    public List<TaskViewEntity> getTaskViewListByDisplayable(String taskID)
    {
        QueryBuilder<TaskViewEntity> queryBuilder = queryBuilder();
        //任务id
        queryBuilder.where(ignoreCase(TaskViewEntityDao.Properties.TaskId, taskID));
        return queryBuilder.orderDesc(TaskViewEntityDao.Properties.UpdateDate).build().list();
    }

    /**
     * 查询列表所有
     *
     * @return
     */
    public List<TaskViewEntity> getTaskViewListByDisplayable()
    {
        QueryBuilder<TaskViewEntity> queryBuilder = queryBuilder();
        return queryBuilder.orderDesc(TaskViewEntityDao.Properties.UpdateDate).build().list();
    }

    /**
     * 修改
     *
     * @param
     */
    public void setTaskViewEntityDisplayed(TaskViewEntity taskViewEntity)
    {
        taskViewEntity.setUpdateDate(DateUtil.shortDate());
        DbUtils.getsTaskViewEntityDaoImpl().insertOrUpdate(taskViewEntity);
    }

    public void setTaskViewListHadDisplayed(List<TaskViewEntity> list)
    {
        for(TaskViewEntity taskViewEntity : list)
        {
            taskViewEntity.setCreateDate(DateUtil.shortDate());
        }
        DbUtils.getsTaskViewEntityDaoImpl().insertOrUpdate(list);
    }
}
