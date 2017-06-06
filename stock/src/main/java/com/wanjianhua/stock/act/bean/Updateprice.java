package com.wanjianhua.stock.act.bean;

import java.io.Serializable;

/**
 * author：wanjianhua on 2017/6/4 13:57
 * email：1243381493@qq.com
 */

public class Updateprice implements Serializable {
    private String i;//第几梯队
    private String j;//第几波段
    private String upprice;//改动后的价格;

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getJ() {
        return j;
    }

    public void setJ(String j) {
        this.j = j;
    }

    public String getUpprice() {
        return upprice;
    }

    public void setUpprice(String upprice) {
        this.upprice = upprice;
    }
}
