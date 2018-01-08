package com.wanjianhua.aooshop.act.base;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class BaseActivity extends FragmentActivity {
    protected NotificationManager notificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
