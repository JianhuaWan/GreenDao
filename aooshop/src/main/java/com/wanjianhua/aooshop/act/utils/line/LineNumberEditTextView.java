package com.wanjianhua.aooshop.act.utils.line;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;


public class LineNumberEditTextView extends LinearLayout
{

    private View mRootView;

    public LineNumberEditTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
        init(attrs);
    }

    public LineNumberEditTextView(Context context)
    {
        this(context, null);
    }

    private void init(AttributeSet attrs)
    {
        if (attrs != null)
        {
            TypedArray array = null;
            try
            {
                array = getContext().obtainStyledAttributes(attrs, R.styleable.LineTextView);
                CharSequence leftText = array.getText(R.styleable.LineTextView_lefttext);
                if (leftText != null)
                    setLeftText(leftText);
                CharSequence text = array.getText(R.styleable.LineTextView_righttext);
                if (text != null)
                    setRightHintText(text);
            }
            finally
            {
                if (array != null)
                {
                    array.recycle();
                }
            }
        }
    }

    private void init(Context context)
    {
        mRootView = LayoutInflater.from(context).inflate(R.layout.line_edit_text_view, null);
        addView(mRootView);
    }

    public void setLeftText(int id)
    {
        TextView leftTv = (TextView) findViewByRootID(R.id.tv1);
        leftTv.setText(id);
    }

    public void setLeftText(CharSequence id)
    {
        TextView leftTv = (TextView) findViewByRootID(R.id.tv1);
        leftTv.setText(id);
    }

    public void setRigthtText(int id)
    {
        EditText rightTv = (EditText) findViewById(R.id.tv2);
        rightTv.setText(id);
    }

    public void setRightText(CharSequence id)
    {
        EditText rightTv = (EditText) findViewById(R.id.tv2);
        rightTv.setText(id);
    }

    public void setRightHintText(CharSequence text)
    {
        EditText rightTv = (EditText) findViewById(R.id.tv2);
        rightTv.setHint(text);
    }

    public CharSequence getRightText()
    {
        EditText rightTv = (EditText) findViewById(R.id.tv2);
        return rightTv.getText();
    }

    public View findViewByRootID(int viewID)
    {
        return mRootView.findViewById(viewID);
    }

}
