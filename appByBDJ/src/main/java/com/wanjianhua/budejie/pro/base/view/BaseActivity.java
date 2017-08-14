package com.wanjianhua.budejie.pro.base.view;

import android.graphics.Color;

import com.wanjianhua.budejie.mvp.presenter.MvpPresenter;
import com.wanjianhua.budejie.mvp.view.impl.MvpBaseActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ying on 2016/6/2.
 */
public class BaseActivity<M, P extends MvpPresenter> extends MvpBaseActivity<M, P> {


    SweetAlertDialog pDialog;

    public void showLoading(boolean isShow) {
        if (isShow) {
            if (pDialog == null) {
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
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
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    protected P bindPresenter() {
        return null;
    }


}
