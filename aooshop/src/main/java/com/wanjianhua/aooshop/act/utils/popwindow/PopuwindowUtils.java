package com.wanjianhua.aooshop.act.utils.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.act.BuyShopActivity;
import com.wanjianhua.aooshop.act.utils.AmountView;

/**
 * Created by 6005001713 on 2017/6/14.
 */

public class PopuwindowUtils
{
    private PopupWindowView popupWindowView;
    private Context mcontext;
    private Activity activity;


    public PopuwindowUtils(Context context, View view, Activity activity)
    {
        this.activity = activity;
        this.mcontext = context;
        popupWindowView = new PopupWindowView(context);
        popupWindowView.show(view);
    }

    private AmountView mAmountView;
    private TextView tv_next;

    // TODO 更多菜单
    private class PopupWindowView extends PopupWindow
    {
        private PopupWindow popupWindow = null;

        public PopupWindowView(final Context context)
        {
            View contentView = LayoutInflater.from(context).inflate(
                    R.layout.pop_main, null);
            popupWindow = new PopupWindow(contentView,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
//            contentView.setFocusableInTouchMode(true);
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            contentView.setOnTouchListener(new View.OnTouchListener()
            {

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    closePop();
                    return true;
                }
            });
            contentView.setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if(event.getAction() == KeyEvent.ACTION_DOWN &&
                            keyCode == KeyEvent.KEYCODE_BACK)
                    {
                    }
                    return true;
                }
            });
            tv_next = (TextView) contentView.findViewById(R.id.tv_next);
            tv_next.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent();
                    intent.setClass(context, BuyShopActivity.class);
                    context.startActivity(intent);
                }
            });
            mAmountView = (AmountView) contentView.findViewById(R.id.amount_view);
            mAmountView.setGoods_storage(50);
            mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener()
            {
                @Override
                public void onAmountChange(View view, int amount)
                {
                }
            });
            contentView.findViewById(R.id.btn_pop_task_detail_cancel).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    closePop();
                }
            });

        }

        public void show(View v)
        {
            if(popupWindow != null && !popupWindow.isShowing())
            {
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
        }

        /**
         * 关闭pop
         */
        public void closePop()
        {
            if(popupWindow != null && popupWindow.isShowing())
            {
                popupWindow.dismiss();
            }
        }
    }

    private void closepop()
    {
        popupWindowView.closePop();
    }
}
