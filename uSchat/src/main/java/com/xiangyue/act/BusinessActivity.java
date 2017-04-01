package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;

import com.xiangyue.base.BaseActivity;

public class BusinessActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.business_main);
    }

    public void back(View view) {
        finish();
    }

}
