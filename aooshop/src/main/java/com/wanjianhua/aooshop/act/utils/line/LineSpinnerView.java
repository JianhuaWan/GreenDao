package com.wanjianhua.aooshop.act.utils.line;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.wanjianhua.aooshop.R;

import java.util.List;

/**
 * <pre>
 * 
 *  xmlns:linetv="http://schemas.android.com/apk/res/com.zte.rs"
 *  
 *  
 *     <com.zte.rs.view.LineTextView
 *         android:id="@+id/ltv_first"
 *         style="@style/line_text_view_line"
 *         android:layout_width="match_parent"
 *         android:layout_height="wrap_content"
 *         linetv:lefttext="@string/my_copo_id" >
 *     </com.zte.rs.view.LineTextView>
 * 
 * 
 * </pre>
 */
@SuppressWarnings("unchecked")
public class LineSpinnerView extends LinearLayout
{

    private View mRootView;

    public LineSpinnerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
        init(attrs);
    }

    public LineSpinnerView(Context context)
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
                // CharSequence text =
                // array.getText(R.styleable.LineTextView_righttext);
                // if (text != null)
                // setRightText(text);
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
        mRootView = LayoutInflater.from(context).inflate(R.layout.line_spinner_view, null);
        mLeftTv = (Spinner) findViewByRootID(R.id.tv2);
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

    public void setLeftTextDrawable()
    {
        ImageView imgImageView=(ImageView) findViewById(R.id.img_line_spinner);
        imgImageView.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        mLeftTv.setEnabled(enabled);
    }
    public void setSpinnerSelected(int position)
    {
        mLeftTv.setSelection(position);
    }

    public Object getSpinnerSelected()
    {
        return mLeftTv.getSelectedItem();
    }

    public Integer getSpinnerPosition(Object item)
    {
        ArrayAdapter adapter = (ArrayAdapter) mLeftTv.getAdapter();
        return adapter.getPosition(item);
    }

    public Integer getSelectedItemPosition()
    {
       return mLeftTv.getSelectedItemPosition();
    }
    
    public void setSpinnerData(List<?> data)
    {
        setSpinnerData(data.toArray());
    }
    
    public void setSelection(int position)
    {
        mLeftTv.setSelection(position);
    }

    public void setSpinnerData(Object[] data)
    {
        ArrayAdapter<Object> commonArrayAdapter = CommonUI.commonArrayAdapter(getContext(), data);
        mLeftTv.setAdapter(commonArrayAdapter);
        mLeftTv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (listener != null)
                {
                    listener.onItemSelected(parent, view, position, id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                if (listener != null)
                {
                    listener.onNothingSelected(parent);
                }
            }
        });
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener)
    {
        this.listener = listener;
    }
    
    public Object getSelectedItem()
    {
       return mLeftTv.getSelectedItem();
    }

    public View findViewByRootID(int viewID)
    {
        return mRootView.findViewById(viewID);
    }

    private AdapterView.OnItemSelectedListener listener;

    private Spinner mLeftTv;
}
