package com.xiangyue.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.act.R;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.coin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginWorkFragment extends Fragment implements OnClickListener {
    private Activity activity;
    private View view;
    private TextView tv_today_coins, tv_work_coins1, tv_work_coins2,
            tv_work_coins3, tv_work_coins4, continuous, tv_work_nowgetcoins,
            tv_type_isvip_loginwork;
    private String isgetcoins;
    private ImageView img_workbg_isvip;
    private MicroRecruitSettings setting;
    private String addcount;
    private String count;
    private String nowcount;
    private boolean issign = false;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_work_main, null);
        ViewGroup parent = (ViewGroup) view.getParent();
        setting = new MicroRecruitSettings(activity);
        img_workbg_isvip = (ImageView) view.findViewById(R.id.img_workbg_isvip);
        tv_type_isvip_loginwork = (TextView) view
                .findViewById(R.id.tv_type_isvip_loginwork);
        tv_work_nowgetcoins = (TextView) view
                .findViewById(R.id.tv_work_nowgetcoins);
        tv_work_nowgetcoins.setOnClickListener(this);
        tv_today_coins = (TextView) view.findViewById(R.id.tv_today_coins);
        tv_work_coins1 = (TextView) view.findViewById(R.id.tv_work_coins1);
        tv_work_coins2 = (TextView) view.findViewById(R.id.tv_work_coins2);
        tv_work_coins3 = (TextView) view.findViewById(R.id.tv_work_coins3);
        tv_work_coins4 = (TextView) view.findViewById(R.id.tv_work_coins4);
        continuous = (TextView) view.findViewById(R.id.continuous);
        getData();
        return view;
    }

    private void getData() {
        BmobQuery<coin> coins = new BmobQuery<>();
        coins.addWhereEqualTo("username", setting.phone.getValue());
        coins.findObjects(getActivity(), new FindListener<coin>() {
            @Override
            public void onSuccess(List<coin> list) {
                if (list.size() == 0) {
                    nowcount = "0";
                    count = "0";
                    addcount = "5";
                    tv_today_coins.setText("今日奖励5根辣条");
                    continuous.setText("您已签到:0天");
                    //如果没有创建表,则默认给值为0,然后创建
                    final coin coin1 = new coin();
                    coin1.setUsername(setting.phone.getValue());
                    coin1.setCoincount("0");
                    coin1.setCount("0");
                    coin1.save(getActivity(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            setting.OBJECT_IDBYCOIN.setValue(coin1.getObjectId());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    setting.OBJECT_IDBYCOIN.setValue(list.get(0).getObjectId());
                    //判断是否已经签到
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    sdf.format(date);
                    if (list.get(0).getUpdatedAt().startsWith(sdf.format(date).substring(0, 10)) && !list.get(0).getCount().equals("0")) {
                        //代表已经签到
                        issign = true;
                    } else {
                        issign = false;
                    }
                    nowcount = list.get(0).getCoincount();
                    count = list.get(0).getCount();
                    continuous.setText("您已签到:" + list.get(0).getCount() + "天");
                    if (Integer.parseInt(list.get(0).getCount()) <= 9) {
                        addcount = "5";

                        tv_today_coins.setText("今日奖励5根辣条");
                    } else if (Integer.parseInt(list.get(0).getCount()) <= 19) {
                        addcount = "10";
                        tv_today_coins.setText("今日奖励10根辣条");
                    } else if (Integer.parseInt(list.get(0).getCount()) <= 29) {
                        addcount = "15";
                        tv_today_coins.setText("今日奖励15根辣条");
                    } else {
                        addcount = "20";
                        tv_today_coins.setText("今日奖励20根辣条");
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_work_nowgetcoins:
                //保存
                if (!issign) {
                    coin coins = new coin();
                    int counts = Integer.parseInt(count) + 1;
                    coins.setCount(counts + "");
                    int overcount = Integer.parseInt(nowcount) + Integer.parseInt(addcount);
                    coins.setCoincount(overcount + "");
                    coins.update(getActivity(), setting.OBJECT_IDBYCOIN.getValue(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
//                            Toast.makeText(getActivity(), "成功签到", Toast.LENGTH_LONG).show();
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "今日已签到!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
