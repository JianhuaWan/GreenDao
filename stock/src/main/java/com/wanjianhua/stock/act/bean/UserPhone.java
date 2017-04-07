package com.wanjianhua.stock.act.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by wanjianhua on 2017/4/7.
 */

public class UserPhone extends BmobObject implements Serializable {
    private String phone;
    private String pwd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
