package com.wanjianhua.aooshop.act.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.wanjianhua.aooshop.R;
import com.wanjianhua.aooshop.act.act.ShopDetailActivity;
import com.wanjianhua.aooshop.act.adapter.StateAdapter;
import com.wanjianhua.aooshop.act.adapter.TicketAdapter;
import com.wanjianhua.aooshop.act.bean.ShopStateInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * @author 6005001713
 */
public class StateAllFragment extends Fragment implements AdapterView.OnItemClickListener
{
    @Bind(R.id.lv_ticket)
    ListView lvTicket;
    private View view;
    public static String photopath = null;
    private static Context context;
    private StateAdapter stateAdapter;
    private SwipyRefreshLayout swipe;
    private List<ShopStateInfo> shopStateInfos = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = (ViewGroup) inflater.inflate(R.layout.me_ticket, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initDate();
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        context = getActivity();
        super.onAttach(context);
    }

    private void initView(View root)
    {
        swipe = (SwipyRefreshLayout) root.findViewById(R.id.swipe);
        swipe.setEnabled(true);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipe.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener()
        {

            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction)
            {

            }
        });
        stateAdapter = new StateAdapter(getActivity());
        lvTicket.setAdapter(stateAdapter);
        lvTicket.setOnItemClickListener(this);
    }

    private void initDate()
    {
        for(int i = 0; i < 5; i++)
        {
            shopStateInfos.add(new ShopStateInfo());
        }
        stateAdapter.setData(shopStateInfos);
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
        ShopStateInfo shopStateInfo = stateAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(context, ShopDetailActivity.class);
        startActivity(intent);
    }
}
