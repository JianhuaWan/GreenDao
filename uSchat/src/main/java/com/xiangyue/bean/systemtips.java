package com.xiangyue.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by wWX321637 on 2016/5/6.
 */
public class systemtips extends BmobObject {
    private String content;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
