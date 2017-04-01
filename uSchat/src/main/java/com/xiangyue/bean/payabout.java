package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/6.
 */
public class payabout extends BmobObject {
    private String username;
    private String paymoney;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(String paymoney) {
        this.paymoney = paymoney;
    }
}
