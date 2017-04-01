package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/6.
 */
public class alipayattest extends BmobObject {
    private String state;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
