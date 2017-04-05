package com.wanjianhua.stock.act.bean;

import java.io.Serializable;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteInfo implements Serializable {
    private String name;
    private String code;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
