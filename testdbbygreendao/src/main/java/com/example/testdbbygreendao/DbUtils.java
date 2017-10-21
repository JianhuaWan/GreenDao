package com.example.testdbbygreendao;

public class DbUtils
{
    private static TaskViewEntityDaoImpl sTaskViewEntityDaoImpl;

    public static TaskViewEntityDaoImpl getsTaskViewEntityDaoImpl()
    {
        if(sTaskViewEntityDaoImpl == null)
        {
            TaskViewEntityDao taskViewEntityDao =
                    GreenDao.getDaoSession().getTaskViewEntityDao();
            sTaskViewEntityDaoImpl = new TaskViewEntityDaoImpl(taskViewEntityDao);
        }
        return sTaskViewEntityDaoImpl;
    }
}
