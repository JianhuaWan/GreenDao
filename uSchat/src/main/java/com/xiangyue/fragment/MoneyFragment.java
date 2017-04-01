package com.xiangyue.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.act.MyMoneyActivity;
import com.xiangyue.act.OtherMoneyDetailActivity;
import com.xiangyue.act.R;
import com.xiangyue.adpter.GridAdapter;
import com.xiangyue.adpter.GridAdapter.OnGridItemClickListener;
import com.xiangyue.adpter.MoneyPhotosAdapter;
import com.xiangyue.pullrefresh.PullToRefreshBase;
import com.xiangyue.pullrefresh.PullToRefreshBase.OnRefreshListener2;
import com.xiangyue.pullrefresh.PullToRefreshListView;
import com.xiangyue.type.User;

import org.apache.http.util.EncodingUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 财富
 *
 * @author Administrator
 */
public class MoneyFragment extends Fragment implements OnClickListener {
    private View root;
    private ImageView img_memoney;
    private ListView listView;
    private PullToRefreshListView pullToRefreshListView;
    private Context context;
    private MoneyPhotosAdapter adapter;
    private List<User> list = new ArrayList<User>();
    private List<User> listHeader = new ArrayList<User>();
    private List<User> listmoney = new ArrayList<User>();
    BmobQuery<User> users = new BmobQuery<User>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        root = (ViewGroup) inflater.inflate(R.layout.money_main, container, false);
        context = getActivity();
        initHeaderView(root);
        initView(root);

