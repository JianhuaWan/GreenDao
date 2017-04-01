package com.xiangyue.base;

import android.content.Context;
import android.content.SharedPreferences;

public class MicroRecruitSettings extends AppSettings {

    private static final String SHARED_PREFERENCES_NAME = "uschat.settings";

    private final SharedPreferences mGlobalPreferences;

    public MicroRecruitSettings(Context context) {
        mGlobalPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
    }

    @Override
    public SharedPreferences getGlobalPreferences() {
        return mGlobalPreferences;
    }

    // 用户角色信息
//	public StringPreference USER_ROLE = new StringPreference("user_role", "");// 用户角色
    // 是否是第一次登录
    public BooleanPreference isFirstLogin = new BooleanPreference("is_first_login", true);// true为第一次,false为不是第一次
    // 手机号
    public StringPreference phone = new StringPreference("phone", "");//手机号
    // // token
    // public StringPreference SESSION_TOKEN = new StringPreference("token",
    // "");//
    // 头像
    public StringPreference HEADICON = new StringPreference("headimage", "");//头像
    public StringPreference PWD = new StringPreference("pwd", "");//密码
    // objectId
    public StringPreference OBJECT_ID = new StringPreference("object_id", "");//user表objectId
    public StringPreference OBJECT_IDBYCOIN = new StringPreference("objectbycoin_id", "");//辣条表objectId
    public StringPreference OBJECT_IDBYLAUD = new StringPreference("objectbylaud_id", "");//点赞objectId
    public StringPreference OBJECT_IDBYCIRCLEBG = new StringPreference("objectbycirclebg_id", "");//背景图片的objectId
    public StringPreference UPDATE_TIPS = new StringPreference("update_tips", "");//""为提示(默认),1为不提示


}
