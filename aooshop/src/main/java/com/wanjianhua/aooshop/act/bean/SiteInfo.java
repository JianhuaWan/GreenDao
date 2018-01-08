package com.wanjianhua.aooshop.act.bean;

import java.io.Serializable;


/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteInfo implements Serializable
{
    private String name;//名称
    private String code;//代码
    private String singleprice;//买入价格
    private String totalprice;//总投入
    private String appreciate;//涨幅
    private String phone;//手机号
    private String balance;//资金配比
    private String objid;//删除用
    private String updateprice;//改变的价格

    public String getUpdateprice()
    {
        return updateprice;
    }

    public void setUpdateprice(String updateprice)
    {
        this.updateprice = updateprice;
    }

    public String getObjid()
    {
        return objid;
    }

    public void setObjid(String objid)
    {
        this.objid = objid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getSingleprice()
    {
        return singleprice;
    }

    public void setSingleprice(String singleprice)
    {
        this.singleprice = singleprice;
    }

    public String getTotalprice()
    {
        return totalprice;
    }

    public void setTotalprice(String totalprice)
    {
        this.totalprice = totalprice;
    }

    public String getAppreciate()
    {
        return appreciate;
    }

    public void setAppreciate(String appreciate)
    {
        this.appreciate = appreciate;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getBalance()
    {
        return balance;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }
}
