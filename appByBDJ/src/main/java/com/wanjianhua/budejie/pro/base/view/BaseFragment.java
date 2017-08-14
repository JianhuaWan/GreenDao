package com.wanjianhua.budejie.pro.base.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wanjianhua.budejie.mvp.presenter.MvpPresenter;
import com.wanjianhua.budejie.mvp.view.impl.MvpBaseFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ying on 2016/6/2.
 */
public abstract class BaseFragment<P extends MvpPresenter> extends MvpBaseFragment<P> {

    private View view;
    public String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            initView(view);
            Log.e(TAG, "onCreateView __ initView");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
            Log.e(TAG, "onCreateView __ removeView");
        }
        Log.e(TAG, "onCreateView end");
        return view;
    }

    SweetAlertDialog pDialog;

    public void showLoading(boolean isShow) {
        if (isShow) {
            if (pDialog == null) {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading...");
                pDialog.setCancelable(false);
            }
            pDialog.show();
        } else {
            if (pDialog != null) {
                pDialog.dismiss();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected void initData() {

    }

    @Override
    protected P bindPresenter() {
        return null;
    }

    protected abstract void initView(View view);

    protected abstract int getLayoutId();
}
