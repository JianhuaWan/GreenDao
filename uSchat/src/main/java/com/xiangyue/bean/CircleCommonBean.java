package com.xiangyue.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/13.
 */
public class CircleCommonBean extends BmobObject implements Serializable {

    private String uuid;
    private String commoncontent;
    private String conmonpeoson;
    private String commonhead;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommoncontent() {
        return commoncontent;
    }

    public void setCommoncontent(String commoncontent) {
        this.commoncontent = commoncontent;
    }

    public String getConmonpeoson() {
        return conmonpeoson;
    }

    public void setConmonpeoson(String conmonpeoson) {
        this.conmonpeoson = conmonpeoson;
    }

    public String getCommonhead() {
        return commonhead;
    }

    public void setCommonhead(String commonhead) {
        this.commonhead = commonhead;
    }
}
