package com.xiangyue.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.act.GameBySZActivity;
import com.xiangyue.act.R;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.coin;
import com.xiangyue.weight.LoginDialog;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

public class GuaHappyFragment extends Fragment implements OnClickListener {
    private Activity activity;
    private View view;
    private MicroRecruitSettings setting;
    private TextView tv_goganme;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gua_happy, null);
        ViewGroup parent = (ViewGroup) view.getParent();
        dialog = new LoginDialog(getActivity(), "");
        dialog.setCanceledOnTouchOutside(false);
        tv_goganme = (TextView) view.findViewById(R.id.tv_goganme);
        tv_goganme.setOnClickListener(this);
        setting = new MicroRecruitSettings(activity);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goganme:
                dialog.show();
                BmobQuery<coin> coinBmobQuery = new BmobQuery<>();
                coinBmobQuery.getObject(getActivity(), setting.OBJECT_IDBYCOIN.getValue(), new GetListener<coin>() {
                    @Override
                    public void onSuccess(coin coin) {
                        if (Integer.parseInt(coin.getCoincount()) >= 10) {


                            coin coin1 = new coin();
                            coin1.setUsername(setting.phone.getValue());
                            int coinover = Integer.parseInt(coin.getCoincount()) - 10;
                            coin1.setCoincount(coinover + "");
                            coin1.update(getActivity(), setting.OBJECT_IDBYCOIN.getValue(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    dialog.dismiss();
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), GameBySZActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), "辣条不足", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
                        dialog.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }

    LoginDialog dialog;
}
