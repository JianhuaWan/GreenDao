package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by wWX321637 on 2016/5/20.
 */
public class circlrmebgphpto extends BmobObject {
    private BmobFile circlebg;
    private BmobFile mebg;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BmobFile getCirclebg() {
        return circlebg;
    }

    public void setCirclebg(BmobFile circlebg) {
        this.circlebg = circlebg;
    }

    public BmobFile getMebg() {
        return mebg;
    }

    public void setMebg(BmobFile mebg) {
        this.mebg = mebg;
    }
}
