package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Photos extends BmobObject {
    private String username;// 用户手机号
    private BmobFile photo;// 图片
    private String isillegal;// 是否违法
    private String isbgphoto;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public String getIsillegal() {
        return isillegal;
    }

    public void setIsillegal(String isillegal) {
        this.isillegal = isillegal;
    }

    public String getIsbgphoto() {
        return isbgphoto;
    }

    public void setIsbgphoto(String isbgphoto) {
        this.isbgphoto = isbgphoto;
    }
}
