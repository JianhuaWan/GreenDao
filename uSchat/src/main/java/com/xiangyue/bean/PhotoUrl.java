package com.xiangyue.bean;

import com.im.bean.User;

import java.io.Serializable;

/**
 * Created by wWX321637 on 2016/5/17.
 */
public class PhotoUrl implements Serializable {
    private String showurl;
    private String delurl;
    private String objectId;
    private User user;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDelurl() {
        return delurl;
    }

    public void setDelurl(String delurl) {
        this.delurl = delurl;
    }

    public String getShowurl() {
        return showurl;
    }

    public void setShowurl(String showurl) {
        this.showurl = showurl;
    }
}
