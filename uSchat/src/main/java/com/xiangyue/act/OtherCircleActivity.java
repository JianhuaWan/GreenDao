package com.xiangyue.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.xiangyue.adpter.CircleAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleBean;
import com.xiangyue.pullrefresh.PullToRefreshBase;
import com.xiangyue.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wWX321637 on 2016/5/11.
 */
public class OtherCircleActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_circle;
    private SwipyRefreshLayout swipyRefreshLayout;
    private ListView listView;
    private View headerView;
    private Context context;
    private CircleAdapter adapter;
    private List<CircleBean> lists = new ArrayList<CircleBean>();
    private ImageView img_write;
    private LinearLayout linear_byme;
    private boolean refresh;
    private static int PageIndex = 1;
    private MicroRecruitSettings settings;
    private boolean isme;
    private List<CircleBean> firstdata = new ArrayList<CircleBean>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.other_circle_main);
        context = this;
        initTools();
        initView();
        initEvetn();
        getCircleData();
//        initBro();
    }

    public void initBro() {
        ReceiverByCircle bReceivercircle = new ReceiverByCircle();
        IntentFilter intentFilter1 = new IntentFilter("changecircle");
        getActivity().registerReceiver(bReceivercircle, intentFilter1);
    }

    public class ReceiverByCircle extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            refresh = true;
            PageIndex = 1;
            isload = "1";
            getCircleData();
        }
    }

    private void initTools() {
        settings = new MicroRecruitSettings(context);

    }

    private static String isload = "0";

    public void initEvetn() {
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    refresh = true;
                    PageIndex = 1;
                    isload = "1";
                    getCircleData();
                } else {
                    refresh = false;
                    PageIndex++;
                    isload = "1";
                    query.setLimit(10);
                    query.setSkip(PageIndex * 10 - 10);
                    getCircleData();
                }
            }
        });
    }

    BmobQuery<CircleBean> query = new BmobQuery<>();

    private void getCircleData() {
        query.order("-createdAt");
        query.include("username");
        if (refresh) {
            query.setSkip(0);
            query.setLimit(10);
        }
        if (isload.equals("0")) {
            query.setSkip(0);
            query.setLimit(10);
        }
        if (isme) {
            query.addWhereEqualTo("username", settings.OBJECT_ID.getValue());
        } else {
            query.addWhereEqualTo("username", getIntent().getStringExtra("objectId"));
        }
        query.findObjects(getActivity(), new FindListener<CircleBean>() {
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(List<CircleBean> list) {
                //成功后查询出相对应的图片
                swipyRefreshLayout.setRefreshing(false);
                if (isload.equals("0")) {
                    if (firstdata.size() != 0) {
                        firstdata.clear();
                    }
                    firstdata.addAll(list);
                }

                try {
                    if (list.size() > 0) {

                        if (refresh) {
                            lists.clear();
                            lists.addAll(list);
                        } else {
                            lists.addAll(list);
                        }
                        adapter.setData(OtherCircleActivity.this, lists);
                    } else {
                        Toast.makeText(getActivity(), "暂无更多动态", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int i, String s) {
                swipyRefreshLayout.setRefreshing(false);
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private ImageView myheadIcon, img_bg;

    private void initView() {
        tv_circle = (TextView) findViewById(R.id.tv_circle);
        listView = (ListView) findViewById(R.id.pl_showcircle_see);
        swipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        headerView = LayoutInflater.from(context).inflate(R.layout.circle_top, null);
        myheadIcon = (ImageView) headerView.findViewById(R.id.myheadIcon);
        myheadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();

                if (getIntent().getStringExtra("type").equals("me")) {
                    intent2.putExtra("headIcon", settings.HEADICON.getValue().toString());
                } else {
                    intent2.putExtra("headIcon", getIntent().getStringExtra("othericon"));
                }
                intent2.setClass(OtherCircleActivity.this, QueryHeadActivity.class);
                startActivity(intent2);
            }
        });
        img_bg = (ImageView) headerView.findViewById(R.id.img_bg);
        img_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView.addHeaderView(headerView);
        img_write = (ImageView) headerView.findViewById(R.id.img_write);
        linear_byme = (LinearLayout) headerView.findViewById(R.id.linear_byme);


        img_write.setOnClickListener(this);
        if (getIntent().getStringExtra("type").equals("me")) {
            isme = true;
            tv_circle.setText("我的圈子");
            ViewUtil.setPicture(context, settings.HEADICON.getValue(), R.drawable.default_head, myheadIcon, null);
            if (!getIntent().getStringExtra("otherbg").equals("0")) {
                ViewUtil.setPicture(context, getIntent().getStringExtra("otherbg"), R.drawable.default_head, img_bg, null);
            }
            linear_byme.setVisibility(View.VISIBLE);
        } else {
            isme = false;
            ViewUtil.setPicture(context, getIntent().getStringExtra("othericon"), R.drawable.default_head, myheadIcon, null);
            tv_circle.setText(getIntent().getStringExtra("othername"));
            if (!getIntent().getStringExtra("otherbg").equals("0")) {
                ViewUtil.setPicture(context, getIntent().getStringExtra("otherbg"), R.drawable.default_head, img_bg, null);
            }
            linear_byme.setVisibility(View.GONE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            // 如果API版本高于9，则设置listview没有自带滚动结束后的反弹效果
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        adapter = new CircleAdapter(context, lists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CircleBean circleBean = adapter.getItem(i - 2);
                Intent intent = new Intent();
                intent.putStringArrayListExtra("circlebean", (ArrayList<String>) circleBean.getPhotos());
                intent.putExtra("title", circleBean.getContent());
                intent.putExtra("objectId", circleBean.getUsername().getObjectId());
                intent.putExtra("username", circleBean.getUsername().getUsername());
                intent.putExtra("uuid", circleBean.getUuid());
                intent.putExtra("cirObjectId", circleBean.getObjectId());
                intent.setClass(getActivity(), CircleInfoActivity.class);
                startActivity(intent);


            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.img_write:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), WriteCircleActivity.class);
                getActivity().startActivity(intent1);
                finish();
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }


}
