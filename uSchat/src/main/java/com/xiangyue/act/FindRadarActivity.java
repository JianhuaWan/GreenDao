package com.xiangyue.act;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.type.User;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class FindRadarActivity extends Activity implements OnClickListener, OnTouchListener {
    private boolean playBeep;
    private MediaPlayer mediaPlayer;
    private static final float BEEP_VOLUME = 0.80f;

    private ImageView radar_btn;
    private TextView xx_tv;

    Animation operatingAnimation;
    private ImageView rotation_img;
    private LinearLayout rotation_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_find_radar_layout);
        radar_btn = (ImageView) findViewById(R.id.radar_btn);
        radar_btn.setOnTouchListener(this);
        xx_tv = (TextView) findViewById(R.id.xx_tv);
        rotation_img = (ImageView) findViewById(R.id.radar_rotation);
        rotation_rl = (LinearLayout) findViewById(R.id.rotation_rl);
        initmplayer();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }


    private long starttime, overtime;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // initmplayer();
                playBeepSound();
                rotation_rl.setVisibility(View.VISIBLE);
                xx_tv.setVisibility(View.GONE);
                radar_rotation();
                starttime = System.currentTimeMillis();
                break;

            case MotionEvent.ACTION_UP:
                StopBeepSound();
                rotation_img.clearAnimation();
                rotation_rl.setVisibility(View.GONE);
                xx_tv.setVisibility(View.VISIBLE);
                playBeep = true;
                overtime = System.currentTimeMillis();

                //随机数
                Random random = new Random();
                int n = random.nextInt(10);
                int mis = new Long(overtime - starttime).intValue();
                if (mis > 2000) {
                    if (n <= 2) {
                        //随机查询一个用户
                        BmobQuery<User> users = new BmobQuery<User>();
                        users.setLimit(20);
                        users.order("-updateAt");
                        users.findObjects(FindRadarActivity.this, new FindListener<User>() {
                                    @Override
                                    public void onSuccess(List<User> list) {
                                        Random random1 = new Random();
                                        int n = random1.nextInt(list.size());
                                        Intent intent = new Intent();
                                        intent.putExtra("userid", list.get(n).getUsername());
                                        intent.putExtra("objectId", list.get(n).getObjectId());
                                        intent.setClass(FindRadarActivity.this, OtherInfoDetailActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onError(int i, String s) {

                                    }
                                }
                        );


                    } else {
                        showShortToast();
                    }
                } else {
                    showShortToast();
                }
                break;
        }

        return true;
    }

    private Toast toast;

    private void showShortToast() {
        Intent intent = new Intent();
        intent.setClass(FindRadarActivity.this, NoFindActivity.class);
        startActivity(intent);
//        View view = LayoutInflater.from(FindRadarActivity.this).inflate(
//                R.layout.include_nofind, null);
//        toast.setView(view);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(Toast.LENGTH_SHORT);
    }

    /**
     * 播放
     */
    private void playBeepSound() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }

    }

    /**
     * 停止
     */
    private void StopBeepSound() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.pause();
        }

    }

    /**
     * 初始化播放
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.radarsound);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onDestroy() {
        // 停止
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        super.onDestroy();
    }

    private void initmplayer() {
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        /** 振铃模式可以是听得见的或振动的。若音量在改变这一模式前是听得见的，则它是听得见的。若打开振动设置，则它是振动的 */
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
    }

    /**
     * 旋转动画
     */
    private void radar_rotation() {
        // 设置图片旋转动画的效果 ，R.anim.tip为动画文件。
        operatingAnimation = AnimationUtils.loadAnimation(FindRadarActivity.this, R.anim.radar_rotation);
        LinearInterpolator lin = new LinearInterpolator(); // 设置旋转速度 此处设置为匀速旋转
        operatingAnimation.setInterpolator(lin);// 将旋转速度配置给动画。
        rotation_img.startAnimation(operatingAnimation);

    }

}
