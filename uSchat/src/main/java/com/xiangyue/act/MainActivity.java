package com.xiangyue.act;

/**
 * 主界面
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.im.model.UserModel;
import com.xiangyue.adpter.MainPageAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.updateversion;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.service.LocationService;
import com.xiangyue.tabpage.TabPageIndicator;
import com.xiangyue.type.User;
import com.xiangyue.util.SysApplication;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends BaseActivity {
    @SuppressWarnings("unused")
    private MicroRecruitSettings settings;
    private Context context;
    private MainPageAdapter pageAdapter;
    private ViewPager pager;
    private TabPageIndicator indicator;
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    public static void setInstance(MainActivity instance) {
        MainActivity.instance = instance;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        SysApplication.getInstance().addActivity(this);
        instance = this;
        BusProvider.getInstance().register(this);
        context = this;
        setContentView(R.layout.helper_main);
        settings = new MicroRecruitSettings(context);
        init();
        initLoc();
        AddLisenler();
        if (settings.UPDATE_TIPS.getValue().equals("")) {
            checkUpdate();
        }
        //链接im
        connectIM();
    }

    private void connectIM() {
        //连接服务器
        com.im.bean.User user = UserModel.getInstance().getCurrentUser();
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                } else {
                }
            }
        });
    }


    private void checkUpdate() {
        //获取版本
        BmobQuery<updateversion> updateversionBmobQuery = new BmobQuery<>();
        updateversionBmobQuery.findObjects(MainActivity.this, new FindListener<updateversion>() {
            @Override
            public void onSuccess(List<updateversion> list) {
                int servletversion = Integer.parseInt(list.get(0).getVersionname().replace(".", ""));
                int currentversion = Integer.parseInt(getVersion().replace(".", ""));
                if (servletversion > currentversion) {
                    //有更新
                    Intent intent = new Intent();
                    intent.putExtra("versionname", list.get(0).getVersionname());
                    intent.putExtra("versioncode", list.get(0).getVersioncode());
                    intent.putExtra("updateinfo", list.get(0).getUpdateinfo());
                    intent.putExtra("apkurl", list.get(0).getApk().getFileUrl(MainActivity.this));
                    Log.e("apkurl", list.get(0).getApk().getFileUrl(MainActivity.this));
                    intent.setClass(MainActivity.this, UpdateActivity.class);
                    startActivity(intent);
                }
                Log.e("update", getVersion());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void AddLisenler() {
        Receiver bReceiver = new Receiver();
//        IntentFilter intentFilter = new IntentFilter("changeIcon");
//        getActivity().registerReceiver(bReceiver, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("finishActivity");
        getActivity().registerReceiver(bReceiver, intentFilter1);

    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            SysApplication.getInstance().exit();
        }
    }


    public void init() {

        indicator = (TabPageIndicator) findViewById(R.id.indicator_main);
        pager = (ViewPager) findViewById(R.id.pager_main);
        pager.setOffscreenPageLimit(3);
        pageAdapter = new MainPageAdapter(getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        indicator.setViewPager(pager);
    }

    // 判断是否退出
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    // 按键响应事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 判断是否退出
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            //提交位置
            SysApplication.getInstance().exit();
        }
    }

    private LocationService locationService;

    private void initLoc() {
        // TODO Auto-generated method stub
        // -----------location config ------------
        locationService = ((BaseApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */


                User user = new User();
                if (location.getCity() == null) {
                    user.setCity("未知地址");
                } else {
                    user.setCity(location.getCity().toString());
                }
                user.update(MainActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(int i, String s) {
                    }
                });

                if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(getActivity(), "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(getActivity(), "网络不同导致定位失败，请检查网络是否通畅", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(getActivity(), "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机", Toast.LENGTH_LONG).show();
                }
            }
        }

    };
}
