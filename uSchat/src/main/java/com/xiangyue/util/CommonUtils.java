/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiangyue.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

public class CommonUtils {

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 点击间隔 假设为0.5s
     */
    private final static int MIN_CIICK_TIME = 500;

    /**
     * 最后点击时间
     */
    private static long lastClickTime = 0;

    /**
     * 防止暴力点击 检查是否为快速点击
     */
    public static boolean isFastClick() {

        long timeInMillis = System.currentTimeMillis();
        long timeC = timeInMillis - lastClickTime;
        if (timeC > 0 && timeC < MIN_CIICK_TIME) {
            return true;
        }
        lastClickTime = timeInMillis;
        return false;
    }

    /**
     * 将dip转换为pix
     **/
    public static int getDip(int size, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                size, context.getResources().getDisplayMetrics());
    }

}
