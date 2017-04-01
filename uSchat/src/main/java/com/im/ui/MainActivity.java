package com.im.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.im.base.BaseActivity;
import com.im.ui.fragment.ConversationFragment;
import com.im.util.IMMLeaks;
import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import de.greenrobot.event.Subscribe;

/**
 * @author :wanjianhua
 * @project:MainActivity
 * @date :2016-01-15-18:23
 */
public class MainActivity extends BaseActivity implements ObseverListener {

    private Button[] mTabs;
    private ConversationFragment conversationFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                Log.i("connect state:", status.getMsg());
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }

    @Override
    protected void initView() {
        super.initView();
//        mTabs = new Button[1];
//        mTabs[0] = btn_conversation;
//        mTabs[1] = btn_contact;
//        mTabs[0].setSelected(true);
        initTab();
    }

    private void initTab() {
        conversationFragment = new ConversationFragment();
//        linkmanFragment = new LinkManFragment();
        fragments = new Fragment[]{conversationFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, conversationFragment).hide(conversationFragment).show(conversationFragment).commit();
    }


    private void onTabIndex(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkRedPoint();
        //添加观察者-用于是否显示通知消息
        BmobNotificationManager.getInstance(this).addObserver(this);
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //移除观察者
        BmobNotificationManager.getInstance(this).removeObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
        //完全退出应用时需调用clearObserver来清除观察者
        BmobNotificationManager.getInstance(this).clearObserver();
    }

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        checkRedPoint();
    }

    private void checkRedPoint() {
        BaseApplication.getInstance().setUnreadallmessage(BmobIM.getInstance().getAllUnReadCount());
    }

}
