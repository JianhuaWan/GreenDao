package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/19.
 */
public class coin extends BmobObject {
    private String username;
    private String coincount;
    private String count;//签到天数

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCoincount() {
        return coincount;
    }

    public void setCoincount(String coincount) {
        this.coincount = coincount;
    }
}
