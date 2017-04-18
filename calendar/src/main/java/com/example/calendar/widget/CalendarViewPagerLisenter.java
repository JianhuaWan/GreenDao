package com.example.calendar.widget;

import android.support.v4.view.ViewPager.OnPageChangeListener;

public class CalendarViewPagerLisenter implements OnPageChangeListener {

	private SildeDirection mDirection = SildeDirection.NO_SILDE;
	int mCurrIndex = 498;
	private CalendarView[] mShowViews;

	public CalendarViewPagerLisenter(CustomViewPagerAdapter<CalendarView> viewAdapter) {
		super();
		this.mShowViews = viewAdapter.getAllItems();
	}

	@Override
	public void onPageSelected(int arg0) {
		measureDirection(arg0);
		updateCalendarView(arg0);
	}

	private void updateCalendarView(int arg0) {
		if(mDirection == SildeDirection.RIGHT){
			mShowViews[arg0 % mShowViews.length].rightSilde();
		}else if(mDirection == SildeDirection.LEFT){
			mShowViews[arg0 % mShowViews.length].leftSilde();
		}
		mDirection = SildeDirection.NO_SILDE;
	}


	/**
	 * 判断滑动方向
	 * @param arg0
	 */
	private void measureDirection(int arg0) {

		if (arg0 > mCurrIndex) {
			mDirection = SildeDirection.RIGHT;

		} else if (arg0 < mCurrIndex) {
			mDirection = SildeDirection.LEFT;
		}
		mCurrIndex = arg0;
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}



	enum SildeDirection {
		RIGHT, LEFT, NO_SILDE;
	}
}