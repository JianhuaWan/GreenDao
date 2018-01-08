package com.wanjianhua.aooshop.act.bean;

import java.io.Serializable;


/**
 * Created by wanjianhua on 2017/4/7.
 */

public class UserPhone implements Serializable
{
    private String phone;
    private String pwd;

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
}
