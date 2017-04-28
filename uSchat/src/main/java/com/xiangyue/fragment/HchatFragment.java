package com.xiangyue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.ui.SplashActivity;
import com.squareup.otto.Subscribe;
import com.xiangyue.act.FindRadarActivity;
import com.xiangyue.act.GuessinhActivity;
import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.coin;
import com.xiangyue.provider.UpdateUnreadEvent;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * chat
 * 2017年3月24日 13:23:58
 */
public class HchatFragment extends Fragment implements OnClickListener {
    @Bind(R.id.linear_lol)
    LinearLayout linearLol;
    @Bind(R.id.linear_king)
    LinearLayout linearKing;
    @Bind(R.id.Linear_video)
    LinearLayout LinearVideo;
    private LinearLayout chatcount;
    private TextView tv_unreadAllmessage;
    private LinearLayout startcp, startcg;
    SwipeRefreshLayout sw_refresh;
    private MicroRecruitSettings settings;
    private TextView tv_g;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.hchat_main, container, false);
        setTools();
        initView(container);
        if (!settings.OBJECT_IDBYCOIN.getValue().equals("")) {
            getCoin();
            getListByCoin(settings.OBJECT_IDBYCOIN.getValue());
        }
        //监听系统消息表
        getListBySys();
        ButterKnife.bind(this, container);
        return container;

    }

    private void getListBySys() {
        rtd_sys = new BmobRealTimeData();
        rtd_sys.start(getActivity(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                Log.e("xiangyue", "连接成功:" + rtd_sys.isConnected());
                if (rtd_sys.isConnected()) {
                    rtd_sys.subTableUpdate("systemtips");
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                Log.e("xiangyue", jsonObject.toString());
            }
        });
    }

    //监听coin表变化

    BmobRealTimeData rtd, rtd_sys;

    private void getListByCoin(final String objectId) {

        rtd = new BmobRealTimeData();
        rtd.start(getActivity(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                Log.e("xiangyue", "连接成功:" + rtd.isConnected());
                if (rtd.isConnected()) {
                    rtd.subRowUpdate("coin", objectId);
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                Log.e("xiangyue", jsonObject.toString());
                getCoin();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rtd.unsubRowUpdate("coin", settings.OBJECT_IDBYCOIN.getValue());
        rtd_sys.unsubTableUpdate("systemtips");
    }

    private void setTools() {
        settings = new MicroRecruitSettings(getActivity());
    }

    private void getCoin() {
        BmobQuery<coin> coins = new BmobQuery<>();
        coins.addWhereEqualTo("username", settings.phone.getValue());
        coins.findObjects(getActivity(), new FindListener<coin>() {
            @Override
            public void onSuccess(List<coin> list) {
                sw_refresh.setRefreshing(false);
                if (list.size() == 0) {
                    //如果没有创建表,则默认给值为0,然后创建
                    final coin coin1 = new coin();
                    coin1.setUsername(settings.phone.getValue());
                    coin1.setCoincount("0");
                    coin1.setCount("0");
                    coin1.save(getActivity(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            settings.OBJECT_IDBYCOIN.setValue(coin1.getObjectId());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        }
                    });
                    tv_g.setText("0辣条");
                } else {
                    settings.OBJECT_IDBYCOIN.setValue(list.get(0).getObjectId());
                    tv_g.setText(list.get(0).getCoincount() + "辣条");
                }
            }

            @Override
            public void onError(int i, String s) {
                sw_refresh.setRefreshing(false);
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initView(View conView) {
        tv_g = (TextView) conView.findViewById(R.id.tv_g);
        sw_refresh = (SwipeRefreshLayout) conView.findViewById(R.id.sw_refresh);
        startcg = (LinearLayout) conView.findViewById(R.id.startcg);
        startcg.setOnClickListener(this);
        startcp = (LinearLayout) conView.findViewById(R.id.startcp);
        startcp.setOnClickListener(this);
        chatcount = (LinearLayout) conView.findViewById(R.id.chatcount);
        chatcount.setOnClickListener(this);
        tv_unreadAllmessage = (TextView) conView.findViewById(R.id.tv_unreadAllmessage);
        if (BaseApplication.getInstance().getUnreadallmessage() > 0) {
            tv_unreadAllmessage.setVisibility(View.VISIBLE);
            tv_unreadAllmessage.setText(BaseApplication.getInstance().getUnreadallmessage() + "");
        } else {
            tv_unreadAllmessage.setVisibility(View.GONE);
        }
        sw_refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //下拉加载
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCoin();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BaseApplication.getInstance().getUnreadallmessage() > 0) {
            tv_unreadAllmessage.setVisibility(View.VISIBLE);
            tv_unreadAllmessage.setText(BaseApplication.getInstance().getUnreadallmessage() + "");
        } else {
            tv_unreadAllmessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.chatcount:
                intent.setClass(getActivity(), SplashActivity.class);
                startActivity(intent);
                break;
            case R.id.startcp:
                intent.setClass(getActivity(), FindRadarActivity.class);
                startActivity(intent);
                break;
            case R.id.startcg:
                intent.setClass(getActivity(), GuessinhActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onUpdateUnreadEvent(UpdateUnreadEvent event) {
        if (BaseApplication.getInstance().getUnreadallmessage() > 0) {
            tv_unreadAllmessage.setVisibility(View.VISIBLE);
            tv_unreadAllmessage.setText(BaseApplication.getInstance().getUnreadallmessage() + "");
        } else {
            tv_unreadAllmessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.linear_lol, R.id.linear_king, R.id.Linear_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_lol:
                break;
            case R.id.linear_king:
                break;
            case R.id.Linear_video:
                //koken:176F1-9E2B9-89EB3-99191
                break;
        }
    }
}
