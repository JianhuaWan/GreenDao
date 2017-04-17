package com.example.rxjavamvpretrofitdagger2.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.rxjavamvpretrofitdagger2.bean.UserBean;
import com.example.rxjavamvpretrofitdagger2.model.IUserModel;
import com.example.rxjavamvpretrofitdagger2.model.IUserModelimpl;
import com.example.rxjavamvpretrofitdagger2.view.IUserView;
import com.example.rxjavamvpretrofitdagger2.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjianhua on 2017/4/17.
 */

public class UserPresenter {
    private IUserView mUserView;
    private IUserModel mUserModel;


    public UserPresenter(IUserView mUserView) {
        this.mUserView = mUserView;
        this.mUserModel = new IUserModelimpl();
    }

    public void saveUser(int id, String username, String pwd) {
        mUserModel.setId(id);
        mUserModel.setUserName(username);
        mUserModel.setPwd(pwd);
        mUserModel.save(id, username, pwd);
    }

    public void loadUser(int id) {
        UserBean bean = mUserModel.load(id);
        mUserView.setUserName(bean.getName());//通过调用IUserView的方法来更新显示
        mUserView.setPwd(bean.getPwd());
    }

    public void loading(Context context) {
        final ProgressDialog dialog = ProgressDialog.show(context, "登录中...", "正在登录...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    dialog.cancel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
