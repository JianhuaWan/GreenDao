package com.wanjianhua.stock.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.base.BaseActivity;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteDetailActivity extends BaseActivity {
    private LinearLayout linear_detail;
    private View childview;
    private TextView tv_level;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_detail);
        initView();
    }


    private void initView() {
        linear_detail = (LinearLayout) findViewById(R.id.linear_detail);
        for (int i = 0; i < 3; i++) {
            childview = LayoutInflater.from(SiteDetailActivity.this).inflate(
                    R.layout.detail_child, null);
            tv_level = (TextView) childview.findViewById(R.id.tv_level);
            if (i == 0) {
                tv_level.setText(getString(R.string.level1));
            } else if (i == 1) {
                tv_level.setText(getString(R.string.level2));
            } else if (i == 2) {
                tv_level.setText(getString(R.string.level3));
            }
            linear_detail.addView(childview);
        }
    }
}
