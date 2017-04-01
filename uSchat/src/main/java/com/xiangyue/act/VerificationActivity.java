package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;

import com.xiangyue.base.BaseActivity;

/**
 * 身份认证
 *
 * @author Administrator
 */
public class VerificationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_main);
    }

    public void back(View view) {
        finish();
    }
}
