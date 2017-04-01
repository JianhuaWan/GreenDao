package com.xiangyue.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.xiangyue.act.R;

/**
 */
public class VideoPlayActivity extends Activity implements OnCompletionListener, OnPreparedListener, OnTouchListener {
    private Context mContext;
    private VideoView mVideoView;
    /**
     * 加载中转圈圈
     **/
    private ProgressBar videoLoadPb;

    /**
     * 加载中提示文字
     **/
    private TextView videoLoadTv;
    private String path = null;
    private boolean isComplete = false; // 是否已经播放完成

    private boolean isSocketError = false;
    PlayerSocketServer.IPlayerSocketServer iPlayerSocketServer;
    private boolean needJurgeFormat;
    private MediaController mController;

    private int per = 0;
    private RelativeLayout view_root;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mController = new MediaController(VideoPlayActivity.this);

//        iPlayerSocketServer = new PlayerSocketServer.IPlayerSocketServer() {
//            @Override
//            public void onError() {
//                // TODO Auto-generated method stub
//                isSocketError = true;
//            }
//        };
//        PlayerSocketServer.getSingleTon().setListener(iPlayerSocketServer);
        setContentView(R.layout.activity_videoview);
        path = getIntent().getStringExtra("videoPath");
//        initData();
        initView();
        mVideoView.setVideoPath(path);
        mVideoView.setMediaController(mController);
        mController.setMediaPlayer(mVideoView);
        mVideoView.requestFocus();
        mVideoView.start();
//        startVideoView(path);
    }

    public void back(View view) {
        finish();

    }

    protected void onResume() {
        if (!(0 == per)) {
            mVideoView.seekTo(per);
            mVideoView.start();
        }

        super.onResume();
    }

    ;

    @Override
    protected void onPause() {
        per = mVideoView.getCurrentPosition();

        mVideoView.pause();
        if (!isComplete) {
            // 数据库保存数据
        }
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

            // 当前屏幕为横屏，界面重构，设置视频区域为全屏播放
            getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setVideoFixedSize();
        } else {

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            mVideoView.getHolder().setFixedSize(dm.widthPixels, dm.heightPixels / 3);
        }
    }

    public void setVideoFixedSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mVideoView.getHolder().setFixedSize(dm.widthPixels, dm.heightPixels);
    }

    private void initData() {

        // 调用界面传入的是否要通过HTTP头信息判断视频格式，默认不判断
        needJurgeFormat = getIntent().getBooleanExtra("needJurgeFormat", false);
    }

    private void initView() {
        view_root = (RelativeLayout) findViewById(R.id.view_root);
        view_root.setOnTouchListener(this);
        mVideoView = (VideoView) findViewById(R.id.vdovw_public);
        videoLoadPb = (ProgressBar) findViewById(R.id.video_load_pb);
        videoLoadPb.setVisibility(View.GONE);
        videoLoadTv = (TextView) findViewById(R.id.video_load_tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.stopPlayback();
            PlayerSocketServer.release();
        }
    }

    public void startVideoView(String path) {
        if (null == mVideoView) {
            return;
        }
        // 判断实际文件格式是否是mp4，是才允许播放，否则提示不支持

        videoViewInit();

    }


    OnErrorListener onErrorListener = new OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {

            return true;
        }
    };

    @Override
    public void onPrepared(MediaPlayer mp) {

        videoLoadPb.setVisibility(View.GONE);
        videoLoadTv.setVisibility(View.GONE);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isComplete = true;
        // 数据库保存数据
        mp = null;
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == 226) {

        }
        return super.onKeyDown(keyCode, event);
    }

    private void startErrorDialog(int dialogResId) {

    }

    void videoViewInit() {
        if (Looper.myLooper() == null)
            Looper.prepare();
        isSocketError = false;
        mController.setMediaPlayer(mVideoView);
        mVideoView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        path = getIntent().getStringExtra("uri");
        mVideoView.setMediaController(mController);
        mVideoView.setOnCompletionListener(VideoPlayActivity.this);
        mVideoView.setOnPreparedListener(VideoPlayActivity.this);
        mVideoView.setOnErrorListener(onErrorListener);
        Uri uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mController.isShowing()) {
            mController.hide();
        } else {
            mController.show();
        }
        return false;
    }

}