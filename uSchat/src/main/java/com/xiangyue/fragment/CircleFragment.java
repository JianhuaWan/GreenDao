package com.xiangyue.fragment;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.xiangyue.act.BlackActivity;
import com.xiangyue.act.CircleInfoActivity;
import com.xiangyue.act.OtherCircleActivity;
import com.xiangyue.act.R;
import com.xiangyue.act.WriteCircleActivity;
import com.xiangyue.adpter.CircleAdapter;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleBean;
import com.xiangyue.bean.circlrmebgphpto;
import com.xiangyue.pullrefresh.PullToRefreshBase;
import com.xiangyue.pullrefresh.PullToRefreshListView;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.weight.LoginDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * 圈子
 *
 * @author Administrator
 */
public class CircleFragment extends android.support.v4.app.Fragment implements OnClickListener {
    private static int PageIndex = 1;
    BmobRealTimeData rtd;
    LoginDialog dialog;
    BmobFile bmobFile;
    private SwipyRefreshLayout swipyrefreshlayout;
    private ListView listView;
    private View headerView;
    private Context context;
    private CircleAdapter adapter;
    private List<CircleBean> lists = new ArrayList<CircleBean>();
    private List<CircleBean> firstdata = new ArrayList<CircleBean>();
    private ImageView img_write, myheadIcon, img_bg;
    private TextView img_situp;
    private MicroRecruitSettings settings;
    private boolean refresh;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.hcircle_main, container, false);
        context = getActivity();
        initTools();
        initView(container);
        initEvetn();
        getCircleData();
        getBgObjectId();
        userLisenter();
        initBro();
        return container;
    }


    public void initBro() {
        Receiver bReceiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter("bgcircle");
        getActivity().registerReceiver(bReceiver, intentFilter);


        ReceiverByCircle bReceivercircle = new ReceiverByCircle();
        IntentFilter intentFilter1 = new IntentFilter("changecircle");
        getActivity().registerReceiver(bReceivercircle, intentFilter1);
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
//            ViewUtil.setPicture(getActivity(), intent.getStringExtra("path"), R.drawable.default_head, myheadIcon, null);
            Upload(intent.getStringExtra("path"));
        }
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

    private void getBgObjectId() {
        final BmobQuery<circlrmebgphpto> circlrmebgphptoBmobQuery = new BmobQuery<>();
        circlrmebgphptoBmobQuery.addWhereEqualTo("username", settings.phone.getValue());
        circlrmebgphptoBmobQuery.findObjects(getActivity(), new FindListener<circlrmebgphpto>() {
            @Override
            public void onSuccess(List<circlrmebgphpto> list) {
                if (list.size() == 0) {
                    //如果没有创建该列数据的时候,这创建后保存ObjectID数据
                    final circlrmebgphpto circlrmebgphptos = new circlrmebgphpto();
                    circlrmebgphptos.setUsername(settings.phone.getValue());
                    circlrmebgphptos.save(getActivity(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            //保存objectid
                            settings.OBJECT_IDBYCIRCLEBG.setValue(circlrmebgphptos.getObjectId());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    settings.OBJECT_IDBYCIRCLEBG.setValue(list.get(0).getObjectId());
                    //判断后设置值
                    if (list.get(0).getCirclebg() != null) {
                        ViewUtil.setPicture(getActivity(), list.get(0).getCirclebg().getFileUrl(getActivity()), R.drawable.default_head, img_bg, null);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initTools() {
        settings = new MicroRecruitSettings(getActivity());
    }

    private void userLisenter() {
        rtd = new BmobRealTimeData();
        rtd.start(getActivity(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                Log.e("xiangyue", "连接成功:" + rtd.isConnected());
                if (rtd.isConnected()) {
                    rtd.subTableUpdate("CircleBean");
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                Log.e("xiangyue", jsonObject.toString());
                img_situp.setVisibility(View.VISIBLE);
            }
        });
    }

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
                    isload = "1";
                    getCircleData();
                    getBgObjectId();
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
//        pl_showcircle.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                // TODO Auto-generated method stub
//                refresh = true;
//                PageIndex = 1;
//                isload = "1";
//                getCircleData();
//                getBgObjectId();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                // TODO Auto-generated method stub
//                refresh = false;
//                PageIndex++;
//                isload = "1";
//                query.setLimit(10);
//                query.setSkip(PageIndex * 10 - 10);
//                getCircleData();
//                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//
//
//            }
//        });
    }

    BmobQuery<CircleBean> query = new BmobQuery<>();

    private void getCircleData() {
        img_situp.setVisibility(View.GONE);
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
        query.findObjects(getActivity(), new FindListener<CircleBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(List<CircleBean> list) {
                        //成功后查询出相对应的图片
                        swipyrefreshlayout.setRefreshing(false);
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
                                adapter.setData(getActivity(), lists);
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
                        swipyrefreshlayout.setRefreshing(false);
                        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                    }
                }

        );
    }

    public void initView(View conView) {
        dialog = new LoginDialog(getActivity(), "");
        dialog.setCanceledOnTouchOutside(false);
        img_situp = (TextView) conView.findViewById(R.id.img_situp);
        img_situp.setVisibility(View.GONE);
        listView = (ListView) conView.findViewById(R.id.pl_showcircle);
        swipyrefreshlayout = (SwipyRefreshLayout) conView.findViewById(R.id.swipyrefreshlayout);
        swipyrefreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        headerView = LayoutInflater.from(context).inflate(R.layout.circle_top, null);
        listView.addHeaderView(headerView);
        myheadIcon = (ImageView) headerView.findViewById(R.id.myheadIcon);
        img_bg = (ImageView) headerView.findViewById(R.id.img_bg);
//        if (!settings.OBJECT_IDBYCIRCLEICON.getValue().equals("")) {
//            ViewUtil.setPicture(getActivity(), settings.OBJECT_IDBYCIRCLEICON.getValue(), R.drawable.default_head, img_bg, null);
//        }
        ViewUtil.setPicture(getActivity(), settings.HEADICON.getValue(), R.drawable.default_head, myheadIcon, null);
        img_write = (ImageView) headerView.findViewById(R.id.img_write);
        img_write.setOnClickListener(this);
//        for (int i = 0; i < 10; i++) {
//            CircleBean bean = new CircleBean();
//            list.add(bean);
//        }
        myheadIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入我的圈子
                BmobQuery<circlrmebgphpto> circlemephotos = new BmobQuery<>();
                circlemephotos.addWhereEqualTo("username", settings.phone.getValue());
                circlemephotos.findObjects(getActivity(), new FindListener<circlrmebgphpto>() {
                    @Override
                    public void onSuccess(List<circlrmebgphpto> list) {
                        Intent intent1 = new Intent();
                        intent1.putExtra("type", "me");
                        if (list.size() == 0) {
                            intent1.putExtra("otherbg", "0");
                            intent1.setClass(getActivity(), OtherCircleActivity.class);
                            startActivity(intent1);
                        } else if (list.get(0).getCirclebg() == null) {
                            intent1.putExtra("otherbg", "0");
                            intent1.setClass(getActivity(), OtherCircleActivity.class);
                            startActivity(intent1);
                        } else {
                            intent1.putExtra("otherbg", list.get(0).getCirclebg().getFileUrl(getActivity()));
                            intent1.setClass(getActivity(), OtherCircleActivity.class);
                            startActivity(intent1);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        img_bg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //更换背景
                new ActionSheetDialog(getActivity()).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = new Intent();
                                intent.putExtra("type", "1");
                                intent.setClass(getActivity(), BlackActivity.class);
                                startActivity(intent);

                            }
                        }).addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Intent intent = new Intent();
                        intent.putExtra("type", "2");
                        intent.setClass(getActivity(), BlackActivity.class);
                        startActivity(intent);
                    }
                }).show();
            }
        });
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
                break;
            default:
                break;
        }
    }

    public void Upload(String event) {
        String uri = event;
        File path = new File(event);
        // 上传头像
        dialog.show();
        bmobFile = new BmobFile(path);
        bmobFile.uploadblock(getActivity(), new UploadFileListener() {
            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                circlrmebgphpto circlrmebgphptos = new circlrmebgphpto();
                circlrmebgphptos.setCirclebg(bmobFile);
                circlrmebgphptos.update(getActivity(), settings.OBJECT_IDBYCIRCLEBG.getValue().toString(), new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
//                        Picasso.with(NameEditActivity.this).load(path).error(R.drawable.ic_launcher)
//                                .placeholder(R.drawable.ic_launcher).into(img_headIcon);
                        ViewUtil.setPicture(getActivity(), bmobFile.getFileUrl(getActivity()), R.drawable.default_head, img_bg, null);
//                        settings.OBJECT_IDBYCIRCLEICON.setValue(bmobFile.getFileUrl(getActivity()));
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        Toast.makeText(getActivity(), arg1, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Toast.makeText(getActivity(), arg1, Toast.LENGTH_LONG).show();
            }
        });
    }
}
