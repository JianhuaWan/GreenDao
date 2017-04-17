package com.example.rxjavamvpretrofitdagger2.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.rxjavamvpretrofitdagger2.R;
import com.example.rxjavamvpretrofitdagger2.base.BaseActivity;
import com.example.rxjavamvpretrofitdagger2.bean.MovieBean;
import com.example.rxjavamvpretrofitdagger2.service.BaseUrl;

import rx.Subscriber;


/**
 * Created by wanjianhua on 2017/4/17.
 */

public class RetrofitActivity extends BaseActivity implements OnClickListener {
    private Button retrofit, retrofitrx;
    private Subscriber<MovieBean> subscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_main);
        initView();
    }

    private void initView() {
        retrofit = (Button) findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);
        retrofitrx = (Button) findViewById(R.id.retrofitrx);
        retrofitrx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrofit:
                break;
            case R.id.retrofitrx:
                getMovieByRx();
                break;
            default:
                break;
        }
    }

    private void getMovieByRx() {
        subscriber = new Subscriber<MovieBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RetrofitActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                //subscriber.unsubscribe();//取消一个网络请求
                Toast.makeText(RetrofitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(MovieBean movieBean) {
                Log.e("content", movieBean.getTitle());
            }
        };
        BaseUrl.getInstance().getTopMovie(subscriber, 0, 10);
    }

}
