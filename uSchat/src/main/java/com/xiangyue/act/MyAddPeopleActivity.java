package com.xiangyue.act;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.im.base.BaseActivity;
import com.im.bean.User;
import com.im.model.BaseModel;
import com.im.model.UserModel;
import com.im.ui.ChatActivity;
import com.xiangyue.adpter.AttentionAdapter;
import com.xiangyue.pullrefresh.PullToRefreshBase;
import com.xiangyue.pullrefresh.PullToRefreshListView;
import com.xiangyue.weight.LoginDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wWX321637 on 2016/4/25.
 */
public class MyAddPeopleActivity extends BaseActivity {
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private AttentionAdapter adapter;
    private List<com.xiangyue.type.User> lists = new ArrayList<>();
    private Context context;
    private LinearLayout liner_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaddpeople_main);
        context = this;
        initViews();
        setUpData();
        getDate();
        initEvetn();
    }

    BmobQuery<com.xiangyue.type.User> userBmobQuery = new BmobQuery<>();

    private void getDate() {
        userBmobQuery.addWhereContainedIn("username", arrayList);
        userBmobQuery.findObjects(this, new FindListener<com.xiangyue.type.User>() {
            @Override
            public void onStart() {
                super.onStart();
                //查询数据
                if (dialog != null)
                    dialog.show();
                if (pullToRefreshListView != null) {
                    pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                }
            }

            @Override
            public void onSuccess(List<com.xiangyue.type.User> list) {
                dialog.dismiss();

                if (pullToRefreshListView == null) {
                    return;
                }
                pullToRefreshListView.setVisibility(View.VISIBLE);
                pullToRefreshListView.getRefreshableView()
                        .setVisibility(View.VISIBLE);
                pullToRefreshListView.onRefreshComplete();
                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                if (lists.size() != 0) {
                    lists.clear();
                }
                lists.addAll(list);
                adapter.setData(lists);
            }


            @Override
            public void onError(int i, String s) {
                dialog.dismiss();
                Toast.makeText(MyAddPeopleActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<String> arrayList = new ArrayList<>();

    private void setUpData() {

        arrayList = getIntent().getStringArrayListExtra("attentionlist");
        if (arrayList.size() == 0) {
            liner_nodata.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            pullToRefreshListView.setVisibility(View.GONE);
        } else {
            pullToRefreshListView.setVisibility(View.VISIBLE);
            liner_nodata.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);


        }
    }

    LoginDialog dialog;

    private void initViews() {
        liner_nodata = (LinearLayout) findViewById(R.id.liner_nodata);
        dialog = new LoginDialog(MyAddPeopleActivity.this, "");
        dialog.setCanceledOnTouchOutside(false);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pl_attention);

        listView = pullToRefreshListView.getRefreshableView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            // 如果API版本高于9，则设置listview没有自带滚动结束后的反弹效果
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        adapter = new AttentionAdapter(context, lists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                com.xiangyue.type.User user = adapter.getItem(i - 1);
                UserModel.getInstance().queryUsers(user.getUsername(), BaseModel.DEFAULT_LIMIT, new FindListener<User>() {
                    @Override
                    public void onSuccess(final List<User> list) {
                        BmobIMUserInfo info = new BmobIMUserInfo(list.get(0).getObjectId(), list.get(0).getUsername(), list.get(0).getHeadImage().getFileUrl(context));
                        BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
                            @Override
                            public void done(BmobIMConversation c, BmobException e) {
                                if (e == null) {
                                    Bundle bundle = new Bundle();
                                    c.setConversationTitle(list.get(0).getNickName());
                                    bundle.putSerializable("c", c);
                                    bundle.putString("nickName", list.get(0).getNickName());
                                    startActivity(ChatActivity.class, bundle, false);
                                } else {
                                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {
                        toast(s + "(" + i + ")");
                    }
                });

            }
        });
    }


    public void initEvetn() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                getDate();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                String label = DateUtils.formatDateTime(MyAddPeopleActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);


            }
        });
    }

    public void back(View view) {
        finish();
    }
}
