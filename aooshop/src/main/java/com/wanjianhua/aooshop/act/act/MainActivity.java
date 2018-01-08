package com.wanjianhua.aooshop.act.act;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.adapter.MainPageAdapter;
import com.wanjianhua.aooshop.act.base.BaseActivity;
import com.wanjianhua.aooshop.act.utils.TabPageIndicator;

public class MainActivity extends BaseActivity
{
    private Context context;
    private MainPageAdapter pageAdapter;
    private ViewPager pager;
    private TabPageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValues();
        initView();
    }

    private void initValues()
    {
        context = this;
    }

    private void initView()
    {
        indicator = (TabPageIndicator) findViewById(R.id.indicator_main);
        pager = (ViewPager) findViewById(R.id.pager_main);
        pager.setOffscreenPageLimit(3);
        pageAdapter = new MainPageAdapter(getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        indicator.setViewPager(pager);
    }
}
