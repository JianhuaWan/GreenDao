package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.xiangyue.adpter.GridAdapter;
import com.xiangyue.adpter.GridAdapter.OnGridItemClickListener;
import com.xiangyue.adpter.MyPhotosAdapter;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.PhotoUrl;
import com.xiangyue.bean.Photos;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.provider.PhotoListRefreshEvent;
import com.xiangyue.pullrefresh.PullToRefreshBase;
import com.xiangyue.pullrefresh.PullToRefreshListView;
import com.xiangyue.tusdk.CameraComponentSimple;
import com.xiangyue.tusdk.EditMultipleComponentSimple;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.view.ActionSheetDialog.OnSheetItemClickListener;
import com.xiangyue.view.ActionSheetDialog.SheetItemColor;
import com.xiangyue.weight.LoginDialog;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;
import org.lasque.tusdk.core.seles.tusdk.FilterManager.FilterManagerDelegate;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 */
public class MyPhotosActivity extends TuFragmentActivity implements OnClickListener {
    private TextView img_upload_photo;
    private MicroRecruitSettings settings;
    private Context mContext;
    public static final int layoutId = R.layout.show_photos;
    private LoginDialog lodingdialog;
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private MyPhotosAdapter adapter;
    private ArrayList<PhotoUrl> list = new ArrayList<PhotoUrl>();
    private LinearLayout liner_nodata;
    private int count;

    @Override
    protected void initActivity() {
        // TODO Auto-generated method stub
        super.initActivity();
        mContext = MyPhotosActivity.this;
        BusProvider.getInstance().register(this);
        this.setRootView(layoutId, 0);
    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        super.initView();
        settings = new MicroRecruitSettings(mContext);
        lodingdialog = new LoginDialog(MyPhotosActivity.this, "");
        lodingdialog.setCanceledOnTouchOutside(false);
        init();
        TuSdk.messageHub().dismissRightNow();
        TuSdk.checkFilterManager(mFilterManagerDelegate);
        // 获取
        dopostCon();
    }

    /**
     * 滤镜管理器委托
     */
    private FilterManagerDelegate mFilterManagerDelegate = new FilterManagerDelegate() {
        @Override
        public void onFilterManagerInited(FilterManager manager) {
            // TuSdk.messageHub().showSuccess(MeAccountActivity.this,
            // R.string.lsq_inited);
            TuSdk.messageHub().dismissRightNow();// 去掉提示
        }
    };

    public void init() {
        liner_nodata = (LinearLayout) findViewById(R.id.liner_nodata);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pl_photos);
        listView = pullToRefreshListView.getRefreshableView();
        img_upload_photo = (TextView) findViewById(R.id.img_upload_photo);
        img_upload_photo.setOnClickListener(this);
        if (getIntent().getStringExtra("username").equals(settings.phone.getValue())) {
            img_upload_photo.setVisibility(View.VISIBLE);
        } else {
            img_upload_photo.setVisibility(View.GONE);
        }
        if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
            // 如果API版本高于9，则设置listview没有自带滚动结束后的反弹效果
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        adapter = new MyPhotosAdapter(mContext, list);
        GridAdapter mAdapter = new GridAdapter<MyPhotosAdapter>(mContext, adapter);
        mAdapter.setNumColumns(3);//
        mAdapter.setOnItemClickListener(new OnGridItemClickListener() {
            @Override
            public void onItemClick(int pos, int realPos) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("photos", list);
                intent.putExtras(bundle);
                intent.putExtra("pos", realPos);
                if (getIntent().getStringExtra("username").equals(settings.phone.getValue())) {
                    intent.putExtra("flag", "myself");
                } else {
                    intent.putExtra("flag", "other");
                }
                intent.setClass(MyPhotosActivity.this, PhotoDetailActivity.class);
                startActivityForResult(intent, 1571);

            }
        });
        listView.setAdapter(mAdapter);
        initEvetn();
    }

    public void back(View view) {
        finish();
    }

    public void dopostCon() {
        BmobQuery<Photos> query = new BmobQuery<Photos>();
        query.addWhereEqualTo("username", getIntent().getStringExtra("username"));
        query.setLimit(12);// 查询12条数据
        query.findObjects(mContext, new FindListener<Photos>() {
            @Override
            public void onSuccess(List<Photos> arg0) {
                count = arg0.size();
                // TODO Auto-generated method stub
                pullToRefreshListView.onRefreshComplete();
                pullToRefreshListView.setVisibility(View.VISIBLE);
                pullToRefreshListView.getRefreshableView().setVisibility(View.VISIBLE);
                list.clear();
                for (int i = 0; i < arg0.size(); i++) {
                    PhotoUrl url = new PhotoUrl();
                    url.setDelurl(arg0.get(i).getPhoto().getUrl());
                    url.setShowurl(arg0.get(i).getPhoto().getFileUrl(MyPhotosActivity.this));
                    url.setObjectId(arg0.get(i).getObjectId());
                    list.add(url);
                }
                if (list.size() > 0) {
                    liner_nodata.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    pullToRefreshListView.setVisibility(View.VISIBLE);
                    adapter.setData(list);
                } else {
                    liner_nodata.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    pullToRefreshListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                pullToRefreshListView.onRefreshComplete();
                Toast.makeText(mContext, arg1, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.img_upload_photo:
                if (count == 12) {
                    Toast.makeText(mContext, "相册最多只能上传12张", Toast.LENGTH_LONG).show();
                    return;
                }
                // 上传头像
                new ActionSheetDialog(MyPhotosActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            public void onClick(int which) {
                                showCameraComponent();
                            }
                        }).addSheetItem("从相册中选择", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    public void onClick(int which) {
                        showEditorComponent();
                    }
                }).show();
                break;
            default:
                break;
        }
    }

    /**
     * 打开相机组件
     */
    private void showCameraComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("2");
        new CameraComponentSimple().showSimple(this);
    }

    /**
     * 打开多功能编辑组件
     */
    private void showEditorComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("2");
        new EditMultipleComponentSimple().showSimple(this);
    }

    private File path = null;

    @Subscribe
    public void onPhotoListRefreshEvent(PhotoListRefreshEvent event) {
        // path = new File(Environment.getExternalStorageDirectory().getPath()
        // + event.path.substring(event.path.lastIndexOf("/DCIM"),
        // event.path.length()));
        path = new File(event.path);
        // 上传头像
        lodingdialog.show();
        final BmobFile bmobFile = new BmobFile(path);
        bmobFile.uploadblock(this, new UploadFileListener() {

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(MyPhotosActivity.this, "失败:" + arg1, Toast.LENGTH_LONG).show();
                lodingdialog.dismiss();
            }

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Photos bean = new Photos();
                bean.setUsername(settings.phone.getValue().toString());
                bean.setIsillegal("0");
                bean.setPhoto(bmobFile);
                bean.save(mContext, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        // 显示所上传的照片
                        dopostCon();// 获取所有照片
                        lodingdialog.dismiss();
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "上传失败:" + arg1, Toast.LENGTH_LONG).show();
                        lodingdialog.dismiss();
                    }
                });
            }
        });
    }

    private boolean refresh = true;
    private static int PageIndex = 1;

    public void initEvetn() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                refresh = true;
                PageIndex = 1;
                dopostCon();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                refresh = false;
                PageIndex++;
                dopostCon();
                String label = DateUtils.formatDateTime(MyPhotosActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case 1571:
                dopostCon();
                break;
        }
    }
}
