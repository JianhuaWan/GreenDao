package com.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;

import com.xiangyue.video.VideoPlayActivity;

import java.io.File;

import cn.bmob.newim.bean.BmobIMVideoMessage;
import cn.bmob.newim.core.BmobDownloadManager;
import cn.bmob.v3.BmobUser;

public class NewVideoPlayClickListener implements View.OnClickListener {

    BmobIMVideoMessage message;
    ImageView iv_voice;
    private AnimationDrawable anim = null;
    Context context;
    String currentObjectId = "";
    MediaPlayer mediaPlayer = null;
    public static boolean isPlaying = false;
    public static NewVideoPlayClickListener currentPlayListener = null;
    static BmobIMVideoMessage currentMsg = null;

    public NewVideoPlayClickListener(Context context, BmobIMVideoMessage msg, ImageView voice) {
        this.iv_voice = voice;
        this.message = msg;
        this.context = context;
        currentMsg = msg;
        currentPlayListener = this;
        try {
            currentObjectId = BmobUser.getCurrentUser(context).getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public void startPlayRecord(String filePath, boolean isUseSpeaker) {
        if (!(new File(filePath).exists())) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("videoPath", filePath);
        intent.setClass(context, VideoPlayActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onClick(View arg0) {
        if (message.getFromId().equals(currentObjectId)) {// 如果是自己发送的视频消息，则播放本地地址
            String localPath = message.getContent().split("&")[0];
            startPlayRecord(localPath, true);
        } else {// 如果是收到的消息，则需要先下载后播放
            String localPath = BmobDownloadManager.getDownLoadFilePath(message);
            startPlayRecord(localPath, true);
        }
    }

}