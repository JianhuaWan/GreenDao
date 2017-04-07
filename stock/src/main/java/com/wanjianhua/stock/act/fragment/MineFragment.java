package com.wanjianhua.stock.act.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.act.WelcomeActivity;
import com.wanjianhua.stock.act.utils.MicroRecruitSettings;
import com.wanjianhua.stock.act.utils.NetUtils;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private View root;
    private TextView tv_phonenum, checkversion;
    private Button btn_loginout;
    private MicroRecruitSettings settings;
    private LinearLayout linear_check;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.me_main, container, false);
        initView(root);
        loadData();
        return root;
    }

    private void loadData() {
        if (settings.PHONE.getValue().equals("")) {
            tv_phonenum.setText(getString(R.string.nologin));
        } else {
            tv_phonenum.setText(settings.PHONE.getValue().toString());
        }
        checkversion.setText(NetUtils.getVersion(getActivity()));
    }

    private void initView(View root) {
        settings = new MicroRecruitSettings(getActivity());
        tv_phonenum = (TextView) root.findViewById(R.id.tv_phonenum);
        checkversion = (TextView) root.findViewById(R.id.checkversion);
        btn_loginout = (Button) root.findViewById(R.id.btn_loginout);
        btn_loginout.setOnClickListener(this);
        linear_check = (LinearLayout) root.findViewById(R.id.linear_check);
        linear_check.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loginout:
                settings.PHONE.setValue("");
                Intent intent = new Intent();
                intent.setClass(getActivity(), WelcomeActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.linear_check:
                Toast.makeText(getActivity(), NetUtils.getVersion(getActivity()), Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (settings.PHONE.getValue().equals("")) {
            tv_phonenum.setText(getString(R.string.nologin));
        } else {
            tv_phonenum.setText(settings.PHONE.getValue().toString());
        }
    }
}
