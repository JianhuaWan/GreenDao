package com.xiangyue.base;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xiangyue.provider.BusProvider;

public class BaseActivity extends FragmentActivity implements AppContext {
    public static final int PRASSAGE_DIALOG = 0x01;
    public Dialog mLoadDialog;
    private static final int notifiId = 11;
    protected NotificationManager notificationManager;

    /**
     * 取消加载中对话框
     */
    @SuppressWarnings("deprecation")
    public void dismissLoadDialog() {
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            dismissDialog(PRASSAGE_DIALOG);
        }
    }

    /**
     * 显示加载中对话框
     */
    @SuppressWarnings("deprecation")
    public void showLoadDialog() {
        showDialog(PRASSAGE_DIALOG);
    }

    @SuppressWarnings("deprecation")
    // @Override
    // protected Dialog onCreateDialog(int id) {
    // switch (id) {
    // case PRASSAGE_DIALOG:
    // if (mLoadDialog != null && mLoadDialog.isShowing()) {
    // mLoadDialog.dismiss();
    // mLoadDialog = null;
    // }
    // // mLoadDialog = CommonDialogFactory.getLoadingDialog(this);
    // mLoadDialog.setCancelable(true);
    // return mLoadDialog;
    // }
    // return super.onCreateDialog(id);
    // }
    // public void setDialogCancelable(boolean can) {
    // if (null != mLoadDialog) {
    // mLoadDialog.setCancelable(can);
    // }
    // }
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * Activity跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void redictToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void redictToActivity(Context context, Class<?> targetActivity, Bundle bundle1, Bundle bundle2) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle1) {
            intent.putExtras(bundle1);
        }
        if (null != bundle2) {
            intent.putExtras(bundle2);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * 获取当前应用程序的版本号
     */
    public String getVersion() {
        String st = "0";
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return st;
        }
    }
}
