package com.xiangyue.act;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.xiangyue.adpter.GamePageAdapter;
import com.xiangyue.adpter.MainPageAdapter;
import com.xiangyue.base.BaseActivity;

/**
 * Created by wanjianhua on 2017/3/24.
 */

public class GameNewsActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout line_lol, line_lus, line_cf, line_dnf;
    private ViewPager pager;
    private GamePageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.gamenews_main);
        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager_main);
        line_lol = (LinearLayout) findViewById(R.id.line_lol);
        line_lus = (LinearLayout) findViewById(R.id.line_lus);
        line_cf = (LinearLayout) findViewById(R.id.line_cf);
        line_dnf = (LinearLayout) findViewById(R.id.line_dnf);
        line_lol.setOnClickListener(this);
        line_lus.setOnClickListener(this);
        line_cf.setOnClickListener(this);
        line_dnf.setOnClickListener(this);
        pager.setOffscreenPageLimit(3);
        pageAdapter = new GamePageAdapter(getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    line_lol.setBackground(getResources().getDrawable(R.drawable.btn_game));
                    line_lus.setBackground(null);
                    line_cf.setBackground(null);
                    line_dnf.setBackground(null);
                } else if (position == 1) {
                    line_lol.setBackground(null);
                    line_lus.setBackground(getResources().getDrawable(R.drawable.btn_game));
                    line_cf.setBackground(null);
                    line_dnf.setBackground(null);
                } else if (position == 2) {
                    line_lol.setBackground(null);
                    line_lus.setBackground(null);
                    line_cf.setBackground(getResources().getDrawable(R.drawable.btn_game));
                    line_dnf.setBackground(null);
                } else if (position == 3) {
                    line_lol.setBackground(null);
                    line_lus.setBackground(null);
                    line_cf.setBackground(null);
                    line_dnf.setBackground(getResources().getDrawable(R.drawable.btn_game));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_lol:
                pager.setCurrentItem(0);
                line_lol.setBackground(getResources().getDrawable(R.drawable.btn_game));
                line_lus.setBackground(null);
                line_cf.setBackground(null);
                line_dnf.setBackground(null);
                break;
            case R.id.line_lus:
                pager.setCurrentItem(1);
                line_lol.setBackground(null);
                line_lus.setBackground(getResources().getDrawable(R.drawable.btn_game));
                line_cf.setBackground(null);
                line_dnf.setBackground(null);
                break;
            case R.id.line_cf:
                pager.setCurrentItem(2);
                line_lol.setBackground(null);
                line_lus.setBackground(null);
                line_cf.setBackground(getResources().getDrawable(R.drawable.btn_game));
                line_dnf.setBackground(null);
                break;
            case R.id.line_dnf:
                pager.setCurrentItem(3);
                line_lol.setBackground(null);
                line_lus.setBackground(null);
                line_cf.setBackground(null);
                line_dnf.setBackground(getResources().getDrawable(R.drawable.btn_game));
                break;
            default:
                break;
        }
    }
}
