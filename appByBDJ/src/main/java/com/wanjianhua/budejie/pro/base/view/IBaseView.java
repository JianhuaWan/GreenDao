package com.wanjianhua.budejie.pro.base.view;

import com.wanjianhua.budejie.mvp.view.MvpView;

/**
 * Created by ying on 2016/6/16.
 */
public interface IBaseView<T> extends MvpView {
    void showDialog();

    void hideDialog();

    void loadData(T data, boolean isDownRefresh);

    void error(Exception e);
}