        getDate();
        initEvetn();
        return root;
    }

    GridAdapter mAdapter;

    public void getDate() {

        users.findObjects(getActivity(), new FindListener<User>() {

            @Override
            public void onSuccess(List<User> arg0) {
                // TODO Auto-generated method stub
                pullToRefreshListView.onRefreshComplete();
                pullToRefreshListView.setVisibility(View.VISIBLE);
                pullToRefreshListView.getRefreshableView().setVisibility(View.VISIBLE);
                list = arg0;
                headerView.setVisibility(View.GONE);
                listView.setAdapter(mAdapter);
                try {
                    // JSONObject object = new
                    // JSONObject(getFromAssets("moneytest.txt"));
                    // list = gson.fromJson(object.getString("users"), new
                    // TypeToken<List<MoneyType>>() {
                    // }.getType());
                    if (list.size() > 0) {
                        addData(list);
                        for (int i = 3; i < list.size(); i++) {
                            listmoney.add(list.get(i));
                        }
                        adapter.setData(listmoney);
                    } else {
                        listView.removeHeaderView(headerView);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                pullToRefreshListView.onRefreshComplete();
                Toast.makeText(getActivity(), "失败:" + arg1, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initView(View root) {
        img_memoney = (ImageView) root.findViewById(R.id.img_memoney);
        img_memoney.setOnClickListener(this);
        pullToRefreshListView = (PullToRefreshListView) root.findViewById(R.id.pl_money);
        listView = pullToRefreshListView.getRefreshableView();
        listView.addHeaderView(headerView);
        if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            // 如果API版本高于9，则设置listview没有自带滚动结束后的反弹效果
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        adapter = new MoneyPhotosAdapter(context, list);
        mAdapter = new GridAdapter<MoneyPhotosAdapter>(context, adapter);
        mAdapter.setNumColumns(3);// 设置每行显示3个girl图片
        mAdapter.setOnItemClickListener(new OnGridItemClickListener() {
            @Override
            public void onItemClick(int pos, int realPos) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", list.get(realPos).getUsername());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), OtherMoneyDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void addData(List<User> getpTypes) {
        for (int i = 0; i < 3; i++) {
            listHeader.add(list.get(i));
        }
        refrushHeaderView();
    }

    private void refrushHeaderView() {
        if (listHeader.size() >= 1) {
            header.setVisibility(View.VISIBLE);
            tv_name_0.setText(1 + "." + listHeader.get(0).getNickName());
//            Picasso.with(context).load(listHeader.get(0).getHeadImage().getFileUrl(getActivity()))
//                    .placeholder(R.drawable.mini_avatar_shadow).error(R.drawable.mini_avatar_shadow)
//                    .into(img_girls_photo_0);
            ViewUtil.setPicture(context, listHeader.get(0).getHeadImage().getFileUrl(getActivity()), R.drawable.default_head, img_girls_photo_0, null);
        }
        if (listHeader.size() >= 2) {
            header.setVisibility(View.VISIBLE);
            header_1.setVisibility(View.VISIBLE);
            tv_name_1.setText(2 + "." + listHeader.get(1).getNickName());
            // loader.display(img_girls_photo_1, Urls.BASIC_URL +
            // listHeader.get(1).getHeadImage(), bmp1);
//            Picasso.with(context).load(listHeader.get(1).getHeadImage().getFileUrl(getActivity()))
//                    .placeholder(R.drawable.mini_avatar_shadow).error(R.drawable.mini_avatar_shadow)
//                    .into(img_girls_photo_1);
            ViewUtil.setPicture(context, listHeader.get(1).getHeadImage().getFileUrl(getActivity()), R.drawable.default_head, img_girls_photo_1, null);

        }
        if (listHeader.size() >= 3) {
            header_2.setVisibility(View.VISIBLE);
            header.setVisibility(View.VISIBLE);
            tv_name_2.setText(3 + "." + listHeader.get(2).getNickName());
            // loader.display(img_girls_photo_2, Urls.BASIC_URL
            // + listHeader.get(2).getHeadImage(), bmp1);

//            Picasso.with(context).load(listHeader.get(2).getHeadImage().getFileUrl(getActivity()))
//                    .placeholder(R.drawable.mini_avatar_shadow).error(R.drawable.mini_avatar_shadow)
//                    .into(img_girls_photo_2);
            ViewUtil.setPicture(context, listHeader.get(2).getHeadImage().getFileUrl(getActivity()), R.drawable.default_head, img_girls_photo_2, null);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.img_memoney:

                intent.setClass(getActivity(), MyMoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.header_0:
                intent.setClass(getActivity(), OtherMoneyDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.header_1:
                intent.setClass(getActivity(), OtherMoneyDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.header_2:
                intent.setClass(getActivity(), OtherMoneyDetailActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private View headerView;
    private RelativeLayout header;
    private FrameLayout header_1;
    private FrameLayout header_2;
    private FrameLayout header_0;
    private TextView tv_name_0;
    private ImageView img_girls_photo_0;
    private ImageView img_showonline_byshowgirl;
    private ImageView img_showusertype, img_two_usertype, img_two_isonline, img_girls_photo_1, img_three_usertype,
            img_three_isonline, img_girls_photo_2;
    private TextView tv_name_1, tv_name_2;

    private void initHeaderView(View view) {
        headerView = LayoutInflater.from(context).inflate(R.layout.moneyphotolist_header, null);
        header = (RelativeLayout) headerView.findViewById(R.id.header);
        header.setVisibility(View.GONE);
        // ///////////初始化第一个 girl的控件
        header_0 = (FrameLayout) headerView.findViewById(R.id.header_0);
        tv_name_0 = (TextView) headerView.findViewById(R.id.tv_name_0);
        img_girls_photo_0 = (ImageView) headerView.findViewById(R.id.img_girls_photo_0);
        header_0.setOnClickListener(this);
        img_showonline_byshowgirl = (ImageView) headerView.findViewById(R.id.img_showonline_byshowgirl);
        img_showusertype = (ImageView) headerView.findViewById(R.id.img_showusertype);
        // ///////////初始化第2个 girl的控件
        header_1 = (FrameLayout) headerView.findViewById(R.id.header_1);
        tv_name_1 = (TextView) headerView.findViewById(R.id.tv_name_1);
        img_two_usertype = (ImageView) headerView.findViewById(R.id.img_two_usertype);//
        img_two_isonline = (ImageView) headerView.findViewById(R.id.img_two_isonline);
        img_girls_photo_1 = (ImageView) headerView.findViewById(R.id.img_girls_photo_1);
        header_1.setOnClickListener(this);
        // ///////////初始化第3个 girl的控件
        header_2 = (FrameLayout) headerView.findViewById(R.id.header_2);
        tv_name_2 = (TextView) headerView.findViewById(R.id.tv_name_2);
        img_three_usertype = (ImageView) headerView.findViewById(R.id.img_three_usertype);
        img_three_isonline = (ImageView) headerView.findViewById(R.id.img_three_isonline);
        img_girls_photo_2 = (ImageView) headerView.findViewById(R.id.img_girls_photo_2);
        header_2.setOnClickListener(this);
    }

    public String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    private boolean refresh = true;
    private static int PageIndex = 1;

    public void initEvetn() {
        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                refresh = true;
                PageIndex = 1;
                list.clear();
                listHeader.clear();
                listmoney.clear();
                users.setLimit(12);
                getDate();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                refresh = false;
                PageIndex++;
                getDate();
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            }
        });
    }

}
