package com.example.rxjavamvpretrofitdagger2.model;

import com.example.rxjavamvpretrofitdagger2.bean.UserBean;

import java.util.List;

/**
 * Created by wanjianhua on 2017/4/17.
 * （处理业务逻辑，这里指数据读写），先写接口，后写实现
 */

public interface IUserModel {
    void setUserName(String username);

    void setPwd(String pwd);

    int getID();

    void setId(int id);

    UserBean load(int id);//通过id读取user信息,返回一个UserBean

    List<UserBean> save(int id, String username, String pwd);//通过id保存user信息,返回一个UserBean

}
