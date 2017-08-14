package com.wanjianhua.budejie.mvp.view.impl;

import com.wanjianhua.budejie.mvp.view.MvpView;

/**
 * Created by ying on 2016/6/2.
 */
public interface MvpImplView<M> extends MvpView {
    void showLoading(boolean isShow);

    void showData(M data);

    void showError(Exception e);
}
