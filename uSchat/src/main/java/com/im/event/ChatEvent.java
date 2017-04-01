package com.im.event;


import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * @author :smile
 * @project:ChatEvent
 * @date :2016-01-25-10:42
 */
public class ChatEvent {

    public BmobIMUserInfo info;

    public ChatEvent(BmobIMUserInfo info) {
        this.info = info;
    }
}
