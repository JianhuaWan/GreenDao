package com.example.rxandroidbywjh.ui.login;

import android.content.Context;

/**
 * Created by wanjianhua on 2017/3/30.
 */

public class LoginContract {
    /**
     * v视图层
     */
    interface ILoginView {
        Context getCurContext();//获取上下问对象,用于保存数据等

        void showProgress();//显示进度条

        void hideProgress();//隐藏进度条

        void showInfo(String info);//提示用户信息

        void showErrorMsg(String msg);//发生错误提示信息

        void toMain();//跳转主界面

        void toRegister();//跳转到注册页面

        UserInfo getUserLoginInfo();
    }
}
