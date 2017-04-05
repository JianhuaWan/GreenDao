package com.wanjianhua.stock.act.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.act.AddSiteActivity;
import com.wanjianhua.stock.act.adapter.SiteAdapter;
import com.wanjianhua.stock.act.bean.SiteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteFragment extends Fragment {
    private View root;
    private TextView tv_add;
    private ListView list_info;
    private SiteAdapter adapter;
    private List<SiteInfo> infoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.site_main, container, false);
        initView(root);
        loadData();
        return root;
    }

    private void loadData() {
        for (int i = 0; i < 10; i++) {
            SiteInfo info = new SiteInfo();
            info.setName("name" + i);
            info.setCode("code" + i);
            info.setPrice("16.8" + i);
            infoList.add(info);
        }
        adapter.setData(infoList);
    }

    private void initView(View root) {
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
    }

}
