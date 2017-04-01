package com.xiangyue.act;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.adpter.CommontAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleCommonBean;
import com.xiangyue.pullrefresh.PullToRefreshBase;
import com.xiangyue.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wWX321637 on 2016/5/12.
 */
public class CommontActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private PullToRefreshListView pullToRefreshListView;
    private Context context;

    private ScrollView linear_commont;
    private CommontAdapter adapter;
    private List<CircleCommonBean> lists = new ArrayList<>();
    private List<CircleCommonBean> firstdata = new ArrayList<>();
    private ImageView img_edit;
    private TextView tv_submit;
    private EditText ed_content;
    private String uuid;
    private LinearLayout liner_nodata;
    private MicroRecruitSettings settings;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.commont_main);
        context = this;
        initTools();
        initView();
        //获取评论列表
        getCommonList();
        initEvetn();
    }

    private void initTools() {
        settings = new MicroRecruitSettings(CommontActivity.this);
        uuid = getIntent().getStringExtra("uuid");
    }

    BmobQuery<CircleCommonBean> circleCommonBeanBmobQuery = new BmobQuery<>();

    private void getCommonList() {
        circleCommonBeanBmobQuery.order("-createdAt");
        circleCommonBeanBmobQuery.addWhereEqualTo("uuid", uuid);
        if (refresh) {
            circleCommonBeanBmobQuery.setSkip(0);
            circleCommonBeanBmobQuery.setLimit(10);
        }
        if (isload.equals("0")) {
            circleCommonBeanBmobQuery.setSkip(0);
            circleCommonBeanBmobQuery.setLimit(10);
        }
        circleCommonBeanBmobQuery.findObjects(CommontActivity.this, new FindListener<CircleCommonBean>() {
            @Override
            public void onStart() {
                super.onStart();
                if (pullToRefreshListView != null) {
                    pullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                }
            }

            @Override
            public void onSuccess(List<CircleCommonBean> list) {
                if (isload.equals("0")) {
                    if (firstdata.size() != 0) {
                        firstdata.clear();
                    }
                    firstdata.addAll(list);
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
                    if (list.size() > 0) {
                        if (refresh) {
                            lists.clear();
                            lists.addAll(list);
                        } else {
                            lists.addAll(list);
                        }

                        if (refresh) {
                            if (firstdata.size() == 0) {
                                pullToRefreshListView.setVisibility(View.GONE);
                                return;
                            }
                            if (firstdata.size() < 10) {
                                // 不可加载更多
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else if (firstdata.size() == 10) {
                                // 可以加载更多
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        } else {
                            if (list.size() == 0) {
                                pullToRefreshListView.setVisibility(View.GONE);
                                return;
                            }
                            if (list.size() < 10) {
                                // 不可加载更多
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            } else if (list.size() == 10) {
                                // 可以加载更多
                                pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                            }
                        }

                        adapter.setData(lists);
                        listView.setSelection(PageIndex * 10 - 10);
                    } else {
//                        Toast.makeText(getActivity(), "暂无动态", Toast.LENGTH_LONG).show();
                        if (PageIndex == 1) {
                            liner_nodata.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(int i, String s) {
                pullToRefreshListView.onRefreshComplete();
                Toast.makeText(CommontActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        liner_nodata = (LinearLayout) findViewById(R.id.liner_nodata);
        ed_content = (EditText) findViewById(R.id.ed_content);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        img_edit = (ImageView) findViewById(R.id.img_edit);
        img_edit.setOnClickListener(this);
        linear_commont = (ScrollView) findViewById(R.id.linear_commont);
        linear_commont.setVisibility(View.GONE);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pl_commonlist);
        listView = pullToRefreshListView.getRefreshableView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            // 如果API版本高于9，则设置listview没有自带滚动结束后的反弹效果
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        adapter = new CommontAdapter(context, lists);
        listView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }

    private boolean refresh;
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
                getCommonList();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                refresh = false;
                PageIndex++;
                isload = "1";
                circleCommonBeanBmobQuery.setLimit(10);
                circleCommonBeanBmobQuery.setSkip(10 * (PageIndex - 1));
                getCommonList();
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            }
        });
    }

    private String commonName;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_edit:
                if (linear_commont.getVisibility() == View.VISIBLE) {
                    linear_commont.setVisibility(View.GONE);
                } else {
                    linear_commont.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_submit:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (ed_content.getText().toString().trim().equals("")) {
                    Toast.makeText(CommontActivity.this, "请输入评论内容", Toast.LENGTH_LONG).show();
                    return;
                }
                //提交
                BmobQuery<com.xiangyue.type.User> userBmobQuery = new BmobQuery<>();
                userBmobQuery.addWhereEqualTo("username", settings.phone.getValue());
                userBmobQuery.findObjects(CommontActivity.this, new FindListener<com.xiangyue.type.User>() {
                    @Override
                    public void onSuccess(final List<com.xiangyue.type.User> list) {
                        commonName = list.get(0).getNickName();
                        CircleCommonBean circleCommonBean = new CircleCommonBean();
                        circleCommonBean.setCommoncontent(ed_content.getText().toString());
                        circleCommonBean.setConmonpeoson(commonName);
                        circleCommonBean.setUuid(uuid);
                        circleCommonBean.setCommonhead(settings.HEADICON.getValue());
                        circleCommonBean.save(CommontActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                //评论成功
                                ed_content.setText("");
                                liner_nodata.setVisibility(View.GONE);
                                linear_commont.setVisibility(View.GONE);
                                if (lists.size() != 0) {
                                    lists.clear();
                                }
                                PageIndex = 1;
                                refresh = true;
                                getCommonList();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(CommontActivity.this, s, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(CommontActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            default:
                break;
        }
    }
}

