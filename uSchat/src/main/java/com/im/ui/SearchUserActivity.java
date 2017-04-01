package com.im.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.OnRecyclerViewListener;
import com.im.adapter.SearchUserAdapter;
import com.im.base.ParentWithNaviActivity;
import com.im.bean.User;
import com.im.model.BaseModel;
import com.im.model.UserModel;
import com.xiangyue.act.OtherInfoDetailActivity;
import com.xiangyue.act.R;
import com.xiangyue.base.MicroRecruitSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.listener.FindListener;

/**
 * 搜索好友
 *
 * @author :smile
 * @project:SearchUserActivity
 * @date :2016-01-25-18:23
 */
public class SearchUserActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_find_name)
    EditText et_find_name;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    @Bind(R.id.btn_search)
    ImageView btn_search;
    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    LinearLayoutManager layoutManager;
    SearchUserAdapter adapter;
    List<User> usernick = new ArrayList<User>();

    @Override
    protected String title() {
        return "添加好友";
    }

    private MicroRecruitSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        initNaviView();
        settings = new MicroRecruitSettings(this);
        adapter = new SearchUserAdapter();
        layoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(layoutManager);
        rc_view.setAdapter(adapter);
        sw_refresh.setEnabled(true);
        sw_refresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });


        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("userid", usernick.get(position).getUsername());
                intent.putExtra("objectId", usernick.get(position).getObjectId());
                intent.setClass(SearchUserActivity.this, OtherInfoDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return true;
            }
        });
        et_find_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    sw_refresh.setRefreshing(true);
                    query();
                }
                return false;
            }

        });
    }

    String name;

    @OnClick(R.id.btn_search)
    public void onSearchClick(View view) {
        sw_refresh.setRefreshing(true);
        query();
    }

    public void query() {
        name = et_find_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast("请输入ID");
            sw_refresh.setRefreshing(false);
            return;
        }
        UserModel.getInstance().queryUsers(name, BaseModel.DEFAULT_LIMIT, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                usernick = list;
                sw_refresh.setRefreshing(false);
                adapter.setDatas(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
//                sw_refresh.setRefreshing(false);
//                List<User> list = new ArrayList<User>();
//                adapter.setDatas(list);
//                adapter.notifyDataSetChanged();
//                toast(s + "(" + i + ")");
                //再次查询
                queryit();
            }
        });


    }

    private void queryit() {
        UserModel.getInstance().queryUsersByNick(name, BaseModel.DEFAULT_LIMIT, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                usernick = list;
                sw_refresh.setRefreshing(false);
                adapter.setDatas(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                sw_refresh.setRefreshing(false);
                List<User> list = new ArrayList<User>();
                adapter.setDatas(list);
                adapter.notifyDataSetChanged();
                toast(s + "(" + i + ")");
            }
        });
    }

//    @Subscribe
//    public void onEventMainThread(ChatEvent event) {
//        BmobIMUserInfo info = event.info;
//        //如果需要更新用户资料，开发者只需要传新的info进去就可以了
//        BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
//            @Override
//            public void done(BmobIMConversation c, BmobException e) {
//                if (e == null) {
//                    Bundle bundle = new Bundle();
//                    c.setConversationTitle(usernick.get(0).getNickName());
//                    bundle.putSerializable("c", c);
//                    bundle.putString("nickName", usernick.get(0).getNickName());
//                    startActivity(ChatActivity.class, bundle, false);
//                } else {
//                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
//                }
//            }
//        });
//    }

}
