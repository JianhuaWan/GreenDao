package com.xiangyue.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.xiangyue.act.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author lWX295154
 * @version 1.0
 * @description 时间对话框。含日期的可滑动控件
 * @date 2016年1月7日下午6:19:42
 */
public class MeDialogUtils {
    private PickerView mFromYear;
    private PickerView mFromMonth;
    private PickerView mFromDay;
    private Button cancelBtn;
    private Button okBtn;

    private TextView yearTv;

    private TextView monthTv;

    private TextView DayTv;
    private int mY;
    private int mM;
    private int mD;
    private Context mContext;
    private List<String> daysFrom;
    private Dialog mDialog;
    private onDataTimeClickListener mClick;

    private int mScrollMonthOfYear;
    private int mScrollDayOfMonth;

    private final int DATE_YEAR_START = 1900;        // 1900年
    private final int DATE_YEAR_END = 2100;            // 2100年
    private final int DATE_MONTH_START = 1;
    private final int DATE_MONTH_END = 12;
    private final int DATE_DAY_START = 1;

    public MeDialogUtils(Context con) {
        mContext = con;
    }

    public MeDialogUtils builder(int yearFrom, int monthFrom, int dayFrom) {
        //布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.me_dialog_date, null);
        //保存日期初始值
        mY = yearFrom;
        mM = mScrollMonthOfYear = monthFrom;
        mD = mScrollDayOfMonth = dayFrom;
        //3个选择器对应一组日期。布局中有两组
        mFromYear = (PickerView) view.findViewById(R.id.me_dialog_year);
        mFromMonth = (PickerView) view.findViewById(R.id.me_dialog_month);
        mFromDay = (PickerView) view.findViewById(R.id.me_dialog_day);
        yearTv = (TextView) view.findViewById(R.id.me_dialog_year_tv);
        monthTv = (TextView) view.findViewById(R.id.me_dialog_month_tv);
        DayTv = (TextView) view.findViewById(R.id.me_dialog_day_tv);
        //得到当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        String[] datList = date.split("-");
        yearTv.setText(datList[0] + "年");
        monthTv.setText(datList[1] + "月");
        DayTv.setText(datList[2] + "日");
        //按钮和监听
        cancelBtn = (Button) view.findViewById(R.id.me_dialog_cacenl_btn);
        okBtn = (Button) view.findViewById(R.id.me_dialog_ok_btn);
        okBtn.setOnClickListener(posListener);

        //年选择器列表
        List<String> datas = new ArrayList<String>();
        for (int i = DATE_YEAR_START; i <= DATE_YEAR_END; i++) {
            datas.add("" + i + "年");
        }

        //月选择器列表
        List<String> months = new ArrayList<String>();
        for (int i = DATE_DAY_START; i <= DATE_MONTH_END; i++) {
            months.add("" + i + "月");
        }

        //日选择器列表（更随年月变化）
        daysFrom = setDayList(yearFrom, monthFrom);

        mFromYear.setData(datas);//对选择器设置列表
        mFromMonth.setData(months);
        mFromDay.setData(daysFrom);

        mFromYear.setSelected(yearFrom - DATE_YEAR_START);//对选择器设置默认选择数2015-1990 =
        mFromMonth.setSelected(monthFrom - DATE_MONTH_START);
        mFromDay.setSelected(dayFrom - DATE_DAY_START);

        mFromYear.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {

            }

            @Override
            public void onMoveHeadToTail(String text) {
                int length = text.length();
                mY = Integer.parseInt(text.substring(0, length - 1));
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, mY);
                yearTv.setText(calendar.get(Calendar.YEAR) + "年");
                refreshFromDayPickerView();
            }

