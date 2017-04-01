package com.xiangyue.bean;

import com.xiangyue.type.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/9.
 */
public class CircleBean extends BmobObject implements Serializable {

    private String content;
    private String address;
    private String fromroot;
    private String uuid;
    private User username;
    private List<String> photos = new ArrayList<>();


    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public CircleBean() {
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getFromroot() {
        return fromroot;
    }

    public void setFromroot(String fromroot) {
        this.fromroot = fromroot;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
