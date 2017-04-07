package com.wanjianhua.stock.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.base.BaseActivity;
import com.wanjianhua.stock.act.bean.SiteInfo;

import org.w3c.dom.Text;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteDetailActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout linear_detail;
    private View childView;
    private TextView tv_levelchild, tv_waveband;
    private SiteInfo siteInfo;
    private TextView name;
    private TextView tv_totalprice, tv_appreciate, tv_totalcount, tv_maxloser;
    private ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_detail);
        initView();
        getIntentData();
    }

    private void getIntentData() {
        siteInfo = (SiteInfo) getIntent().getSerializableExtra("info");
        name.setText(siteInfo.getName() + "(" + siteInfo.getCode() + ")");
        tv_totalprice.setText(siteInfo.getTotalprice());
        tv_appreciate.setText(siteInfo.getAppreciate());
    }

    /**
     * 每一波段信息
     */
    private TextView tv_nowprice, tv_nowappreciate, singlecount, tv_singleprice, maxwin, nowtv_maxloser;

    private void initView() {
        name = (TextView) findViewById(R.id.name);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tv_totalprice = (TextView) findViewById(R.id.tv_totalprice);
        tv_appreciate = (TextView) findViewById(R.id.tv_appreciate);
        tv_totalcount = (TextView) findViewById(R.id.tv_totalcount);
        tv_maxloser = (TextView) findViewById(R.id.tv_maxloser);
        linear_detail = (LinearLayout) findViewById(R.id.linear_detail);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                childView = LayoutInflater.from(SiteDetailActivity.this).inflate(
                        R.layout.detail_child, null);
                tv_levelchild = (TextView) childView.findViewById(R.id.tv_level);
                tv_waveband = (TextView) childView.findViewById(R.id.tv_waveband);
                //==============================================================
                tv_nowprice = (TextView) childView.findViewById(R.id.tv_nowprice);
                tv_nowappreciate = (TextView) childView.findViewById(R.id.tv_nowappreciate);
                singlecount = (TextView) childView.findViewById(R.id.singlecount);
                tv_singleprice = (TextView) childView.findViewById(R.id.tv_singleprice);
                maxwin = (TextView) childView.findViewById(R.id.maxwin);
                nowtv_maxloser = (TextView) childView.findViewById(R.id.tv_nowmaxloser);
                //===============================================================
                linear_detail.addView(childView);
                if (i == 0) {
                    tv_levelchild.setText(getString(R.string.level1));
                    if (j == 0) {
                        tv_waveband.setText(getString(R.string.wareband1));
                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        tv_levelchild.setVisibility(View.GONE);
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        tv_levelchild.setVisibility(View.GONE);
                    }
                } else if (i == 1) {
                    tv_levelchild.setText(getString(R.string.level2));
                    if (j == 0) {
                        tv_waveband.setText(getString(R.string.wareband1));
                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        tv_levelchild.setVisibility(View.GONE);
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        tv_levelchild.setVisibility(View.GONE);
                    }
                } else if (i == 2) {
                    tv_levelchild.setText(getString(R.string.level3));
                    if (j == 0) {
                        tv_waveband.setText(getString(R.string.wareband1));
                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        tv_levelchild.setVisibility(View.GONE);
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        tv_levelchild.setVisibility(View.GONE);
                    }
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
