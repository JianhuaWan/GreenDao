package com.im.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.im.adapter.LinkManAdapter;
import com.im.adapter.OnRecyclerViewListener;
import com.im.base.ParentWithNaviActivity;
import com.im.base.ParentWithNaviFragment;
import com.im.ui.ChatActivity;
import com.im.ui.SearchUserActivity;
import com.xiangyue.act.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 会话界面
 *
 * @author :smile
 * @project:ConversationFragment
 * @date :2016-01-25-18:23
 */
public class LinkManFragment extends ParentWithNaviFragment {

    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    LinkManAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected String title() {
        return "联系人";
    }

//    @Override
//    public Object right() {
//        return R.drawable.base_action_bar_add_bg_selector;
//    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                getActivity().finish();
            }

            @Override
            public void clickRight() {
                startActivity(SearchUserActivity.class, null);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_linkman, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        adapter = new LinkManAdapter();
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);
        setListener();
        return rootView;
    }

    private void setListener() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                sw_refresh.setRefreshing(true);
                query();
            }
        });
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                BmobIMConversation c = adapter.getItem(position);
                bundle.putSerializable("c", c);
                startActivity(ChatActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(int position) {
                //以下两种方式均可以删除会话
//                BmobIM.getInstance().deleteConversation(adapter.getItem(position).getConversationId());
//                BmobIM.getInstance().deleteConversation(adapter.getItem(position));
//                adapter.remove(position);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sw_refresh.setRefreshing(true);
        query();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * 查询本地会话
     */
    public void query() {
        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.notifyDataSetChanged();
        sw_refresh.setRefreshing(false);
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        //重新刷新列表
        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.notifyDataSetChanged();
    }

    /**
     * 注册消息接收事件
     *
     * @param event 1、与用户相关的由开发者自己维护，SDK内部只存储用户信息
     *              2、开发者获取到信息后，可调用SDK内部提供的方法更新会话
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        //重新获取本地消息并刷新列表
        adapter.bindDatas(BmobIM.getInstance().loadAllConversation());
        adapter.notifyDataSetChanged();
    }

    public void back(View view) {
        getActivity().finish();
    }
}
