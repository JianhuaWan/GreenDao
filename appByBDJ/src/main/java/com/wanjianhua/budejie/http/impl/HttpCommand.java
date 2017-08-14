package com.wanjianhua.budejie.http.impl;

import com.wanjianhua.budejie.http.IHttpCommand;
import com.wanjianhua.budejie.http.IHttpRequestParam;
import com.wanjianhua.budejie.http.utils.HttpUtils;

/**
 * Created by ying on 2016/6/16.
 */
public class HttpCommand implements IHttpCommand {

    @Override
    public String execute(String url, IHttpRequestParam requestParam) {
        return HttpUtils.post(url, requestParam);
    }
}
