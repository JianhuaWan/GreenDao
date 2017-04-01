package com.xiangyue.act;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.xiangyue.base.BaseActivity;

public class OtherMoneyDetailActivity extends BaseActivity {
    private SwipeRefreshLayout sw_refresh;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.othermoneydetail_main);
        initView();
    }

    private void initView() {
        sw_refresh = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        sw_refresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
    }


    public void back(View view) {
        finish();
    }

}
