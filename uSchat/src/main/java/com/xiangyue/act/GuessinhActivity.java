package com.xiangyue.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.fragment.GuaHappyFragment;
import com.xiangyue.fragment.OneChoiseFragment;

/**
 * Created by wWX321637 on 2016/5/9.
 */
public class GuessinhActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager mFM = null;
    private LinearLayout mTab_item_container;
    private LinearLayout linear_work_1, linear_work_2;
    private TextView tv_work_1, tv_work_2;
    private ImageView img_work_left, img_work_right;
    private ImageView room_work_2, room_work_1;
    private ImageView img_situp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guessin_main);
        init();
        changePerson();
    }

    /**
     * 初始化
     */
    private void init() {
        img_situp = (ImageView) findViewById(R.id.img_situp);
        img_situp.setOnClickListener(this);
        room_work_1 = (ImageView) findViewById(R.id.room_work_1);
        room_work_1.setBackgroundColor(getResources().getColor(R.color.appcolor));
        room_work_2 = (ImageView) findViewById(R.id.room_work_2);
        room_work_2.setBackgroundColor(Color.parseColor("#d3d3d3"));
        img_work_left = (ImageView) findViewById(R.id.img_work_left);
        img_work_left.setBackgroundResource(R.drawable.loginworkiconed);
        img_work_right = (ImageView) findViewById(R.id.img_work_right);
        img_work_right.setBackgroundResource(R.drawable.onlineworkicon);
        tv_work_1 = (TextView) findViewById(R.id.tv_work_1);
        tv_work_1.setTextColor(getResources().getColor(R.color.appcolor));
        tv_work_2 = (TextView) findViewById(R.id.tv_work_2);
        tv_work_2.setTextColor(Color.parseColor("#686868"));
        mTab_item_container = (LinearLayout) findViewById(R.id.work_content);
        linear_work_1 = (LinearLayout) findViewById(R.id.linear_work_1);
        linear_work_1.setOnClickListener(this);
        linear_work_2 = (LinearLayout) findViewById(R.id.linear_work_2);
        linear_work_2.setOnClickListener(this);
    }

    private View last, now;
    View v1, v2;

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.img_situp:
                //介绍
                Intent intent = new Intent();
                intent.setClass(GuessinhActivity.this, GameInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_work_1:
                now = mTab_item_container.getChildAt(0);
                tv_work_1.setTextColor(getResources().getColor(R.color.appcolor));
                tv_work_2.setTextColor(Color.parseColor("#686868"));
                room_work_2.setBackgroundColor(Color.parseColor("#d3d3d3"));// 线条颜色值
                room_work_1.setBackgroundColor(getResources().getColor(R.color.appcolor));
                img_work_left.setBackgroundResource(R.drawable.loginworkiconed);
                img_work_right.setBackgroundResource(R.drawable.onlineworkicon);
                changePerson();
                break;
            case R.id.linear_work_2:
                now = mTab_item_container.getChildAt(1);
                tv_work_1.setTextColor(Color.parseColor("#686868"));
                tv_work_2.setTextColor(getResources().getColor(R.color.appcolor));
                room_work_2.setBackgroundColor(getResources().getColor(R.color.appcolor));// 线条颜色值
                room_work_1.setBackgroundColor(Color.parseColor("#d3d3d3"));
                img_work_left.setBackgroundResource(R.drawable.loginworkno);
                img_work_right.setBackgroundResource(R.drawable.onlineworked);
                changeBussiness();
                break;
            default:
                break;
        }
    }

    private void changePerson() {
        Fragment f = new OneChoiseFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();// 有改动
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.work_content, f);
        ft.commit();
    }

    private void changeBussiness() {
        Fragment f = new GuaHappyFragment();
        if (null == mFM)
            mFM = getSupportFragmentManager();
        FragmentTransaction ft = mFM.beginTransaction();
        ft.replace(R.id.work_content, f);
        ft.commit();
    }

    public void back(View view) {
        finish();
    }

}
