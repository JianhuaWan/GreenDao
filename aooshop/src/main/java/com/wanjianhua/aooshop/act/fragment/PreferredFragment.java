package com.wanjianhua.aooshop.act.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.act.ProbationActivity;
import com.wanjianhua.aooshop.act.act.SpellGroupActivity;
import com.wanjianhua.aooshop.act.act.TicketMainActivity;
import com.wanjianhua.aooshop.act.adapter.SiteAdapter;
import com.wanjianhua.aooshop.act.bean.SiteInfo;
import com.wanjianhua.aooshop.act.utils.MicroRecruitSettings;
import com.wanjianhua.aooshop.act.utils.PhoneUtils;
import com.wanjianhua.aooshop.act.utils.RefreshMessage;
import com.wanjianhua.aooshop.act.utils.RxBus;

import java.lang.reflect.Field;
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

public class PreferredFragment extends Fragment
{
    @Bind(R.id.img_title)
    ImageView imgTitle;
    @Bind(R.id.searchView)
    SearchView searchView;
    @Bind(R.id.rel_topbyme)
    RelativeLayout relTopbyme;
    @Bind(R.id.linear_spellgroup)
    LinearLayout linearSpellgroup;
    @Bind(R.id.linear_tryuse)
    LinearLayout linearTryuse;
    @Bind(R.id.linear_coupons)
    LinearLayout linearCoupons;
    @Bind(R.id.rel_kind)
    RelativeLayout relKind;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.view_buttom)
    View viewButtom;
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
    @Bind(R.id.linear_content_preferred)
    LinearLayout listInfo;
    private View root;
    private List<SiteInfo> infoList = new ArrayList<>();
    private MicroRecruitSettings settings;
    Subscription rxbus;
    private Context context;
    private PreferredChildFragment preferredChildFragment;


    @Override
    public void onAttach(Context context)
    {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        root = (ViewGroup) inflater.inflate(R.layout.preferred_main, container, false);
        ButterKnife.bind(this, root);
        initView(root);
        loadate();
        setLisenr();
        return root;
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


    /**
     * 删除操作
     */
    private void del(SiteInfo siteInfo)
    {

    }


    private void loadate()
    {
        mHandler.sendEmptyMessageDelayed(0, 500);

    }


    private LinearLayout linear_spellgroup, linear_tryuse, linear_coupons;
    private SearchView mSearchView;


    private void initView(View root)
    {
        settings = new MicroRecruitSettings(getActivity());
        linear_spellgroup = (LinearLayout) root.findViewById(R.id.linear_spellgroup);
        linear_tryuse = (LinearLayout) root.findViewById(R.id.linear_tryuse);
        linear_coupons = (LinearLayout) root.findViewById(R.id.linear_coupons);
        linear_spellgroup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(context, SpellGroupActivity.class);
                startActivity(intent);
            }
        });
        linear_tryuse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(context, ProbationActivity.class);
                startActivity(intent);
            }
        });
        linear_coupons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(context, TicketMainActivity.class);
                startActivity(intent);
            }
        });
        mSearchView = (SearchView) root.findViewById(R.id.searchView);
        try
        {
            //文字向下便宜
            int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView tvSearchView = (TextView) mSearchView.findViewById(id);
            tvSearchView.setTextSize(12);
            tvSearchView.setHeight(10);
            tvSearchView.setHintTextColor(getResources().getColor(R.color.app_color));
            tvSearchView.setTextColor(getResources().getColor(R.color.app_color));
            if(PhoneUtils.isMIUI())
            {
                tvSearchView.setPadding(0, 0, 0, 0);
            }
            else
            {
                tvSearchView.setPadding(0, 45, 0, 0);
            }
            //去掉searchView下划线
            Field ownField = mSearchView.getClass().getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(mSearchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }

        initfragmentView(root);
    }

    private void initfragmentView(View root)
    {
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
                preferredChildFragment = new PreferredChildFragment();
                transaction.replace(R.id.linear_content_preferred, preferredChildFragment).commit();
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
                preferredChildFragment = new PreferredChildFragment();
                transaction.replace(R.id.linear_content_preferred, preferredChildFragment).commit();
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
                preferredChildFragment = new PreferredChildFragment();
                transaction.replace(R.id.linear_content_preferred, preferredChildFragment).commit();
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
                preferredChildFragment = new PreferredChildFragment();
                transaction.replace(R.id.linear_content_preferred, preferredChildFragment).commit();
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
                preferredChildFragment = new PreferredChildFragment();
                transaction.replace(R.id.linear_content_preferred, preferredChildFragment).commit();
                break;
        }
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch(msg.what)
            {
                case 0:
                    android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
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
                    preferredChildFragment = new PreferredChildFragment();
                    transaction.replace(R.id.linear_content_preferred, preferredChildFragment).commit();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy()
    {
        if(mHandler != null)
        {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }
}
