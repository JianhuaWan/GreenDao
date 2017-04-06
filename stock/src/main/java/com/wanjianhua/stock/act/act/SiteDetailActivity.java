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
    private View childView;
    private TextView tv_levelchild, tv_waveband;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_detail);
        initView();
    }


    private void initView() {
        linear_detail = (LinearLayout) findViewById(R.id.linear_detail);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                childView = LayoutInflater.from(SiteDetailActivity.this).inflate(
                        R.layout.detail_child, null);
                tv_levelchild = (TextView) childView.findViewById(R.id.tv_level);
                tv_waveband = (TextView) childView.findViewById(R.id.tv_waveband);
                linear_detail.addView(childView);
                if (j == 0) {
                    tv_waveband.setText(getString(R.string.wareband1));
                } else if (j == 1) {
                    tv_waveband.setText(getString(R.string.wareband2));
                    tv_levelchild.setVisibility(View.GONE);
                } else if (j == 2) {
                    tv_waveband.setText(getString(R.string.wareband3));
                    tv_levelchild.setVisibility(View.GONE);
                }
                if (i == 0) {
                    tv_levelchild.setText(getString(R.string.level1));
                } else if (i == 1) {
                    tv_levelchild.setText(getString(R.string.level2));
                } else if (i == 2) {
                    tv_levelchild.setText(getString(R.string.level3));
                }
            }

        }
    }
}
