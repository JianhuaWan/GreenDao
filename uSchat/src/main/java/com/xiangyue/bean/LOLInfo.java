package com.xiangyue.bean;

import java.io.Serializable;

/**
 * Created by wanjianhua on 2017/3/31.
 */

public class LOLInfo implements Serializable {
    String time;
    String photo;
    String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
