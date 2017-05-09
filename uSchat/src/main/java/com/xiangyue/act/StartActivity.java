package com.xiangyue.act;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.type.User;
import com.xiangyue.util.SysApplication;

import cn.bmob.v3.listener.SaveListener;

public class StartActivity extends BaseActivity {
    private SharedPreferences preferences;
    Context mContext;
    private final static int TOLOGIN = 0;// 未登录
    private final static int TOMAIN = 1;// 已登录
    private MicroRecruitSettings settings;
    private boolean count;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TOLOGIN:
                    redictToActivity(StartActivity.this, GuideActivity.class, null);
                    // redictToActivity(StartActivity.this,
                    // ChoiceRoleActivity.class, null);
                    finish();
                    break;
                case TOMAIN:
                    redictToActivity(StartActivity.this, MainActivity.class, null);
//                    redictToActivity(StartActivity.this, GuideActivity.class, null);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        mContext = this;
//        View view = View.inflate(StartActivity.this, R.layout.activity_splash_layout, null);
        settings = new MicroRecruitSettings(mContext);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (settings.isFirstLogin.getValue()) {
                    // 第一次登录
                    mHandler.sendEmptyMessage(TOLOGIN);
                } else {
                    // 不是第一次登录
                    //这里必须要进行登录才行
                    User user = new User();
                    user.setUsername(settings.phone.getValue());
                    user.setPassword(settings.PWD.getValue());
                    user.login(StartActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            mHandler.sendEmptyMessage(TOMAIN);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(StartActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, 2000);
//        setContentView(view);
//        Animation animation = new AnimationUtils().loadAnimation(StartActivity.this, R.anim.alpha);
//        view.setAnimation(animation);
//        animation.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                if (settings.isFirstLogin.getValue()) {
//                    // 第一次登录
//                    mHandler.sendEmptyMessage(TOLOGIN);
//                } else {
//                    // 不是第一次登录
//                    //这里必须要进行登录才行
//                    User user = new User();
//                    user.setUsername(settings.phone.getValue());
//                    user.setPassword(settings.PWD.getValue());
//                    user.login(StartActivity.this, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            mHandler.sendEmptyMessage(TOMAIN);
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            Toast.makeText(StartActivity.this, s, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            }
//        });
    }
}
