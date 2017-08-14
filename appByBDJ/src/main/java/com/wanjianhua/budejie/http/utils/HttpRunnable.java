package com.wanjianhua.budejie.http.utils;

import android.os.Handler;
import android.os.Message;

import com.wanjianhua.budejie.http.IHttpCommand;
import com.wanjianhua.budejie.http.IHttpRequestParam;

import java.util.Map;

/**
 * Created by ying on 2016/6/21.
 */
public class HttpRunnable implements Runnable {
    private String url;
    private IHttpCommand command;
    private IHttpRequestParam<Map<String, Object>, Map<String, Object>> requestParam;
    private Handler handler;

    public HttpRunnable(String url, IHttpCommand command, IHttpRequestParam<Map<String, Object>, Map<String, Object>> requestParam, Handler handler) {
        this.url = url;
        this.command = command;
        this.requestParam = requestParam;
        this.handler = handler;
    }

    @Override
    public void run() {
        String response = command.execute(url, requestParam);
        Message message = handler.obtainMessage();
        message.obj = response;
        handler.sendMessage(message);
    }
}
