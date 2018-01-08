package com.wanjianhua.aooshop.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.base.BaseActivity;
import com.wanjianhua.aooshop.act.fragment.AllTicketFragment;
import com.wanjianhua.aooshop.act.fragment.MeTicketFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 6005001713 on 2017/12/19.
 */

public class TicketMainActivity extends BaseActivity
{
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.rel_topbyme)
    RelativeLayout relTopbyme;
    @Bind(R.id.tv_meticket)
    TextView tvMeticket;
    @Bind(R.id.tv_allticket)
    TextView tvAllticket;
    @Bind(R.id.lienar_title)
    LinearLayout lienarTitle;
    @Bind(R.id.linear_content)
    LinearLayout linearContent;
    private LinearLayout linear_content;
    private MeTicketFragment meTicketFragment;
    private AllTicketFragment allTicketFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_main);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment()
    {
        setDefaultFragment();
    }

    private void setDefaultFragment()
    {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        meTicketFragment = new MeTicketFragment();
        transaction.replace(R.id.linear_content, meTicketFragment);
        transaction.commit();
        tvMeticket.setBackgroundColor(getResources().getColor(R.color.app_color));
        tvMeticket.setTextColor(getResources().getColor(R.color.white));
        tvAllticket.setBackgroundColor(getResources().getColor(R.color.white));
        tvAllticket.setTextColor(getResources().getColor(R.color.app_color));
    }

    @OnClick({R.id.img_back, R.id.tv_meticket, R.id.tv_allticket})
    public void onViewClicked(View view)
    {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch(view.getId())
        {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_meticket:
                tvMeticket.setBackgroundColor(getResources().getColor(R.color.app_color));
                tvMeticket.setTextColor(getResources().getColor(R.color.white));
                tvAllticket.setBackgroundColor(getResources().getColor(R.color.white));
                tvAllticket.setTextColor(getResources().getColor(R.color.app_color));
                meTicketFragment = new MeTicketFragment();
                transaction.replace(R.id.linear_content, meTicketFragment).commit();
                break;
            case R.id.tv_allticket:
                tvAllticket.setBackgroundColor(getResources().getColor(R.color.app_color));
                tvAllticket.setTextColor(getResources().getColor(R.color.white));
                tvMeticket.setBackgroundColor(getResources().getColor(R.color.white));
                tvMeticket.setTextColor(getResources().getColor(R.color.app_color));
                allTicketFragment = new AllTicketFragment();
                transaction.replace(R.id.linear_content, allTicketFragment).commit();
                break;
        }
    }
}
