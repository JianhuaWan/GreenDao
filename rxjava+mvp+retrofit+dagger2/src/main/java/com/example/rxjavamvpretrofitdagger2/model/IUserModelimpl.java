package com.example.rxjavamvpretrofitdagger2.model;

import android.util.Log;

import com.example.rxjavamvpretrofitdagger2.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjianhua on 2017/4/17.
 */

public class IUserModelimpl implements IUserModel {
    private List<UserBean> userBeens = new ArrayList<UserBean>();

    @Override
    public void setUserName(String username) {
    }

    @Override
    public void setPwd(String pwd) {

    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public UserBean load(int id) {
        UserBean userBean = null;
        if (userBeens.size() > 0) {
            for (int i = 0; i < userBeens.size(); i++) {
                if (userBeens.get(i).getId() == id) {
                    userBean = new UserBean(userBeens.get(i).getId(), userBeens.get(i).getName(), userBeens.get(i).getPwd());
                }
            }
        } else {
            userBean = new UserBean(-1, "", "");
        }
        return userBean;
    }

    @Override
    public List<UserBean> save(int id, String username, String pwd) {
        UserBean userBean = new UserBean(id, username, pwd);
        userBeens.add(userBean);
        return userBeens;
    }

}
