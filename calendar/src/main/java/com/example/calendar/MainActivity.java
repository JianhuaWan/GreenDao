package com.example.calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;
import android.widget.TextView;

import com.example.caledar.util.DateUtil;
import com.example.calendar.doim.CalendarViewBuilder;
import com.example.calendar.doim.CustomDate;
import com.example.calendar.widget.CalendarView;
import com.example.calendar.widget.CalendarView.CallBack;
import com.example.calendar.widget.CalendarViewPagerLisenter;
import com.example.calendar.widget.CircleTextView;
import com.example.calendar.widget.CustomViewPagerAdapter;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity implements OnClickListener, CallBack {

    private ViewPager viewPager;
    private CalendarView[] views;
    private TextView showYearView;
    private TextView showMonthView;
    private TextView showWeekView;
    private TextView monthCalendarView;
    private TextView weekCalendarView;
    private CalendarViewBuilder builder = new CalendarViewBuilder();
    private SlidingDrawer mSlidingDrawer;
    private View mContentPager;
    private CustomDate mClickDate;
    private CircleTextView mNowCircleView;
    private CircleTextView mAddCircleView;
    public static final String MAIN_ACTIVITY_CLICK_DATE = "main_click_date";
    private String temp = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewbyId();
    }

    private void findViewbyId() {
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        showMonthView = (TextView) this.findViewById(R.id.show_month_view);
        showYearView = (TextView) this.findViewById(R.id.show_year_view);
        showWeekView = (TextView) this.findViewById(R.id.show_week_view);
        views = builder.createMassCalendarViews(this, 5, this);
        monthCalendarView = (TextView) this
                .findViewById(R.id.month_calendar_button);
        weekCalendarView = (TextView) this
                .findViewById(R.id.week_calendar_button);
        mContentPager = this.findViewById(R.id.contentPager);
        mSlidingDrawer = (SlidingDrawer) this.findViewById(R.id.sildingDrawer);
        mNowCircleView = (CircleTextView) this
                .findViewById(R.id.now_circle_view);
        mAddCircleView = (CircleTextView) this
                .findViewById(R.id.add_circle_view);
        monthCalendarView.setOnClickListener(this);
        weekCalendarView.setOnClickListener(this);
        mNowCircleView.setOnClickListener(this);
        mAddCircleView.setOnClickListener(this);
        setViewPager();
        setOnDrawListener();
    }

    private void setViewPager() {
        CustomViewPagerAdapter<CalendarView> viewPagerAdapter = new CustomViewPagerAdapter<CalendarView>(
                views);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(498);
        viewPager.setOnPageChangeListener(new CalendarViewPagerLisenter(
                viewPagerAdapter));
    }

    private void setOnDrawListener() {
        mSlidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                builder.swtichCalendarViewsStyle(CalendarView.WEEK_STYLE);
            }
        });
        mSlidingDrawer.setOnDrawerScrollListener(new OnDrawerScrollListener() {

            @Override
            public void onScrollStarted() {
                builder.swtichCalendarViewsStyle(CalendarView.MONTH_STYLE);
            }

            @Override
            public void onScrollEnded() {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setShowDateViewText(int year, int month, int week) {
        showYearView.setText(year + "");
        showMonthView.setText(month + "月");
        showWeekView.setText(DateUtil.weekName[DateUtil.getWeekDay() - 1]);
    }

    public void setShowDateViewText1(int year, int month, int week) {
        showYearView.setText(year + "");
        showMonthView.setText(month + "月");
        showWeekView.setText(DateUtil.weekName[week]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.month_calendar_button:
                swtichBackgroundForButton(true);
                builder.swtichCalendarViewsStyle(CalendarView.MONTH_STYLE);
                mSlidingDrawer.close();
                break;
            case R.id.week_calendar_button:
                swtichBackgroundForButton(false);
                mSlidingDrawer.open();
                break;
            case R.id.now_circle_view:
                builder.backTodayCalendarViews();
                break;
            case R.id.add_circle_view:
                Intent i = new Intent(this, AddPlanActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(MAIN_ACTIVITY_CLICK_DATE, mClickDate);
                i.putExtras(mBundle);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
        }
    }

    private void swtichBackgroundForButton(boolean isMonth) {
        if (isMonth) {
            monthCalendarView
                    .setBackgroundResource(R.drawable.press_left_text_bg);
            weekCalendarView.setBackgroundColor(Color.TRANSPARENT);
        } else {
            weekCalendarView
                    .setBackgroundResource(R.drawable.press_right_text_bg);
            monthCalendarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onMesureCellHeight(int cellSpace) {
        mSlidingDrawer.getLayoutParams().height = mContentPager.getHeight()
                - cellSpace;
    }

    @Override
    public void clickDate(CustomDate date) {
        temp = "1";
        mClickDate = date;
        setShowDateViewText1(date.year, date.month, date.week);
        // Toast.makeText(this, date.year+"-"+date.month+"-"+date.day,
        // Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),
                "点击了:" + date.month + "月" + date.day + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeDate(CustomDate date) {
        setShowDateViewText(date.year, date.month, date.week);
    }
}
