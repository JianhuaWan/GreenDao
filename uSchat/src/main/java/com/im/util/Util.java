package com.im.util;

/**
 * @author smile
 * @project Util
 * @date 2016-03-01-14:55
 */
public class Util {
    public static boolean checkSdCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
}
