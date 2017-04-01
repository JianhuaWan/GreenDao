package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by wWX321637 on 2016/6/14.
 */
public class updateversion extends BmobObject {
    private String versionname;//版本名称
    private String versioncode;//版本code
    private String updateinfo;//更新说明
    private BmobFile apk;//apk

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getUpdateinfo() {
        return updateinfo;
    }

    public void setUpdateinfo(String updateinfo) {
        this.updateinfo = updateinfo;
    }

    public BmobFile getApk() {
        return apk;
    }

    public void setApk(BmobFile apk) {
        this.apk = apk;
    }
}
