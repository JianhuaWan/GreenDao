package com.wanjianhua.budejie.pro.base.model;

import android.content.Context;

import com.wanjianhua.budejie.mvp.model.impl.MvpBaseModel;

/**
 * Created by ying on 2016/6/2.
 */
public abstract class BaseModel extends MvpBaseModel {

    private String serverUrl = "http://api.budejie.com";
    //    //获取视频的接口地址(精华)
//    private String getVideo = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取图片的接口地址(精华)
//    private String getPhoto = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取段子的接口地址(精华)
//    private String getDZ = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取网红的接口地址(精华)
//    private String getWH = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取排行的接口地址(精华)
//    private String getPH = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取社会的接口地址(精华)
//    private String getSH = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取美女的接口地址(精华)
//    private String getGirls = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取冷知识的接口地址(精华)
//    private String getLZS = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
//    //获取游戏的接口地址(精华)
//    private String getGames = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.5.1/0-20.json?";
    private Context mContext;

    public BaseModel(Context context) {
        this.mContext = context;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public Context getContext() {
        return mContext;
    }
}
