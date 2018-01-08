package com.wanjianhua.aooshop.act.utils;

import android.os.Build;

import java.io.IOException;

/**
 * Created by 6005001713 on 2017/7/21.
 */

public class PhoneUtils
{
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI()
    {
        try
        {
            //如果是小米,或者是7.0的手机都判断下
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null || Build.VERSION.SDK_INT > 23;
        }
        catch(final IOException e)
        {
            return false;
        }
    }
}
