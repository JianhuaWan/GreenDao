package com.wanjianhua.aooshop.act.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextTypefaceUtiles {

    public static Typeface fONT_FZLTHK = Typeface.DEFAULT;

    private static void initializeFonts(Context context) {
        if (fONT_FZLTHK == Typeface.DEFAULT) {
            fONT_FZLTHK = Typeface.createFromAsset(context.getAssets(), "fonts/FZLTHK.TTF");
        }
    }

    public static void overrideFonts(final View v) {
        try {
            //初始化字体
            initializeFonts(v.getContext());

            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child);
                }
            } else if (v instanceof TextView) {
                Typeface tempTypeface = ((TextView) v).getTypeface();
                if (!fONT_FZLTHK.equals(tempTypeface)) {
                    ((TextView) v).setTypeface(fONT_FZLTHK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }
}
