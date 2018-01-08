package com.wanjianhua.aooshop.act.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.bean.SiteInfo;
import com.wanjianhua.aooshop.act.utils.MicroRecruitSettings;
import com.wanjianhua.aooshop.act.utils.RefreshMessage;
import com.wanjianhua.aooshop.act.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class OrderFragment extends Fragment
{
    @Bind(R.id.rel_topbyme)
    RelativeLayout relTopbyme;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.view_isshow1)
    View viewIsshow1;
    @Bind(R.id.tv_unpay)
    TextView tvUnpay;
    @Bind(R.id.view_isshow2)
    View viewIsshow2;
    @Bind(R.id.tv_unsend)
    TextView tvUnsend;
    @Bind(R.id.view_isshow3)
    View viewIsshow3;
    @Bind(R.id.tv_unaccept)
    TextView tvUnaccept;
    @Bind(R.id.view_isshow4)
    View viewIsshow4;
    @Bind(R.id.tv_end)
    TextView tvEnd;
    @Bind(R.id.view_isshow5)
    View viewIsshow5;
    @Bind(R.id.linear_top)
    LinearLayout linearTop;
    @Bind(R.id.hor_top)
    HorizontalScrollView horTop;
    @Bind(R.id.linear_content_order)
    LinearLayout linearContent;
    private View root;
    private LinearLayout list_info;
    private List<SiteInfo> infoList = new ArrayList<>();
    private MicroRecruitSettings settings;
    private boolean isexute = true;
    Subscription rxbus;
    private LinearLayout linear_top;
    private Fragment allfragment, unpayfragment, unsendfragment, unacceptfragment, endfragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        root = (ViewGroup) inflater.inflate(R.layout.order_main, container, false);
        initView(root);
        ButterKnife.bind(this, root);
        initFragment();
        loadate();
        setLisenr();
        return root;
    }

    private void initFragment()
    {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        allfragment = new StateAllFragment();
        transaction.replace(R.id.linear_content_order, allfragment);
        transaction.commit();
        tvAll.setTextColor(getResources().getColor(R.color.app_color));
        viewIsshow1.setVisibility(View.VISIBLE);
        tvUnpay.setTextColor(getResources().getColor(R.color.color_chat_send_status));
        viewIsshow2.setVisibility(View.INVISIBLE);
        tvUnsend.setTextColor(getResources().getColor(R.color.color_chat_send_status));
        viewIsshow3.setVisibility(View.INVISIBLE);
        tvUnaccept.setTextColor(getResources().getColor(R.color.color_chat_send_status));
        viewIsshow4.setVisibility(View.INVISIBLE);
        tvEnd.setTextColor(getResources().getColor(R.color.color_chat_send_status));
        viewIsshow5.setVisibility(View.INVISIBLE);
    }

    private void setLisenr()
    {
        rxbus = RxBus.getInstance().toObserverable(RefreshMessage.class)
                .subscribe(new Action1<RefreshMessage>()
                {
                    @Override
                    public void call(RefreshMessage refreshMessage)
                    {
                        loadate();
                    }
                });
    }

    private void loadate()
    {
        if(settings.PHONE.getValue().equals(""))
        {
        }
        else
        {
        }
    }


    private void initView(View root)
    {
        settings = new MicroRecruitSettings(getActivity());
        list_info = (LinearLayout) root.findViewById(R.id.linear_content_order);

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_all, R.id.tv_unpay, R.id.tv_unsend, R.id.tv_unaccept, R.id.tv_end})
    public void onViewClicked(View view)
    {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch(view.getId())
        {
            case R.id.tv_all:
                tvAll.setTextColor(getResources().getColor(R.color.app_color));
                viewIsshow1.setVisibility(View.VISIBLE);
                tvUnpay.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow2.setVisibility(View.INVISIBLE);
                tvUnsend.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow3.setVisibility(View.INVISIBLE);
                tvUnaccept.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow4.setVisibility(View.INVISIBLE);
                tvEnd.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow5.setVisibility(View.INVISIBLE);
                allfragment = new StateAllFragment();
                transaction.replace(R.id.linear_content_order, allfragment).commit();
                break;
            case R.id.tv_unpay:
                tvAll.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow1.setVisibility(View.INVISIBLE);
                tvUnpay.setTextColor(getResources().getColor(R.color.app_color));
                viewIsshow2.setVisibility(View.VISIBLE);
                tvUnsend.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow3.setVisibility(View.INVISIBLE);
                tvUnaccept.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow4.setVisibility(View.INVISIBLE);
                tvEnd.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow5.setVisibility(View.INVISIBLE);
                unpayfragment = new StateUnpayFragment();
                transaction.replace(R.id.linear_content_order, unpayfragment).commit();
                break;
            case R.id.tv_unsend:
                tvUnsend.setTextColor(getResources().getColor(R.color.app_color));
                viewIsshow3.setVisibility(View.VISIBLE);
                tvAll.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow1.setVisibility(View.INVISIBLE);
                tvUnpay.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow2.setVisibility(View.INVISIBLE);
                tvUnaccept.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow4.setVisibility(View.INVISIBLE);
                tvEnd.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow5.setVisibility(View.INVISIBLE);
                unsendfragment = new StateUnsendFragment();
                transaction.replace(R.id.linear_content_order, unsendfragment).commit();
                break;
            case R.id.tv_unaccept:
                tvUnaccept.setTextColor(getResources().getColor(R.color.app_color));
                viewIsshow4.setVisibility(View.VISIBLE);
                tvAll.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow1.setVisibility(View.INVISIBLE);
                tvUnpay.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow2.setVisibility(View.INVISIBLE);
                tvUnsend.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow3.setVisibility(View.INVISIBLE);
                tvEnd.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow5.setVisibility(View.INVISIBLE);
                unacceptfragment = new StateUnacceptFragment();
                transaction.replace(R.id.linear_content_order, unacceptfragment).commit();
                break;
            case R.id.tv_end:
                tvEnd.setTextColor(getResources().getColor(R.color.app_color));
                viewIsshow5.setVisibility(View.VISIBLE);
                tvAll.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow1.setVisibility(View.INVISIBLE);
                tvUnpay.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow2.setVisibility(View.INVISIBLE);
                tvUnsend.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow3.setVisibility(View.INVISIBLE);
                tvUnaccept.setTextColor(getResources().getColor(R.color.color_chat_send_status));
                viewIsshow4.setVisibility(View.INVISIBLE);
                endfragment = new StateEndFragment();
                transaction.replace(R.id.linear_content_order, endfragment).commit();
                break;
        }
    }
}
