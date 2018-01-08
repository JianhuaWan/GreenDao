package com.wanjianhua.aooshop.act.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.act.AddressListActivity;
import com.wanjianhua.aooshop.act.act.HelpMainActivity;
import com.wanjianhua.aooshop.act.act.SuggestMainActivity;
import com.wanjianhua.aooshop.act.act.TicketMainActivity;
import com.wanjianhua.aooshop.act.utils.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class MineFragment extends Fragment
{
    @Bind(R.id.img_user_icon)
    CircleImageView imgUserIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_degree)
    TextView tvDegree;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.linear_address)
    LinearLayout linearAddress;
    @Bind(R.id.linear_ticket)
    LinearLayout linearTicket;
    @Bind(R.id.linear_customer_service)
    LinearLayout linearCustomerService;
    @Bind(R.id.linear_help)
    LinearLayout linearHelp;
    @Bind(R.id.linear_suggest)
    LinearLayout linearSuggest;
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        root = (ViewGroup) inflater.inflate(R.layout.me_main, container, false);
        initView(root);
        loadData();
        ButterKnife.bind(this, root);
        return root;
    }

    private void loadData()
    {
//        checkversion.setText(NetUtils.getVersion(getActivity()));
    }

    private void initView(View root)
    {
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.linear_address, R.id.linear_ticket, R.id.linear_customer_service, R.id.linear_help, R.id.linear_suggest})
    public void onViewClicked(View view)
    {
        Intent intent = new Intent();
        switch(view.getId())
        {
            case R.id.linear_address:
                intent.setClass(getActivity(), AddressListActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_ticket:
                intent.setClass(getActivity(), TicketMainActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_customer_service:
                break;
            case R.id.linear_help:
                intent.setClass(getActivity(), HelpMainActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_suggest:
                intent.setClass(getActivity(), SuggestMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
