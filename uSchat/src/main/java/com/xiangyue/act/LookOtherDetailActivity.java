package com.xiangyue.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.xiangyue.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class LookOtherDetailActivity extends BaseActivity implements OnClickListener {
    private LinearLayout go_photo;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.lookotherdetail_main);
        initView();
    }

    public void initView() {
        go_photo = (LinearLayout) findViewById(R.id.go_photo);
        go_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.go_photo:
                // 他人相册
                intent.putExtra("flag", "other");
                intent.putStringArrayListExtra("photos", (ArrayList<String>) list);
                intent.putExtra("pos", 0);
                intent.setClass(LookOtherDetailActivity.this, PhotoDetailActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

}
