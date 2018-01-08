package com.wanjianhua.aooshop.act.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 6005001713 on 2017/12/22.
 */

public class BuyShopActivity extends BaseActivity
{
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.rel_topbyme)
    RelativeLayout relTopbyme;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.tv_getmen)
    TextView tvGetmen;
    @Bind(R.id.rel_address_top)
    RelativeLayout relAddressTop;
    @Bind(R.id.rel_address)
    RelativeLayout relAddress;
    @Bind(R.id.lienar_pic)
    LinearLayout lienarPic;
    @Bind(R.id.tv_write)
    TextView tvWrite;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_preferential)
    TextView tvPreferential;
    @Bind(R.id.linear_1)
    RelativeLayout linear1;
    @Bind(R.id.linear_2)
    RelativeLayout linear2;
    @Bind(R.id.linear_3)
    RelativeLayout linear3;
    @Bind(R.id.linear_4)
    RelativeLayout linear4;
    @Bind(R.id.tv_tatal)
    TextView tvTatal;
    @Bind(R.id.tv_tatalprice)
    TextView tvTatalprice;
    @Bind(R.id.linear_submit)
    RelativeLayout linearSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyshop_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.rel_address})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.rel_address:
                Intent intent = new Intent();
                intent.setClass(BuyShopActivity.this, AddressListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
