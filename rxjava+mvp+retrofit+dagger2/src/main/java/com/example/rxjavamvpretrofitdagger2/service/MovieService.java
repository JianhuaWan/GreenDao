package com.example.rxjavamvpretrofitdagger2.service;

import com.example.rxjavamvpretrofitdagger2.bean.MovieBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    @POST("v2/movie/top250")
    Observable<MovieBean> getTopMoviebyRx(@Query("start") int start, @Query("count") int count);

    //上传文件(图片文件字符串一起上传)
//    @Multipart
//    @POST("Test/")
//    Observable<MovieBean> upload(@Query("name") String carName, @Query("phone") String carphone,
//                                 @Part("avatar\\";filename =\\"avatar.jpg") RequestBody avatar);

    //图片上传(多个,单个)
    @Multipart
    @POST("upload")
    Observable<MovieBean> updateImage(@PartMap Map<String, RequestBody> params);

    @POST("top250")
    Observable<MovieBean> getTopMovieByRxPost(@Query("start") int start, @Query("count") int count);
}
