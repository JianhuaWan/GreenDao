package com.wanjianhua.mockpanda;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.VideoView;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class MainActivity extends AppCompatActivity {
    private VideoView diveo_view;
    private boolean showDanmaku;
    private DanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private LinearLayout operation_layout;
    private EditText edit_text;
    private Button send;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

//    private void checkPremission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//        //android 6.0动态申请权限
//            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            }
//        }
//    }

    private void initView() {
        operation_layout = (LinearLayout) findViewById(R.id.operation_layout);
        edit_text = (EditText) findViewById(R.id.edit_text);
        send = (Button) findViewById(R.id.send);
        diveo_view = (VideoView) findViewById(R.id.diveo_view);
        diveo_view.setVideoPath(Environment.getExternalStorageDirectory() + "/lol.mp4");
        diveo_view.start();
        danmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
        danmakuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation_layout.getVisibility() == View.GONE) {
                    operation_layout.setVisibility(View.VISIBLE);
                } else if (operation_layout.getVisibility() == View.VISIBLE) {
                    operation_layout.setVisibility(View.GONE);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送消息
                addDanmaku(edit_text.getText().toString(), true);
            }
        });
    }

    private void addDanmaku(String content, boolean withborder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withborder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随即生成一些内容测试
     *
     * @param
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
                    addDanmaku(content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public int sp2px(float value) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (value * fontScale + 0.5f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }

    @Override

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
