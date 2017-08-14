package com.wanjianhua.budejie.pro.essence.view;

import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.wanjianhua.budejie.R;
import com.wanjianhua.budejie.bean.EssecneListBean;
import com.wanjianhua.budejie.pro.essence.presenter.EssenceAllPresenter;
import com.wanjianhua.budejie.pro.essence.view.adapter.EssenceAllAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ying on 2016/6/13.
 * 全部
 */
public class EssenceAllFragment extends EssenceContentFragment<EssenceAllPresenter> implements IEssenceAllView {

    private RecyclerView recyclerView;
    private EssenceAllAdapter adapter;
    private List<EssecneListBean.ListBean> list;
    private SwipyRefreshLayout refreshView;

    @Override
    protected void initView(View view) {
        refreshView = (SwipyRefreshLayout) view.findViewById(R.id.swipyrefreshlayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.essence_all_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new EssenceAllAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
        refreshView.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    //下拉刷新
                    loadData(true);
                } else {
                    loadData(false);
                }
            }
        });

    }

    @Override
    protected void initData() {
        loadData(false);
    }

    private void loadData(boolean isRefresh) {
        getPresenter().getAllEssence(getType(), isRefresh);
    }

    @Override
    protected EssenceAllPresenter bindPresenter() {
        return new EssenceAllPresenter(getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_essence_all;
    }

    @Override
    public void showDialog() {
        showLoading(true);
    }

    @Override
    public void hideDialog() {
        showLoading(false);
    }

    @Override
    public void loadData(List<EssecneListBean.ListBean> data, boolean isDownRefresh) {
        refreshView.setRefreshing(false);
        if (isDownRefresh) {
            adapter.setData(data);
        } else {
            adapter.addData(data);
        }
        Log.d("count", "count:" + adapter.getItemCount());
    }


    @Override
    public void error(Exception e) {

    }
}
