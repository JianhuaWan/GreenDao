package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiangyue.base.BaseActivity;

public class StudyDetailActivity extends BaseActivity {
    private TextView study_title;
    private String sid;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.study_detail);
        getIntentData();
        initView();
    }

    public void back(View view) {
        finish();
    }

    private void getIntentData() {
        sid = getIntent().getStringExtra("sid");
    }

    public void initView() {
        study_title = (TextView) findViewById(R.id.study_title);
    }
}
