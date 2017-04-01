package com.xiangyue.act;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.util.SysApplication;

import java.util.LinkedList;

public class GuideActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
    private ViewPager viewPager;
    private Button login, register;
    private ImageView page0, page1, page2, page3, page4;
    private LinkedList<View> mViews = null;
    private LayoutInflater mInflater = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_guide_layout);
        mViews = new LinkedList<View>();
        mInflater = LayoutInflater.from(this);
        View view1 = mInflater.inflate(R.layout.guide1, null);
        View view2 = mInflater.inflate(R.layout.guide2, null);
        View view3 = mInflater.inflate(R.layout.guide3, null);
        View view4 = mInflater.inflate(R.layout.guide4, null);
        View view5 = mInflater.inflate(R.layout.guide5, null);
        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);
        mViews.add(view4);
        mViews.add(view5);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        page0 = (ImageView) findViewById(R.id.page0);
        page1 = (ImageView) findViewById(R.id.page1);
        page2 = (ImageView) findViewById(R.id.page2);
        page3 = (ImageView) findViewById(R.id.page3);
        page4 = (ImageView) findViewById(R.id.page4);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login:
                redictToActivity(GuideActivity.this, LoginActivity.class, null);
                break;
            case R.id.register:
                redictToActivity(GuideActivity.this, RegisterActivity.class, null);
                break;

            default:
                break;
        }
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mViews.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub
            View childView = mViews.get(position);
            ((ViewPager) container).addView(childView);
            return childView;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            // TODO Auto-generated method stub
            return view == obj;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        switch (arg0) {
            case 0:
                page0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                page1.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page2.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page3.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page4.setImageDrawable(getResources().getDrawable(R.drawable.page));
                break;
            case 1:
                page0.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                page2.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page3.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page4.setImageDrawable(getResources().getDrawable(R.drawable.page));
                break;
            case 2:
                page0.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page1.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page3.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page4.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                break;
            case 3:
                page0.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page1.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                page4.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page2.setImageDrawable(getResources().getDrawable(R.drawable.page));
                break;
            case 4:
                page0.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page1.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page3.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page4.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                page2.setImageDrawable(getResources().getDrawable(R.drawable.page));
                break;

            default:
                page0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
                page1.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page2.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page3.setImageDrawable(getResources().getDrawable(R.drawable.page));
                page4.setImageDrawable(getResources().getDrawable(R.drawable.page));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("finishActivity");
        sendBroadcast(intent);
        SysApplication.getInstance().exit();
    }
}
