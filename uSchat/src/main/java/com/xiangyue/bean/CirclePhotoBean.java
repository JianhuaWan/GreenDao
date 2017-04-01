package com.xiangyue.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/13.
 */
public class CirclePhotoBean extends BmobObject implements Serializable {
    private String uuid;
    private String photo;

    public CirclePhotoBean() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
