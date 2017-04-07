package com.glgjing.recorder;

import android.app.Activity;
import android.graphics.Rect;

/**
 * --------------------------------------------------
 * 版       权 ：易成勇
 * <p>
 * 作       者： 易成勇
 * <p>
 * 文件名：SizeUtil
 * <p>
 * 创 建 日 期 ： 2016/12/6 0006  22:28
 * <p>
 * 描      述 ：
 * <p>
 * <p>
 * 修 订 历 史:
 * <p>
 * --------------------------------------------------
 */

public class SizeUtil {
    public static int getNavigationBarHeight(Activity activity) {
            int statusHeight = 0;
            Rect localRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
            statusHeight = localRect.top;
            if (0 == statusHeight){
                Class<?> localClass;
                try {
                    localClass = Class.forName("com.android.internal.R$dimen");
                    Object localObject = localClass.newInstance();
                    int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                    statusHeight = activity.getResources().getDimensionPixelSize(i5);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            return statusHeight;
        }
}
