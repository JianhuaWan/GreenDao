package com.wanjianhua.aooshop.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 6005001713 on 2017/12/18.
 * 添加收货地址
 */

public class AddAddressActivity extends BaseActivity
{
    @Bind(R.id.img_back)
    ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addaddress_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
