package com.wanjianhua.budejie.http;

/**
 * Created by ying on 2016/6/16.
 */
public interface IHttpRequestParam<T,H> {
    public void put(String key,Object value);
    public Object get(String key);
    public int size();
    public T getRequestParam();

    public H getHeaderParam();
    public void putHeader(String key, Object value);
    public Object getHeader(String key);
    public int headerSize();

}
