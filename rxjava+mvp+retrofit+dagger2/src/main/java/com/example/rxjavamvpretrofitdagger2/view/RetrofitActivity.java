package com.example.rxjavamvpretrofitdagger2.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rxjavamvpretrofitdagger2.R;
import com.example.rxjavamvpretrofitdagger2.base.BaseActivity;
import com.example.rxjavamvpretrofitdagger2.bean.MovieBean;
import com.example.rxjavamvpretrofitdagger2.bus.RefreshMessage;
import com.example.rxjavamvpretrofitdagger2.bus.RxBus;
import com.example.rxjavamvpretrofitdagger2.service.BaseUrl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;


/**
 * Created by wanjianhua on 2017/4/17.
 */

public class RetrofitActivity extends BaseActivity {
    @Bind(R.id.retrofit)
    Button retrofit;
    @Bind(R.id.retrofitrx)
    Button retrofitrx;
    @Bind(R.id.rxbus)
    Button rxbus;
    private Subscriber<MovieBean> subscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_main);
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

    @OnClick({R.id.retrofit, R.id.retrofitrx, R.id.rxbus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.retrofit:
                break;
            case R.id.retrofitrx:
                getMovieByRx();
                break;
            case R.id.rxbus:
                RxBus.getInstance().post(new RefreshMessage("mingzi", "333"));
                break;
        }
    }

}
