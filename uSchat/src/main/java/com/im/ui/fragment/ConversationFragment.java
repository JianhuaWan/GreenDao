package com.im.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.im.adapter.ConversationAdapter;
import com.im.adapter.OnRecyclerViewListener;
import com.im.base.BaseFragment;
import com.im.ui.ChatActivity;
import com.im.ui.SearchUserActivity;
import com.xiangyue.act.R;
import com.xiangyue.weight.AlertDialog;

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
public class ConversationFragment extends BaseFragment {

    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    ConversationAdapter adapter;
    LinearLayoutManager layoutManager;
    private View rootView;
    @Bind(R.id.img_search)
    ImageView img_search;
    @Bind(R.id.liner_back)
    LinearLayout liner_back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conversation, container, false);
        ButterKnife.bind(this, rootView);
        adapter = new ConversationAdapter();
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
        sw_refresh.setEnabled(true);
        setListener();
        return rootView;
    }


    private void setListener() {
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchUserActivity.class);
                startActivity(intent);
            }
        });
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
                bundle.putString("nickName", c.getConversationTitle());
                startActivity(ChatActivity.class, bundle);
            }

            @Override
            public boolean onItemLongClick(final int position) {
                //以下两种方式均可以删除会话
//                BmobIM.getInstance().deleteConversation(adapter.getItem(position).getConversationId());
                new AlertDialog(getActivity()).builder().setTitle("删除聊天消息")
                        .setPositiveButton("确认删除", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BmobIM.getInstance().deleteConversation(adapter.getItem(position));
                                adapter.remove(position);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();

                return true;
            }
        });
        liner_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
}
