package com.xiangyue.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/28.
 */
public class CircleassisBean extends BmobObject {
    private String uuid;
    private List<String> peoson;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getPeoson() {
        return peoson;
    }

    public void setPeoson(List<String> peoson) {
        this.peoson = peoson;
    }
}
