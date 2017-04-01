package com.xiangyue.act;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangyue.adpter.MymoneyAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MyMoneyActivity extends BaseActivity implements OnClickListener {
    private TextView tv_getmoney;
    private ListView listView;
    private PullToRefreshListView pullToRefreshListView;
    private MymoneyAdapter adapter;
    private List<String> lStrings = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.mymoney_main);
        getHeadView();
        initView();
    }

    private View view;

    private void getHeadView() {
        view = LayoutInflater.from(this).inflate(R.layout.item_mymoney, null);
    }

    public void initView() {
        tv_getmoney = (TextView) findViewById(R.id.tv_getmoney);
        tv_getmoney.setOnClickListener(this);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pl_mymoney);
        listView = pullToRefreshListView.getRefreshableView();
        if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            // 如果API版本高于9，则设置listview没有自带滚动结束后的反弹效果
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        lStrings.add("1");
        lStrings.add("3");
        lStrings.add("2");
        lStrings.add("4");
        lStrings.add("5");
        lStrings.add("7");
        lStrings.add("6");
        lStrings.add("8");
        lStrings.add("9");
        adapter = new MymoneyAdapter(getActivity(), lStrings);
        listView.addHeaderView(view);
        listView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_getmoney:
                Intent intent = new Intent();
                intent.setClass(MyMoneyActivity.this, BusinessActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

}
