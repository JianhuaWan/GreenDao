/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiangyue.util;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class Utils {

    private static final String TAG = "BitmapCommonUtils";
    private static final long POLY64REV = 0x95AC9329AC4BC9B5L;
    private static final long INITIALCRC = 0xFFFFFFFFFFFFFFFFL;

    private static long[] sCrcTable = new long[256];

    /**
     * 获取可以使用的缓存目录
     * 
     * @param context
     * @param uniqueName
     *            目录名称
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? getExternalCacheDir(
                context).getPath()
                : context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取bitmap的字节大小
     * 
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 获取Drawable的字节大小
     * 
     * @param bitmap
     * @return
     */
    public static int getDrawableSize(Drawable bitmap) {
        return bitmap.getIntrinsicWidth() * bitmap.getIntrinsicHeight() * 4;
    }

    /**
     * 创建StateListDrawablw对象。
     *
     * @param states
     * @param drawables
     * @return StateListDrawable 返回类型
     */
    public static StateListDrawable addStateDrawable(int[] states, Drawable[] drawables) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normalDrawable = null;
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        for (int i = 0; i < states.length; i++) {
            if (states[i] != -1) {
                sd.addState(new int[] { android.R.attr.state_enabled, states[i] }, drawables[i]);
                sd.addState(new int[] { states[i] }, drawables[i]);
            } else {
                normalDrawable = drawables[i];
            }
        }
        sd.addState(new int[] { android.R.attr.state_enabled }, normalDrawable);
        sd.addState(new int[] {}, normalDrawable);
        return sd;
    }

    /**
     * 创建StateListDrawablw对象。
     *
     * @param states
     * @param drawables
     * @return StateListDrawable 返回类型
     */
    public static StateListDrawable addStateDrawable(Resources res, int[] states, Bitmap[] bitmaps) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normalDrawable = null;
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        for (int i = 0; i < states.length; i++) {
            Drawable drawable = createDrawable(res, bitmaps[i]);
            if (states[i] != -1) {
                sd.addState(new int[] { android.R.attr.state_enabled, states[i] }, drawable);
                sd.addState(new int[] { states[i] }, drawable);
            } else {
                normalDrawable = drawable;
            }
        }
        sd.addState(new int[] { android.R.attr.state_enabled }, normalDrawable);
        sd.addState(new int[] {}, normalDrawable);
        return sd;
    }

    public static Drawable createDrawable(Resources res, Bitmap bitmap) {
        if (bitmap == null) {
            return new ColorDrawable(android.R.color.transparent);
        }
        byte[] chunk = bitmap.getNinePatchChunk();
        boolean result = NinePatch.isNinePatchChunk(chunk);
        Drawable drawable = null;
        if (result) {
            drawable = new NinePatchDrawable(res, bitmap, chunk, new Rect(), null);
        } else {
            drawable = new BitmapDrawable(res, bitmap);
        }
        return drawable;
    }

    public static Drawable[] createDrawablesByImages(Resources res, Bitmap[] bitmaps) {
        Drawable[] drawables = new Drawable[bitmaps.length];
        for (int i = 0; i < bitmaps.length; i++) {
            byte[] chunk = bitmaps[i].getNinePatchChunk();
            boolean result = NinePatch.isNinePatchChunk(chunk);
            if (result) {
                drawables[i] = new NinePatchDrawable(res, bitmaps[i], chunk, new Rect(), null);
            } else {
                drawables[i] = new BitmapDrawable(res, bitmaps[i]);
            }
        }
        return drawables;
    }

    /**
     * 获取程序外部的缓存目录
     * 
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * 获取文件路径空间大小
     * 
     * @param path
     * @return
     */
    public static long getUsableSpace(File path) {
        try {
            final StatFs stats = new StatFs(path.getPath());
            return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
        } catch (Exception e) {
            Log.e(TAG, "获取 sdcard 缓存大小 出错，请查看AndroidManifest.xml 是否添加了sdcard的访问权限");
            e.printStackTrace();
            return -1;
        }

    }

    public static byte[] getBytes(String in) {
        byte[] result = new byte[in.length() * 2];
        int output = 0;
        for (char ch : in.toCharArray()) {
            result[output++] = (byte) (ch & 0xFF);
            result[output++] = (byte) (ch >> 8);
        }
        return result;
    }

    public static boolean isSameKey(byte[] key, byte[] buffer) {
        int n = key.length;
        if (buffer.length < n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (key[i] != buffer[i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        byte[] copy = new byte[newLength];
        System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
        return copy;
    }

    static {
        // 参考 http://bioinf.cs.ucl.ac.uk/downloads/crc64/crc64.c
        long part;
        for (int i = 0; i < 256; i++) {
            part = i;
            for (int j = 0; j < 8; j++) {
                long x = ((int) part & 1) != 0 ? POLY64REV : 0;
                part = (part >> 1) ^ x;
            }
            sCrcTable[i] = part;
        }
    }

    public static byte[] makeKey(String httpUrl) {
        return getBytes(httpUrl);
    }

    /**
     * A function thats returns a 64-bit crc for string
     *
     * @param in
     *            input string
     * @return a 64-bit crc value
     */
    public static final long crc64Long(String in) {
        if (in == null || in.length() == 0) {
            return 0;
        }
        return crc64Long(getBytes(in));
    }

    public static final long crc64Long(byte[] buffer) {
        long crc = INITIALCRC;
        for (int k = 0, n = buffer.length; k < n; ++k) {
            crc = sCrcTable[(((int) crc) ^ buffer[k]) & 0xff] ^ (crc >> 8);
        }
        return crc;
    }

}
