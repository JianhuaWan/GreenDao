package com.wanjianhua.budejie.http;

/**
 * Created by ying on 2016/6/16.
 */
public interface IHttpCommand {
    public String execute(String url,IHttpRequestParam requestParam);
}
