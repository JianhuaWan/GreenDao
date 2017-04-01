package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiangyue.base.BaseActivity;

/**
 * Created by wWX321637 on 2016/5/6.
 */
public class SystemContentActivity extends BaseActivity {
    private TextView tv_content;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.syscontent_main);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(getIntent().getStringExtra("content"));
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setText(getIntent().getStringExtra("time"));
    }

    public void back(View v) {
        finish();
    }
}
