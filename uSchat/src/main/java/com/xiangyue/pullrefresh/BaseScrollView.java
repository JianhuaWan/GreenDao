package com.xiangyue.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class BaseScrollView extends GridView {

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public BaseScrollView(Context context) {
        super(context);
    }

    public BaseScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}