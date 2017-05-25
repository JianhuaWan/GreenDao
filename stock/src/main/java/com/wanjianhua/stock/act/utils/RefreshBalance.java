package com.wanjianhua.stock.act.utils;

/**
 * Created by wanjianhua on 2017/4/17.
 */

public class RefreshBalance {
    private String balance1;
    private String balance2;
    private String balance3;

    public String getBalance1() {
        return balance1;
    }

    public void setBalance1(String balance1) {
        this.balance1 = balance1;
    }

    public String getBalance2() {
        return balance2;
    }

    public void setBalance2(String balance2) {
        this.balance2 = balance2;
    }

    public String getBalance3() {
        return balance3;
    }

    public void setBalance3(String balance3) {
        this.balance3 = balance3;
    }

    public RefreshBalance(String balance1, String balance2, String balance3) {
        this.balance1 = balance1;
        this.balance2 = balance2;
        this.balance3 = balance3;
    }

}
