package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;

import com.xiangyue.base.BaseActivity;

//随机碰碰
public class RandonPeopleActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.randompeople_main);
    }

    public void back(View view) {
        finish();
    }

}
