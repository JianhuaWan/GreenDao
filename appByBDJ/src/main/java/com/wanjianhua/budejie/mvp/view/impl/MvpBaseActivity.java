package com.wanjianhua.budejie.mvp.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wanjianhua.budejie.mvp.presenter.MvpPresenter;
import com.wanjianhua.budejie.mvp.view.MvpView;

/**
 * Created by ying on 2016/6/2.
 */
public abstract class MvpBaseActivity<M,P extends MvpPresenter> extends AppCompatActivity implements MvpView {

    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = bindPresenter();
        if(presenter != null){
            presenter.attach(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.destroy();
        }

    }

    protected abstract P bindPresenter();
    protected P getPresenter(){
        if(presenter == null){
            throw new NullPointerException("请先绑定presenter在使用");
        }
        return presenter;
    }


}
