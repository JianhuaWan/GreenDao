package com.wanjianhua.budejie.http.utils;

import android.os.Handler;
import android.os.Message;

import com.wanjianhua.budejie.http.IHttpRequestParam;
import com.wanjianhua.budejie.http.IHttpThreadExecutor;
import com.wanjianhua.budejie.http.impl.HttpCommand;

/**
 * Created by ying on 2016/6/21.
 */
public class HttpTask {
    static IHttpThreadExecutor executor = ThreadPoolUtils.getInstance();

    public static void requestPost(String url, IHttpRequestParam requestParam, final OnHttpResultListener listener) {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                listener.onResult(msg.obj.toString());
            }
        };
        HttpCommand httpCommand = new HttpCommand();
        Runnable httpRunnable = new HttpRunnable(url, httpCommand, requestParam, handler);
        //添加到线程池中 线程池待实现
        executor.addTask(httpRunnable);
    }

    public interface OnHttpResultListener {
        void onResult(String result);
    }
}
