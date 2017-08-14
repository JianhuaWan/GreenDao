package com.wanjianhua.budejie.http.impl;

import com.wanjianhua.budejie.http.IHttpRequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ying on 2016/6/16.
 */
public class HttpRequestParam implements IHttpRequestParam<Map<String, Object>, Map<String, Object>> {
    private Map<String, Object> paramMap = new HashMap<String, Object>();
    private Map<String, Object> headerMap = new HashMap<String, Object>();

    @Override
    public void put(String key, Object value) {
        paramMap.put(key, value);
    }

    @Override
    public Object get(String key) {
        return paramMap.get(key);
    }

    @Override
    public int size() {
        return paramMap.size();
    }

    @Override
    public Map<String, Object> getRequestParam() {
        return paramMap;
    }

    @Override
    public Map<String, Object> getHeaderParam() {
        return headerMap;
    }

    @Override
    public void putHeader(String key, Object value) {
        headerMap.put(key, value);
    }

    @Override
    public Object getHeader(String key) {
        return headerMap.get(key);
    }

    @Override
    public int headerSize() {
        return headerMap.size();
    }
}
