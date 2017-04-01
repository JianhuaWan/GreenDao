package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangyue.adpter.Kindadapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.bean.KindBean;
import com.xiangyue.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

//标签列
public class KindPeopleActivity extends BaseActivity {
    private PullToRefreshListView pl_kindpeople;
    private TextView tv_title;
    private ListView listView;
    private KindBean kindBean;
    private Kindadapter adapter;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.kindpeople_main);
        initView();
        setUp();
    }

    private void setUp() {
        adapter = new Kindadapter(KindPeopleActivity.this, list);
        listView.setAdapter(adapter);
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        pl_kindpeople = (PullToRefreshListView) findViewById(R.id.pl_kindpeople);
        listView = pl_kindpeople.getRefreshableView();

    }

    public void back(View view) {
        finish();
    }
}
