package com.xiangyue.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.xiangyue.act.OtherInfoDetailActivity;
import com.xiangyue.act.R;
import com.xiangyue.adpter.GridAdapter;
import com.xiangyue.adpter.GridAdapter.OnGridItemClickListener;
import com.xiangyue.adpter.MoneyPhotosAdapter;
import com.xiangyue.type.User;
import com.xiangyue.weight.LoginDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * show
 *
 * @author Administrator
 */
public class HshowtopFragment extends Fragment implements OnClickListener {
    private View root;
    private ListView listView;
    private SwipyRefreshLayout swipyrefreshlayout;
    private Context context;
    private MoneyPhotosAdapter adapter;
    private List<User> list = new ArrayList<User>();
    private List<User> listHeader = new ArrayList<User>();
    private List<User> listmoney = new ArrayList<User>();
    private List<User> firstdata = new ArrayList<User>();
    BmobQuery<User> users = new BmobQuery<User>();
    private TextView img_seriout;
    private RelativeLayout rel_topmeoney;
    LoginDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        root = (ViewGroup) inflater.inflate(R.layout.hshowtop_main, container, false);
        context = getActivity();
        initHeaderView(root);
        initView(root);
        getDate();
        initEvetn();
        return root;
    }


    GridAdapter mAdapter;

    public void getDate() {
        if (refresh) {
            users.setLimit(12);
        }
        users.findObjects(getActivity(), new FindListener<User>() {
            @Override
            public void onStart() {
                super.onStart();
                if (listmoney.size() == 0) {
                    dialog.show();
                }
            }

            @Override
            public void onSuccess(List<User> arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                // TODO Auto-generated method stub
                swipyrefreshlayout.setRefreshing(false);
                if (isload.equals("0")) {
                    firstdata.addAll(arg0);
                }
                if (isload.equals("1") && refresh) {
                    list = firstdata;
                } else {
                    list = arg0;
                }
                try {
                    if (list.size() > 0) {

                        if (refresh) {
                            listmoney.clear();
                            addData(list);
                            for (int i = 3; i < list.size(); i++) {
                                listmoney.add(list.get(i));
                            }
                        } else {
                            listmoney.addAll(list);
                        }
                        adapter.setData(listmoney);
                    } else {
                        Toast.makeText(getActivity(), "暂无更多", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                swipyrefreshlayout.setRefreshing(false);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(getActivity(), arg1, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initView(View root) {
        rel_topmeoney = (RelativeLayout) root.findViewById(R.id.rel_topmeoney);
        img_seriout = (TextView) root.findViewById(R.id.img_seriout);
        img_seriout.setOnClickListener(this);
        listView = (ListView) root.findViewById(R.id.pl_hshow);
        swipyrefreshlayout = (SwipyRefreshLayout) root.findViewById(R.id.swipyrefreshlayout);
        swipyrefreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        listView.addHeaderView(headerView);
        adapter = new MoneyPhotosAdapter(context, list);
        mAdapter = new GridAdapter<MoneyPhotosAdapter>(context, adapter);
        mAdapter.setNumColumns(3);// 设置每行显示3个girl图片
        mAdapter.setOnItemClickListener(new OnGridItemClickListener() {
            @Override
            public void onItemClick(int pos, int realPos) {
                Intent intent = new Intent();
                intent.putExtra("userid", listmoney.get(realPos).getUsername());
                intent.putExtra("objectId", listmoney.get(realPos).getObjectId());
                intent.setClass(getActivity(), OtherInfoDetailActivity.class);
                startActivity(intent);
            }
        });
        headerView.setVisibility(View.GONE);
        listView.setAdapter(mAdapter);
        dialog = new LoginDialog(getActivity(), "");
        dialog.setCanceledOnTouchOutside(false);
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
            tv_name_0.setText(listHeader.get(0).getNickName());
            ViewUtil.setPicture(context, listHeader.get(0).getHeadImage().getFileUrl(getActivity()), R.drawable.default_head, img_girls_photo_0, null);
        }
        if (listHeader.size() >= 2) {
            header.setVisibility(View.VISIBLE);
            header_1.setVisibility(View.VISIBLE);
            tv_name_1.setText(listHeader.get(1).getNickName());
            ViewUtil.setPicture(context, listHeader.get(1).getHeadImage().getFileUrl(getActivity()), R.drawable.default_head, img_girls_photo_1, null);

        }
        if (listHeader.size() >= 3) {
            header_2.setVisibility(View.VISIBLE);
            header.setVisibility(View.VISIBLE);
            tv_name_2.setText(listHeader.get(2).getNickName());
            ViewUtil.setPicture(context, listHeader.get(2).getHeadImage().getFileUrl(getActivity()), R.drawable.default_head, img_girls_photo_2, null);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.header_0:
                intent.putExtra("userid", listHeader.get(0).getUsername());
                intent.putExtra("objectId", listHeader.get(0).getObjectId());
                intent.setClass(getActivity(), OtherInfoDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.header_1:
                intent.putExtra("userid", listHeader.get(1).getUsername());
                intent.putExtra("objectId", listHeader.get(1).getObjectId());
                intent.setClass(getActivity(), OtherInfoDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.header_2:
                intent.putExtra("userid", listHeader.get(2).getUsername());
                intent.putExtra("objectId", listHeader.get(2).getObjectId());
                intent.setClass(getActivity(), OtherInfoDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.img_seriout:
                //popuwindow
                if (temp.equals("0")) {
                    showPopuwind();
                } else {
                    window.dismiss();
                    temp = "0";
                }

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


    private boolean refresh = true;
    private static int PageIndex = 1;
    private static String isload = "0";


    public void initEvetn() {
        swipyrefreshlayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    refresh = true;
                    PageIndex = 1;
                    users.setLimit(12);
                    users.setSkip(0);
                    isload = "1";
                    getDate();
                } else {
                    refresh = false;
                    PageIndex++;
                    isload = "1";
                    users.setLimit(12);
                    users.setSkip(PageIndex * 12 - 12);
                    getDate();
                }
            }

        });
    }

    private PopupWindow window;
    private static String temp = "0";

    private void showPopuwind() {
        temp = "1";
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_show, null);
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView searchgirl = (TextView) view.findViewById(R.id.tv_girl);
        searchgirl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                window.dismiss();
                Toast.makeText(getActivity(), "girl", Toast.LENGTH_LONG).show();
            }
        });
        TextView searboy = (TextView) view.findViewById(R.id.tv_boy);
        searboy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                window.dismiss();
                Toast.makeText(getActivity(), "boy", Toast.LENGTH_LONG).show();
            }
        });

        TextView searchall = (TextView) view.findViewById(R.id.tv_all);
        searchall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                window.dismiss();
                Toast.makeText(getActivity(), "all", Toast.LENGTH_LONG).show();
            }
        });


        window.setOutsideTouchable(true);
//        window.setAnimationStyle(R.style.mypopwindow_anim_style);
//        window.showAtLocation(getActivity().findViewById(R.id.rel_topmeoney), Gravity.BOTTOM, 0, 0);
        window.showAsDropDown(img_seriout);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });
    }
}
