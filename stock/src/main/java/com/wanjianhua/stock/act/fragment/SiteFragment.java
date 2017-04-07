package com.wanjianhua.stock.act.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.act.AddSiteActivity;
import com.wanjianhua.stock.act.act.LoginActivity;
import com.wanjianhua.stock.act.adapter.SiteAdapter;
import com.wanjianhua.stock.act.bean.SiteInfo;
import com.wanjianhua.stock.act.utils.MicroRecruitSettings;
import com.wanjianhua.stock.act.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteFragment extends Fragment {
    private View root;
    private TextView tv_add;
    private ListView list_info;
    private SiteAdapter adapter;
    private List<SiteInfo> infoList = new ArrayList<>();
    private SwipeRefreshLayout swipe;
    private MicroRecruitSettings settings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.site_main, container, false);
        initView(root);
        loadate();
        return root;
    }

    private void loadate() {
        if (settings.PHONE.getValue().equals("")) {
        } else {
            swipe.setRefreshing(true);
            queryInfo();
        }
    }


    private void initView(View root) {
        settings = new MicroRecruitSettings(getActivity());
        swipe = (SwipeRefreshLayout) root.findViewById(R.id.swipe);
        tv_add = (TextView) root.findViewById(R.id.tv_add);
        list_info = (ListView) root.findViewById(R.id.list_info);
        adapter = new SiteAdapter(getActivity());
        list_info.setAdapter(adapter);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddSiteActivity.class);
                startActivity(intent);
            }
        });
        swipe.setEnabled(true);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (settings.PHONE.getValue().equals("")) {
                    swipe.setRefreshing(false);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    queryInfo();
                }
            }
        });
    }

    private void queryInfo() {
        infoList.clear();
        if (!NetUtils.isNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), getString(R.string.netfial), Toast.LENGTH_LONG).show();
            swipe.setRefreshing(false);
            return;
        }
        BmobQuery<SiteInfo> siteInfoBmobQuery = new BmobQuery<>();
        siteInfoBmobQuery.addWhereEqualTo("phone", settings.PHONE.getValue());
        Log.e("phone", settings.PHONE.getValue());
        siteInfoBmobQuery.findObjects(new FindListener<SiteInfo>() {
            @Override
            public void done(List<SiteInfo> list, BmobException e) {
                swipe.setRefreshing(false);
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        SiteInfo info = new SiteInfo();
                        info.setName(list.get(i).getName());
                        info.setCode(list.get(i).getCode());
                        info.setSingleprice(list.get(i).getSingleprice());
                        info.setTotalprice(list.get(i).getTotalprice());
                        infoList.add(info);
                    }
                    adapter.setData(infoList);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.nodata), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
