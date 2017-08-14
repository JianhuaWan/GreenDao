package com.wanjianhua.budejie.mvp.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wanjianhua.budejie.mvp.presenter.MvpPresenter;
import com.wanjianhua.budejie.mvp.view.MvpView;

/**
 * Created by ying on 2016/6/2.
 */
public abstract class MvpBaseFragment<P extends MvpPresenter> extends Fragment implements MvpView {

    private P presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = bindPresenter();
        if (presenter != null) {
            presenter.attach(this);
        }

    }

    protected P getPresenter() {
        if (presenter == null) {
            throw new NullPointerException("请先绑定presenter在使用");
        }
        return presenter;
    }

    protected abstract P bindPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.destroy();
        }
    }
}
