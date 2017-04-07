package com.wanjianhua.stock.act.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.base.BaseActivity;
import com.wanjianhua.stock.act.bean.SiteInfo;
import com.wanjianhua.stock.act.utils.MicroRecruitSettings;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
    private TextView tv_appreciate;
    private MicroRecruitSettings settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsite_main);
        settings = new MicroRecruitSettings(this);
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
        tv_appreciate = (TextView) findViewById(R.id.tv_appreciate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_add:
                if (settings.PHONE.getValue().equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(AddSiteActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (tv_name.getText().toString().isEmpty() || tv_code.getText().toString().isEmpty() || tv_totalPrice.getText().toString().isEmpty() || tv_point_transfrom.getText().toString().isEmpty()) {
                        Toast.makeText(AddSiteActivity.this, getString(R.string.infonotwrite), Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        //添加一行数据
                        SiteInfo siteInfo = new SiteInfo();
                        siteInfo.setPhone(settings.PHONE.getValue().toString());
                        siteInfo.setName(tv_name.getText().toString());
                        siteInfo.setCode(tv_code.getText().toString());
                        siteInfo.setSingleprice(tv_point_transfrom.getText().toString());
                        siteInfo.setTotalprice(tv_totalPrice.getText().toString());
                        siteInfo.setBalance("1:1:3");
                        siteInfo.setAppreciate("-30%");
                        siteInfo.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddSiteActivity.this, getString(R.string.savepass), Toast.LENGTH_LONG).show();
                                    tv_name.setText("");
                                    tv_code.setText("");
                                    tv_point_transfrom.setText("");
                                    tv_totalPrice.setText("");
                                } else {
                                    Toast.makeText(AddSiteActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
                break;
        }
    }
}
