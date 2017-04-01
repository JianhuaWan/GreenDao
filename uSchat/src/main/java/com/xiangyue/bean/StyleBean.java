package com.xiangyue.bean;

import com.xiangyue.type.User;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class StyleBean extends BmobObject {
    private String name;
    private List<String> choseed;
    private User username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getChoseed() {
        return choseed;
    }

    public void setChoseed(List<String> choseed) {
        this.choseed = choseed;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }
}
