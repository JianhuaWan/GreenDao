package com.xiangyue.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiangyue.act.R;
import com.xiangyue.base.MicroRecruitSettings;

public class OnlineWorkFragment extends Fragment implements OnClickListener {
    private Activity activity;
    private View view;
    private TextView tv_onlinework_timenow, online_work_time1,
            online_work_time2, online_work_time3, online_work_time4,
            online_work_coins1, online_work_coins2, online_work_coins3;
    private TextView tv_onlinework_bg1, tv_onlinework_bg2, tv_onlinework_bg3,
            tv_onlinework_bg4;
    private String task1, task2, task3, task4;
    private String tempTask;
    private MicroRecruitSettings setting;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.online_work_main, null);
        setting = new MicroRecruitSettings(activity);
        ViewGroup parent = (ViewGroup) view.getParent();
        tv_onlinework_bg1 = (TextView) view
                .findViewById(R.id.tv_onlinework_bg1);
        tv_onlinework_bg1.setOnClickListener(this);
        tv_onlinework_bg2 = (TextView) view
                .findViewById(R.id.tv_onlinework_bg2);
        tv_onlinework_bg2.setOnClickListener(this);
        tv_onlinework_bg3 = (TextView) view
                .findViewById(R.id.tv_onlinework_bg3);
        tv_onlinework_bg3.setOnClickListener(this);
        tv_onlinework_bg4 = (TextView) view
                .findViewById(R.id.tv_onlinework_bg4);
        tv_onlinework_bg4.setOnClickListener(this);
        tv_onlinework_timenow = (TextView) view
                .findViewById(R.id.tv_onlinework_timenow);
        online_work_time1 = (TextView) view
                .findViewById(R.id.online_work_time1);
        online_work_time2 = (TextView) view
                .findViewById(R.id.online_work_time2);
        online_work_time3 = (TextView) view
                .findViewById(R.id.online_work_time3);
        online_work_time4 = (TextView) view
                .findViewById(R.id.online_work_time4);
        online_work_coins1 = (TextView) view
                .findViewById(R.id.online_work_coins1);
        online_work_coins2 = (TextView) view
                .findViewById(R.id.online_work_coins2);
        online_work_coins3 = (TextView) view
                .findViewById(R.id.online_work_coins3);

        getUptime();
        return view;
    }

    private void getUptime() {
        handler.postDelayed(runnable, 1000);
    }

    private int nowtime;
    private int c;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            nowtime++;
            if (c <= 9 && nowtime <= 9) {
                tv_onlinework_timenow.setText("0" + c + ":" + "0" + nowtime);
            } else if (c <= 9 && nowtime > 9) {
                tv_onlinework_timenow.setText("0" + c + ":" + nowtime);
            } else if (c > 9 && nowtime <= 9) {
                tv_onlinework_timenow.setText(c + ":" + "0" + nowtime);
            } else if (c > 9 && nowtime > 9) {
                tv_onlinework_timenow.setText(c + ":" + nowtime);
            }
            if (c == 10) {
                tv_onlinework_bg1
                        .setBackgroundResource(R.drawable.button_normal_shape);
                tv_onlinework_bg1.setTextColor(Color.parseColor("#A945E5"));
                tv_onlinework_bg1.setText("领取");
                tv_onlinework_bg1.setEnabled(true);
            } else if (c == 30) {
                tv_onlinework_bg2
                        .setBackgroundResource(R.drawable.button_normal_shape);
                tv_onlinework_bg2.setTextColor(Color.parseColor("#A945E5"));
                tv_onlinework_bg2.setText("领取");
                tv_onlinework_bg2.setEnabled(true);
            } else if (c == 60) {
                tv_onlinework_bg3
                        .setBackgroundResource(R.drawable.button_normal_shape);
                tv_onlinework_bg3.setTextColor(Color.parseColor("#A945E5"));
                tv_onlinework_bg3.setText("领取");
                tv_onlinework_bg3.setEnabled(true);
            } else if (c == 90) {
                tv_onlinework_bg4
                        .setBackgroundResource(R.drawable.button_normal_shape);
                tv_onlinework_bg4.setTextColor(Color.parseColor("#A945E5"));
                tv_onlinework_bg4.setText("领取");
                tv_onlinework_bg4.setEnabled(true);
            } else if (c > 90) {
                nowtime = 0;
                c = 0;
            }
            handler.postDelayed(this, 1000);
            if (nowtime == 59) {
                c++;
                nowtime = -1;
            }
        }
    };


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.tv_onlinework_bg1:
                if (tv_onlinework_bg1.getText().equals("领取")) {
                    tv_onlinework_bg1.setBackgroundResource(R.drawable.workbtn1);
                    tv_onlinework_bg1.setText("已领取");
                    tv_onlinework_bg1.setTextColor(Color.parseColor("#b9b7b8"));
                    tv_onlinework_bg1.setEnabled(false);
                }
                break;
            case R.id.tv_onlinework_bg2:
                if (tv_onlinework_bg2.getText().equals("领取")) {
                    tv_onlinework_bg2.setBackgroundResource(R.drawable.workbtn1);
                    tv_onlinework_bg2.setText("已领取");
                    tv_onlinework_bg2.setTextColor(Color.parseColor("#b9b7b8"));
                    tv_onlinework_bg2.setEnabled(false);
                }
                break;
            case R.id.tv_onlinework_bg3:
                if (tv_onlinework_bg3.getText().equals("领取")) {
                    tv_onlinework_bg3.setBackgroundResource(R.drawable.workbtn1);
                    tv_onlinework_bg3.setText("已领取");
                    tv_onlinework_bg3.setTextColor(Color.parseColor("#b9b7b8"));
                    tv_onlinework_bg3.setEnabled(false);
                }
                break;
            case R.id.tv_onlinework_bg4:
                if (tv_onlinework_bg4.getText().equals("领取")) {
                    tv_onlinework_bg4.setBackgroundResource(R.drawable.workbtn1);
                    tv_onlinework_bg4.setText("已领取");
                    tv_onlinework_bg4.setTextColor(Color.parseColor("#b9b7b8"));
                    tv_onlinework_bg4.setEnabled(false);
                }
                break;
            default:
                break;
        }
    }

}
