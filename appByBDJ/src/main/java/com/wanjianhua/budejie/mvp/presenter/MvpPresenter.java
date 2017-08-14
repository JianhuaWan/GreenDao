package com.wanjianhua.budejie.mvp.presenter;

import com.wanjianhua.budejie.mvp.model.MvpModel;
import com.wanjianhua.budejie.mvp.view.MvpView;

/**
 * Created by ying on 2016/6/2.
 */
public interface MvpPresenter<V extends MvpView, M extends MvpModel> {
    void attach(V view);

    void destroy();

    //    V getView();
//    boolean isDestroy();
    M bindModel();
//    M getModel();
}
