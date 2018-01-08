package com.wanjianhua.aooshop.act.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MicroRecruitSettings extends AppSettings {

    private static final String SHARED_PREFERENCES_NAME = "stock.settings";

    private final SharedPreferences mGlobalPreferences;

    public MicroRecruitSettings(Context context) {
        mGlobalPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
    }

    @Override
    public SharedPreferences getGlobalPreferences() {
        return mGlobalPreferences;
    }

    // 手机号
    public StringPreference PHONE = new StringPreference("phone", "");//手机号


}
