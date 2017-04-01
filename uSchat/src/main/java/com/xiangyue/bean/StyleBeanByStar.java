package com.xiangyue.bean;

import com.xiangyue.type.User;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class StyleBeanByStar {
    private String name;
    private boolean ischeck;

    public StyleBeanByStar(String name, boolean ischeck) {
        this.name = name;
        this.ischeck = ischeck;
    }

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
