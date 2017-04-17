package com.example.rxjavamvpretrofitdagger2.service;

import com.example.rxjavamvpretrofitdagger2.bean.MovieBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wanjianhua on 2017/4/17.
 */

public interface MovieService {
    //原生retrofit
    @GET("top250")
    Call<MovieBean> getTopMovie(@Query("start") int start, @Query("count") int count);

    //retrofit+rxjava
    @GET("top250")
    Observable<MovieBean> getTopMoviebyRx(@Query("start") int start, @Query("count") int count);
}
