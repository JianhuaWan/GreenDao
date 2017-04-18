package com.example.calendar;


import com.example.caledar.util.DateUtil;
import com.example.calendar.doim.CustomDate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class AddPlanActivity extends Activity implements OnClickListener{
	private TextView mCancelTv;
	private TextView mConfirmTv;
	private CustomDate mCustomDate;
	private TextView mPlanContentTv;
	private TextView mStartPlanTimeTv;
	private TextView mEndPlanTimeTv;
	private TextView mNoCancelPlanTv;
	private TextView mConfirmCancelPlanTv;
	private View mShowDialogLayout;
	private View mCancelDialogLayout;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_plan);
		getIntentData();
		findById();
		setTextData();
	}

	private void findById() {
		mCancelTv = (TextView) this.findViewById(R.id.cancel_tv);
		mConfirmTv = (TextView)this.findViewById(R.id.confirm_tv);
		mPlanContentTv = (TextView)this.findViewById(R.id.plan_content_tv);
		mStartPlanTimeTv = (TextView)this.findViewById(R.id.start_plan_time_tv);
		mEndPlanTimeTv = (TextView)this.findViewById(R.id.end_plan_time_tv);
		mNoCancelPlanTv = (TextView)this.findViewById(R.id.no_cancel_plan_tv);
		mConfirmCancelPlanTv = (TextView)this.findViewById(R.id.confirm_cancel_plan_tv);
		mShowDialogLayout = this.findViewById(R.id.dialog_show_layout);
		mCancelDialogLayout = this.findViewById(R.id.cancel_dialog_layout);
		setOnClickListener();
		
	}

	private void setTextData() {
		StringBuffer str = new StringBuffer();
		str.append(mCustomDate.toString());
		Log.i("huang", mCustomDate.toString());
		str.append(DateUtil.weekName[mCustomDate.week]);
		str.append(DateUtil.getHour());
		str.append(":");
		int minute1 = DateUtil.getMinute();
		str.append(minute1 < 10 ? "0"+minute1 : minute1);
		mStartPlanTimeTv.setText(str.toString());
		str = new StringBuffer();
		str.append(mCustomDate.toString());
		str.append(DateUtil.weekName[mCustomDate.week]);
		str.append(DateUtil.getHour()+1);
		str.append(":");
		int minute2 = DateUtil.getMinute();
		str.append(minute2 < 10 ? "0"+minute2 : minute2);
		mEndPlanTimeTv.setText(str.toString());
		
	}

	private void setOnClickListener() {
		mCancelTv.setOnClickListener(this);
		mConfirmTv.setOnClickListener(this);
		mPlanContentTv.setOnClickListener(this);
		mStartPlanTimeTv.setOnClickListener(this);
		mEndPlanTimeTv.setOnClickListener(this);
		mNoCancelPlanTv.setOnClickListener(this);
		mConfirmCancelPlanTv.setOnClickListener(this);
		
	}


	private void getIntentData(){
		 mCustomDate = (CustomDate)getIntent()
				 .getSerializableExtra(MainActivity.MAIN_ACTIVITY_CLICK_DATE);
	}
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancel_tv:
			showCancelDialogState(true);
			break;
		case R.id.no_cancel_plan_tv:
			showCancelDialogState(false);
			break;
		case R.id.confirm_cancel_plan_tv:
			finish();
			break;
		case R.id.confirm_tv:
			finish();
			break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	showCancelDialogState(true);
             return true;
         }
         return super.onKeyDown(keyCode, event);
     }
	
	private void showCancelDialogState(boolean isVisable){
		Animation anim = null;
		if(isVisable){
		    anim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_to_up);
			mCancelDialogLayout.setAnimation(anim);
			mShowDialogLayout.setVisibility(View.VISIBLE);
			
			
		}else{
			anim = AnimationUtils.loadAnimation(this, R.anim.slide_up_to_bottom);
			mCancelDialogLayout.setAnimation(anim);
			mShowDialogLayout.setVisibility(View.GONE);
		}
	}
}
