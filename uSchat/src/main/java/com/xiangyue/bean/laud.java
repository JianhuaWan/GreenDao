package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/6.
 */
public class laud extends BmobObject {
    private String username;
    private String laudcont;
    private String stampcont;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLaudcont() {
        return laudcont;
    }

    public void setLaudcont(String laudcont) {
        this.laudcont = laudcont;
    }

    public String getStampcont() {
        return stampcont;
    }

    public void setStampcont(String stampcont) {
        this.stampcont = stampcont;
    }
}
