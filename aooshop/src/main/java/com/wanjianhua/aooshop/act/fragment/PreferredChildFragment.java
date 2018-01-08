package com.wanjianhua.aooshop.act.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.act.ShopDetailActivity;
import com.wanjianhua.aooshop.act.adapter.PreferredChildAdapter;
import com.wanjianhua.aooshop.act.adapter.TicketAdapter;
import com.wanjianhua.aooshop.act.bean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * @author 6005001713
 */
public class PreferredChildFragment extends Fragment implements AdapterView.OnItemClickListener
{
    GridView gvPreferred;
    @Bind(R.id.swipe)
    SwipyRefreshLayout swipe;
    private View view;
    public static String photopath = null;
    private PreferredChildAdapter preferredChildAdapter;
    private List<ShopInfo> shopInfos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = (ViewGroup) inflater.inflate(R.layout.preferred_child, container, false);
        initView(view);
        initDate();
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view)
    {
        gvPreferred = (GridView) view.findViewById(R.id.gv_preferred);
        preferredChildAdapter = new PreferredChildAdapter(getActivity());
        gvPreferred.setAdapter(preferredChildAdapter);
        gvPreferred.setOnItemClickListener(this);
    }

    private void initDate()
    {
        for(int i = 0; i < 20; i++)
        {
            shopInfos.add(new ShopInfo());
        }
        preferredChildAdapter.setData(shopInfos);
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        ShopInfo shopInfo = preferredChildAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShopDetailActivity.class);
        startActivity(intent);
    }
}
