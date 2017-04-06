package com.wanjianhua.stock.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.base.BaseActivity;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class AddSiteActivity extends BaseActivity implements View.OnClickListener {
    private View childView;
    private LinearLayout linear_detail;
    private TextView tv_waveband, tv_levelchild;
    private ImageView img_back;
    private TextView tv_add;
    private EditText tv_name, tv_code, tv_point_transfrom, tv_totalPrice;
    private SeekBar tv_appreciate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsite_main);
        initView();
    }

    private void initView() {
        tv_name = (EditText) findViewById(R.id.tv_name);
        tv_code = (EditText) findViewById(R.id.tv_code);
        tv_point_transfrom = (EditText) findViewById(R.id.tv_point_transfrom);
        tv_totalPrice = (EditText) findViewById(R.id.tv_totalPrice);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        linear_detail = (LinearLayout) findViewById(R.id.linear_detail);
        tv_appreciate = (SeekBar) findViewById(R.id.tv_appreciate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_add:
                break;
        }
    }
}
