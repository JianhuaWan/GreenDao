package com.example.rxjavamvpretrofitdagger2.service;

import com.example.rxjavamvpretrofitdagger2.bean.MovieBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wanjianhua on 2017/4/17.
 * 请求过程进行封装
 */

public class BaseUrl {
    public static final String baseUrl = "https://api.douban.com/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private MovieService movieService;

    //构造方法私有
    private BaseUrl() {
        OkHttpClient.Builder httpclientBuilder = new OkHttpClient.Builder();
        httpclientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpclientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        movieService = retrofit.create(MovieService.class);
    }

    //访问baseurl创建单例
    private static class SingleHolder {
        private static final BaseUrl INSTANCE = new BaseUrl();
    }

    //获取单例
    public static BaseUrl getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void getTopMovie(Subscriber<MovieBean> subscriber, int start, int count) {
        movieService.getTopMoviebyRx(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
