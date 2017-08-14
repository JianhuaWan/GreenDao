package com.wanjianhua.budejie.pro.base.presenter;

import android.content.Context;

import com.wanjianhua.budejie.mvp.model.MvpModel;
import com.wanjianhua.budejie.mvp.presenter.impl.MvpBasePresenter;
import com.wanjianhua.budejie.mvp.view.MvpView;

/**
 * Created by ying on 2016/6/2.
 */
public abstract class BasePresenter<V extends MvpView, M extends MvpModel> extends MvpBasePresenter<V, M> {

    private Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