            @Override
            public void onMoveTailToHead(String text) {
                int length = text.length();
                mY = Integer.parseInt(text.substring(0, length - 1));
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, mY);
                yearTv.setText(calendar.get(Calendar.YEAR) + "年");
                refreshFromDayPickerView();
            }
        });
        mFromMonth.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {

            }

            @Override
            public void onMoveHeadToTail(String text) {
                int length = text.length();
                int currentScrollMonth = Integer.parseInt(text.substring(0, length - 1));
                if (mScrollMonthOfYear > currentScrollMonth) {
                    /**
                     * 成员变量的月赋值为当前滑动到的月
                     */
                    mM = mScrollMonthOfYear = currentScrollMonth;
                    /**
                     * 显示年的变化到年控件中
                     */
                    mY++;
                    if (mY > DATE_YEAR_END) {
                        mY = DATE_YEAR_START;
                    }
                    mFromYear.setSelected(mY - DATE_YEAR_START);
                } else {
                    mM = mScrollMonthOfYear = currentScrollMonth;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, mM);
                monthTv.setText(calendar.get(Calendar.MONTH) + "月");
                refreshFromDayPickerView();
                refreshFromDayPickerView();
            }

            @Override
            public void onMoveTailToHead(String text) {
                int length = text.length();
                int currentScrollMonth = Integer.parseInt(text.substring(0, length - 1));
                if (mScrollMonthOfYear < currentScrollMonth) {
                    /**
                     * 成员变量的月赋值为当前滑动到的月
                     */
                    mM = mScrollMonthOfYear = currentScrollMonth;

                    /**
                     * 显示年的变化到年控件中
                     */
                    mY--;
                    if (mY < DATE_YEAR_START) {
                        mY = DATE_YEAR_END;
                    }
                    mFromYear.setSelected(mY - DATE_YEAR_START);
                } else {
                    mM = mScrollMonthOfYear = currentScrollMonth;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, mM);
                monthTv.setText(calendar.get(Calendar.MONTH) + "月");
                refreshFromDayPickerView();
            }
        });

        mFromDay.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
            }

            @Override
            public void onMoveHeadToTail(String text) {
                int length = text.length();
                int currentScrollDay = Integer.parseInt(text.substring(0, length - 1));
                if (mScrollDayOfMonth > currentScrollDay) {
                    /**
                     * 成员变量的日赋值为当前滑动到的日
                     */
                    mD = mScrollDayOfMonth = currentScrollDay;
                    mM++;
                    if (mM > DATE_MONTH_END) {
                        mM = DATE_MONTH_START;
                    }
                    mFromMonth.setSelected(mM - DATE_MONTH_START);

                    /**
                     * 记录当前月份滚动到的值mScrollMonthOfYear
                     */
                    if (mScrollMonthOfYear > mM) {
                        mScrollMonthOfYear = mM;
                        /**
                         * 显示年的变化到年控件中
                         */
                        mY++;
                        if (mY > DATE_YEAR_END) {
                            mY = DATE_YEAR_START;
                        }
                        mFromYear.setSelected(mY - DATE_YEAR_START);
                    } else {
                        mScrollMonthOfYear = mM;
                    }

                } else {
                    mD = mScrollDayOfMonth = currentScrollDay;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DATE, mD);
                DayTv.setText(calendar.get(Calendar.DATE) + "日");
                refreshFromDayPickerView();
            }

            @Override
            public void onMoveTailToHead(String text) {
                int length = text.length();
                int currentScrollDay = Integer.parseInt(text.substring(0, length - 1));
                if (mScrollDayOfMonth < currentScrollDay) {

                    /**
                     * 成员变量的日赋值为当前滑动到的日
                     */
                    mD = mScrollDayOfMonth = currentScrollDay;

                    /**
                     * 显示月的变化到月控件中
                     */
                    mM--;
                    if (mM < DATE_MONTH_START) {
                        mM = DATE_MONTH_END;
                    }
                    mFromMonth.setSelected(mM - DATE_MONTH_START);

                    /**
                     * 记录当前月份滚动到的值mScrollMonthOfYear
                     */
                    if (mScrollMonthOfYear < mM) {
                        mScrollMonthOfYear = mM;
                        /**
                         * 显示年的变化到年控件中
                         */
                        mY--;
                        if (mY < DATE_YEAR_START) {
                            mY = DATE_YEAR_END;
                        }
                        mFromYear.setSelected(mY - DATE_YEAR_START);
                    } else {
                        mScrollMonthOfYear = mM;
                    }

                } else {
                    mD = mScrollDayOfMonth = currentScrollDay;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DATE, mM);
                System.out.println("day===" + calendar.get(Calendar.DATE));
                DayTv.setText(calendar.get(Calendar.DATE) + "日");
                refreshFromDayPickerView();
            }
        });
        mDialog = new Dialog(mContext, R.style.no_bg_frame_translucent_dialog);
        mDialog.setContentView(view);
        return this;
    }

    /**
     * 根据最新的年、月，更新mDays集合，显示当前的日到日控件中
     */
    private void refreshFromDayPickerView() {
        daysFrom = setDayList(mY, mM);
        // 若当前的日，大于新选择的年、月所能有的日的最大值，则把该最大值设置为当前的日
        if (mD > daysFrom.size()) {
            mD = daysFrom.size();
        }
        mFromDay.setData(daysFrom);
        mFromDay.setSelected(mD - DATE_DAY_START);
    }


    private List<String> setDayList(int y, int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m - 1);
        calendar.set(Calendar.DATE, DATE_DAY_START);
        int DayCounts = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<String> days = new ArrayList<String>();
        for (int i = 1; i <= DayCounts; i++) {
            days.add(i + "" + "日");
        }
        return days;
    }

    public MeDialogUtils setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public MeDialogUtils setOnCancelListener(OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
        return this;
    }

    public MeDialogUtils setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public MeDialogUtils setOk(onDataTimeClickListener mc) {
        this.mClick = mc;
        return this;
    }

    public MeDialogUtils setCancel(final OnClickListener listener) {
        cancelBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }

        });
        return this;
    }

    OnClickListener posListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Calendar cal = Calendar.getInstance();//获取当前时间
            cal.set(mY, mM, mD);
            mClick.onClick(v, mY, mM, mD);
            dismiss();
        }
    };

    public void dismiss() {
        mDialog.dismiss();
    }

    public MeDialogUtils show() {
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (screenWidth * 0.9);
        dialogWindow.setAttributes(lp);

        mDialog.show();
        return this;
    }

    public interface onDataTimeClickListener {
        void onClick(View v, int mY, int mM, int mD);
    }
}
