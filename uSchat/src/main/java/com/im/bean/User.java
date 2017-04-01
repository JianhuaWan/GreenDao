package com.im.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author :smile
 * @project:User
 * @date :2016-01-22-18:11
 */
public class User extends BmobUser implements Serializable {

    private String avatar;
    private BmobFile headImage;
    private String nickName;
    private String sex;
    private String age;
    private String convertlist;

    public User() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BmobFile getHeadImage() {
        return headImage;
    }

    public void setHeadImage(BmobFile headImage) {
        this.headImage = headImage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getConvertlist() {
        return convertlist;
    }

    public void setConvertlist(String convertlist) {
        this.convertlist = convertlist;
    }
}
