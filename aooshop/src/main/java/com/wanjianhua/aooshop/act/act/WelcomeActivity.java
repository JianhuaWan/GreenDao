package com.wanjianhua.aooshop.act.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wanjianhua.aooshop.act.base.BaseActivity;
import com.wanjianhua.aooshop.R;

import java.lang.ref.WeakReference;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class WelcomeActivity extends BaseActivity
{
    private static final int arg = 10086;
    private Context context;
    Intent intent = new Intent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);
        context = this;
        Myhandler myhandler = new Myhandler();
        Message message = new Message();
        message.arg1 = arg;
        myhandler.sendMessageDelayed(message, 2000);

    }

    class Myhandler extends Handler
    {
        WeakReference<WelcomeActivity> activity;

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch(msg.arg1)
            {
                case arg:
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    context.startActivity(WelcomeActivity.this.intent);
                    finish();
                    break;
            }
        }
    }

}
