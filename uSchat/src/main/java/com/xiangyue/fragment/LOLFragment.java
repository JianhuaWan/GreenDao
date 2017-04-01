package com.xiangyue.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.act.GameActivity;
import com.xiangyue.act.R;
import com.xiangyue.adpter.GameAdapter;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.LOLInfo;
import com.xiangyue.bean.coin;
import com.xiangyue.weight.LoginDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

public class LOLFragment extends Fragment {
    private Activity activity;
    private View view;
    private ImageView img_icon;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GameAdapter adapter;
    private List<LOLInfo> lolInfos = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lol_main, null);
        dialog = new LoginDialog(getActivity(), "");
        dialog.setCanceledOnTouchOutside(false);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_game);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GameAdapter(activity);
        recyclerView.setAdapter(adapter);
        setData();
        return view;
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            LOLInfo lolInfo = new LOLInfo();
            lolInfo.setContent(i + "test");
            lolInfo.setTime(i + "2017年3月31日 16:49:39");
            lolInfos.add(lolInfo);
        }
        adapter.setData(lolInfos);
    }


    LoginDialog dialog;
}
