package com.wanjianhua.aooshop.act.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.adapter.AddressAdapter;
import com.wanjianhua.aooshop.act.base.BaseActivity;
import com.wanjianhua.aooshop.act.bean.AddressInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 6005001713 on 2017/12/18.
 * 收获地址列表
 */

public class AddressListActivity extends BaseActivity
{
    @Bind(R.id.rel_topbyme)
    RelativeLayout relTopbyme;
    @Bind(R.id.linear_content)
    ListView linearContent;
    @Bind(R.id.swipe)
    SwipyRefreshLayout swipe;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.linear_buttom)
    LinearLayout linearButtom;
    @Bind(R.id.img_back)
    ImageView imgBack;

    private AddressAdapter addresAdapter;
    private List<AddressInfo> addressInfos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list);
        ButterKnife.bind(this);
        initView();
        initData();
        setListen();
    }

    private void initView()
    {
        addresAdapter = new AddressAdapter(AddressListActivity.this);
        linearContent.setAdapter(addresAdapter);
    }

    private void setListen()
    {
    }

    private void initData()
    {
        for(int i = 0; i < 3; i++)
        {
            addressInfos.add(new AddressInfo());
        }
        addresAdapter.setData(addressInfos);
    }

    @OnClick({R.id.img_back, R.id.linear_buttom})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.linear_buttom:
                Intent intent = new Intent();
                intent.setClass(AddressListActivity.this, AddAddressActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
