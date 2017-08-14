package com.wanjianhua.budejie.pro.essence.view;

import com.wanjianhua.budejie.mvp.presenter.MvpPresenter;
import com.wanjianhua.budejie.mvp.presenter.impl.MvpBasePresenter;
import com.wanjianhua.budejie.pro.base.view.BaseFragment;

/**
 * Created by ying on 2016/6/13.
 */
public abstract class EssenceContentFragment<P extends MvpPresenter> extends BaseFragment<P> {
    private int mType;
    private String mTitle;


    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
