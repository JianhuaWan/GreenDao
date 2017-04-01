package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;

import com.xiangyue.base.BaseActivity;

/**
 * Created by wWX321637 on 2016/5/10.
 */
public class SetMoneyActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.setmoney_main);

    }

    public void back(View view) {
        finish();
    }
}
