package com.im.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author :smile
 * @project:ViewUtil
 * @date :2016-01-25-9:57
 */
public class ViewUtil {

    /**
     * 设置头像（String类型）
     *
     * @param avatar
     * @param defaultRes
     * @param iv
     */
    public static void setAvatar(Context context, String avatar, int defaultRes, ImageView iv) {
        if (!TextUtils.isEmpty(avatar)) {
            if (!avatar.equals(iv.getTag())) {//增加tag标记，减少UIL的display次数
                iv.setTag(avatar);
                //不直接display imageview改为ImageAware，解决ListView滚动时重复加载图片
                ImageAware imageAware = new ImageViewAware(iv, false);
                ImageLoader.getInstance().displayImage(avatar, iv, DisplayConfig.getDefaultOptions(true, defaultRes));
//                Picasso.with(context).load(avatar).placeholder(R.drawable.head).error(R.drawable.head).into(iv);
            }
        } else {
            iv.setImageResource(defaultRes);
        }
    }

    /**
     * 显示图片
     *
     * @param url
     * @param defaultRes
     * @param iv
     * @param listener
     */
    public static void setPicture(Context context, String url, int defaultRes, ImageView iv, ImageLoadingListener listener) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.equals(iv.getTag())) {//增加tag标记，减少UIL的display次数
                iv.setTag(url);
                //不直接display imageview改为ImageAware，解决ListView滚动时重复加载图片
                ImageAware imageAware = new ImageViewAware(iv, false);
                if (listener != null) {
                    ImageLoader.getInstance().displayImage(url, iv, DisplayConfig.getDefaultOptions(false, defaultRes), listener);
//                    Picasso.with(context).load(url).placeholder(R.drawable.head).error(R.drawable.head).into(iv);
                } else {
                    ImageLoader.getInstance().displayImage(url, iv, DisplayConfig.getDefaultOptions(false, defaultRes));
                }
            }
        } else {
            iv.setImageResource(defaultRes);
        }
    }
}
