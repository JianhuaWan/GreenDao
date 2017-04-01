package com.im.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.im.base.BaseActivity;
import com.im.bean.User;
import com.im.model.UserModel;


/**
 * 启动界面
 *
 * @author :smile
 * @project:SplashActivity
 * @date :2016-01-15-18:23
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = UserModel.getInstance().getCurrentUser();
                if (user == null) {
                    startActivity(com.xiangyue.act.LoginActivity.class, null, true);
                } else {
                    startActivity(MainActivity.class, null, true);
                }
            }
        }, 0);

    }
}
