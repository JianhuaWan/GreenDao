package com.example.rxjavamvpretrofitdagger2.bus;

/**
 * Created by wanjianhua on 2017/4/17.
 */

public class RefreshMessage {
    private String name;
    private String id;

    public RefreshMessage(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
