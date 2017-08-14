package com.wanjianhua.budejie.http;

/**
 * Created by ying on 2016/6/21.
 */
public interface IHttpThreadExecutor {
    public void addTask(Runnable runnable);
}
