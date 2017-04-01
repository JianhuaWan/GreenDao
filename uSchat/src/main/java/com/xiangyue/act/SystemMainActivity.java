package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.xiangyue.adpter.SystemTipsAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.bean.systemtips;
import com.xiangyue.pullrefresh.PullToRefreshBase;
import com.xiangyue.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class SystemMainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private SystemTipsAdapter adapter;
    private List<systemtips> temp = new ArrayList<>();
    private List<systemtips> firstdata = new ArrayList<systemtips>();
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private Context context;


    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.system_tips);
        context = this;
        initView();
        getData();
        initEvetn();
    }

    private void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pl_hsystemmessage);
        listView = pullToRefreshListView.getRefreshableView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            // 如果API版本高于9，则设置listview没有自带滚动结束后的反弹效果
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        adapter = new SystemTipsAdapter(context, temp);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    BmobQuery<systemtips> query = new BmobQuery<>();

    public void getData() {
        if (refresh) {
            query.setSkip(0);
            query.setLimit(12);
        }
        query.findObjects(SystemMainActivity.this, new FindListener<systemtips>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (pullToRefreshListView != null) {
                            pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                        }
                    }

                    @Override
                    public void onSuccess(List<systemtips> arg0) {
                        // TODO Auto-generated method stub
                        if (isload.equals("0")) {
                            firstdata.addAll(arg0);
                        }
                        if (pullToRefreshListView == null) {
                            return;
                        }
                        pullToRefreshListView.setVisibility(View.VISIBLE);
                        pullToRefreshListView.getRefreshableView()
                                .setVisibility(View.VISIBLE);
                        pullToRefreshListView.onRefreshComplete();
                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

                        try {
                            if (arg0.size() > 0) {
                                if (refresh) {
                                    temp.clear();
                                    temp.addAll(firstdata);
                                } else {
                                    temp.addAll(arg0);
                                }

                                if (refresh) {
                                    if (firstdata.size() == 0) {
                                        pullToRefreshListView.setVisibility(View.GONE);
                                        return;
                                    }
                                    if (firstdata.size() < 12) {
                                        // 不可加载更多
                                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else if (firstdata.size() == 12) {
                                        // 可以加载更多
                                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                } else {
                                    if (arg0.size() == 0) {
                                        pullToRefreshListView.setVisibility(View.GONE);
                                        return;
                                    }
                                    if (arg0.size() < 12) {
                                        // 不可加载更多
                                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                                    } else if (arg0.size() == 12) {
                                        // 可以加载更多
                                        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                                    }
                                }

                                adapter.setDate(SystemMainActivity.this, temp);
                                listView.setSelection(PageIndex * 12 - 12);
                            } else {
                                Toast.makeText(SystemMainActivity.this, "暂无系统消息", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(SystemMainActivity.this, arg1, Toast.LENGTH_LONG).show();
                        pullToRefreshListView.onRefreshComplete();
                        if (!refresh) {
                            PageIndex--;
                            pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                        } else {
                            pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        }
                        Toast.makeText(getActivity(), arg1, Toast.LENGTH_LONG).show();
                    }
                }

        );

    }

    public void back(View view) {
        finish();
    }

    private boolean refresh = true;
    private static int PageIndex = 1;
    private static String isload = "0";

    public void initEvetn() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                refresh = true;
                PageIndex = 1;
                isload = "1";
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                refresh = false;
                isload = "1";
                PageIndex++;
                if (PageIndex == 2) {
                    query.setLimit(12);
                    query.setSkip(12);
                } else if (PageIndex == 3) {
                    query.setLimit(12);
                    query.setSkip(24);
                } else if (PageIndex == 4) {
                    query.setLimit(12);
                    query.setSkip(36);
                } else if (PageIndex == 5) {
                    query.setLimit(12);
                    query.setSkip(48);
                } else if (PageIndex == 6) {
                    query.setLimit(12);
                    query.setSkip(60);
                } else if (PageIndex == 7) {
                    query.setLimit(12);
                    query.setSkip(72);
                } else if (PageIndex == 8) {
                    query.setLimit(12);
                    query.setSkip(84);
                } else if (PageIndex == 9) {
                    query.setLimit(12);
                    query.setSkip(96);
                } else if (PageIndex == 10) {
                    query.setLimit(12);
                    query.setSkip(108);
                } else {
                    Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_LONG).show();
                    return;
                }
                getData();
                String label = DateUtils.formatDateTime(getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        systemtips sys = adapter.getItem(i - 1);
        Intent intent = new Intent();
        intent.setClass(SystemMainActivity.this, SystemContentActivity.class);
        intent.putExtra("content", sys.getContent());
        intent.putExtra("time", sys.getCreatedAt().toString());
        startActivity(intent);
    }

}
