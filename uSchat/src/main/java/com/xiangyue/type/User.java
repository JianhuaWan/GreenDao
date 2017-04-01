package com.xiangyue.type;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户信息
 *
 * @author Administrator
 */
public class User extends BmobUser {

    // private String username;// 电话;
    private String nickName;// 昵称
    private BmobFile headImage;// 头像
    // private BmobDate birthday;// 生日
    private String sex;// 性别
    private String school;// 学校
    private String discipline;// 院系
    //    private String role;// 角色
    private String age;// 年龄
    private String token;// sessionid;
    private String convertlist;//好友列表
    private String star;//星座
    private String sign;//个性签名
    private String city;//城市记录位置信息
    private String alicode;//支付宝账号
    private String alicodestate;//支付宝状态
    private List<String> stylebq;
    private List<String> attention;//关注的人

    public List<String> getAttention() {
        return attention;
    }

    public void setAttention(List<String> attention) {
        this.attention = attention;
    }

    public List<String> getStylebq() {
        return stylebq;
    }

    public void setStylebq(List<String> stylebq) {
        this.stylebq = stylebq;
    }

    public String getAlicode() {
        return alicode;
    }

    public void setAlicode(String alicode) {
        this.alicode = alicode;
    }

    public String getAlicodestate() {
        return alicodestate;
    }

    public void setAlicodestate(String alicodestate) {
        this.alicodestate = alicodestate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
    // public String getUsername() {
    // return username;
    // }
    //
    // public void setUsername(String username) {
    // this.username = username;
    // }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BmobFile getHeadImage() {
        return headImage;
    }

    public void setHeadImage(BmobFile headImage) {
        this.headImage = headImage;
    }

    // public BmobDate getBirthday() {
    // return birthday;
    // }
    //
    // public void setBirthday(BmobDate birthday) {
    // this.birthday = birthday;
    // }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getConvertlist() {
        return convertlist;
    }

    public void setConvertlist(String convertlist) {
        this.convertlist = convertlist;
    }
}
