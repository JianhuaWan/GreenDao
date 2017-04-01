package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.squareup.otto.Subscribe;
import com.xiangyue.adpter.WiritePhotoAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleBean;
import com.xiangyue.bean.CirclePhotoBean;
import com.xiangyue.image.ImageItem;
import com.xiangyue.image.IntentConstants;
import com.xiangyue.provider.ShowPhotoEvent;
import com.xiangyue.service.LocationService;
import com.xiangyue.type.User;
import com.xiangyue.util.CommonUtils;
import com.xiangyue.util.SystemInfoTools;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.weight.LoginDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by wWX321637 on 2016/5/10.
 */
public class WriteCircleActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout lienar_address;
    private TextView img_situp;
    private EditText ed_content;
    private ImageView img_photo;
    private GridView gridView;
    private WiritePhotoAdapter adapter;
    private Context context;
    private TextView tv_address;
    private CircleBean circleBean;
    private MicroRecruitSettings settings;
    private String uuid;
    private List<BmobObject> bmobObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.writecircle_main);
        context = this;
        initMic();
        initView();
        getDevice();
    }

    private void initMic() {
        loginDialog = new LoginDialog(WriteCircleActivity.this, "");
        loginDialog.setCanceledOnTouchOutside(false);
        settings = new MicroRecruitSettings(context);
    }

    private void initView() {
        tv_address = (TextView) findViewById(R.id.tv_address);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        img_photo.setOnClickListener(this);
        img_situp = (TextView) findViewById(R.id.img_situp);
        img_situp.setOnClickListener(this);
        lienar_address = (LinearLayout) findViewById(R.id.lienar_address);
        lienar_address.setOnClickListener(this);
        ed_content = (EditText) findViewById(R.id.ed_content);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new WiritePhotoAdapter(context, mDataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageItem imageItem = (ImageItem) adapter.getItem(i);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("imageItem", imageItem);
                intent.putExtras(bundle);
                intent.setClass(WriteCircleActivity.this, SinglePhotoActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        mHandler.removeCallbacksAndMessages(null);//销毁未执行的message
        super.onDestroy();
    }

    private void initLoc() {
        // TODO Auto-generated method stub
        // -----------location config ------------
        locationService = ((BaseApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }

    /*****
     * @ee funtion to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    final List<Poi> list = location.getPoiList();// POI信息
                    Message message = mHandler.obtainMessage();
                    message.what = 9;
                    message.obj = list;
                    mHandler.sendMessage(message);


                }
                if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(getActivity(), "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(getActivity(), "网络不同导致定位失败，请检查网络是否通畅", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(getActivity(), "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机", Toast.LENGTH_LONG).show();
                }
            }
        }

    };

    public void back(View view) {
        finish();
    }

    private LoginDialog loginDialog;
    private LocationService locationService;
    private int temp;
    private boolean isover = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lienar_address:
                //选择位置
                if (CommonUtils.isFastClick()) {
                    return;
                }
                initLoc();

                break;
            case R.id.img_situp:
                if (ed_content.getText().toString().trim().equals("")) {
                    Toast.makeText(WriteCircleActivity.this, "请输入内容", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tv_address.getText().toString().equals("位置")) {
                    Toast.makeText(WriteCircleActivity.this, "请添加位置", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mDataList.size() == 0) {
                    Toast.makeText(WriteCircleActivity.this, "添加几张图片吧", Toast.LENGTH_LONG).show();
                    return;
                }
                //插入数据
                loginDialog.show();
                uuid = UUID.randomUUID().toString();
                final String[] uploadfiles = new String[mDataList.size()];
                for (int i = 0; i < mDataList.size(); i++) {
                    uploadfiles[i] = mDataList.get(i).getSourcePath();
                }
                //先是批量上传文件
                BmobFile.uploadBatch(WriteCircleActivity.this, uploadfiles, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        //判断文件是否全部上传完成
                        if (list1.size() == uploadfiles.length) {
                            //批量添加数据
                            List<String> ph = new ArrayList<String>();
                            for (int i = 0; i < mDataList.size(); i++) {
                                ph.add(list.get(i).getFileUrl(WriteCircleActivity.this));
                            }

                            //成功后提交文字信息
                            User user = BmobUser.getCurrentUser(WriteCircleActivity.this, User.class);//获取当前用户实例,所以后面不需要判断user是谁
                            CircleBean circleBean = new CircleBean();
                            circleBean.setUuid(uuid);
                            circleBean.setContent(ed_content.getText().toString());
                            circleBean.setAddress(tv_address.getText().toString());
                            circleBean.setUsername(user);
                            circleBean.setFromroot(getDevice());
                            circleBean.setPhotos(ph);
                            circleBean.save(context, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    //发送广播通知刷新
                                    Intent intent = new Intent();
                                    intent.setAction("changecircle");
                                    sendBroadcast(intent);
                                    loginDialog.dismiss();
                                    Toast.makeText(context, "发布成功", Toast.LENGTH_LONG).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    loginDialog.dismiss();
                                    Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {
                        Log.e("xiangyue", "上传进度" + i3);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(WriteCircleActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });

//                circleBean = new CircleBean(ed_content.getText().toString(), tv_address.getText().toString(), settings.phone.getValue(), getDevice(), uuid);


                break;
            case R.id.img_photo:
                //多图片选择(8张)
                new ActionSheetDialog(WriteCircleActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            public void onClick(int which) {
                                takePhoto();
                            }
                        }).addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    public void onClick(int which) {
                        Intent intent = new Intent();
                        intent.setClassName(context.getPackageName(), ImageChooseActivity.class.getName());
//                        intent.putExtra("image_list", getAvailableSize());
                        startActivityForResult(intent, GET_FROM_ABIUM);
                    }
                }).show();

                break;
            default:
                break;
        }
    }

    CirclePhotoBean bean;
    private String path = "";
    private static final int UPDATE = 30;
    private static final int ADDRESS = 305;
    private List<ImageItem> mDataList = new ArrayList<ImageItem>(); // 需要发表的总数

    private void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File vFile = new File(Environment.getExternalStorageDirectory() + "/xiangyue/",
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        path = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    private static final int TAKE_PICTURE = 0x000000;
    private static final int GET_FROM_ABIUM = 0x000001;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case TAKE_PICTURE: {
                img_photo.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                if (mDataList.size() < IntentConstants.MAX_IMAGE_SIZE && resultCode == -1 && !TextUtils.isEmpty(path)) {
                    ImageItem item = new ImageItem();
                    item.sourcePath = path;
                    mDataList.add(item);
                }
                Message msg = Message.obtain();
                msg.what = UPDATE;
                msg.obj = mDataList;
                mHandler.sendMessage(msg);
                break;
            }
            case GET_FROM_ABIUM:
                img_photo.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                if (null != data) {
                    List<ImageItem> incomingDataList = (List<ImageItem>) data.getSerializableExtra("selectedImgs");

                    if (incomingDataList != null) {
                        mDataList.addAll(incomingDataList);
                    }
                    Message msg = Message.obtain();
                    msg.what = UPDATE;
                    msg.obj = mDataList;
                    mHandler.sendMessage(msg);
                    break;
                }
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    mDataList = (List<ImageItem>) msg.obj;
                    adapter.setData(mDataList);
                    break;
                case 9:
                    final List<Poi> list = (List<Poi>) msg.obj;
                    new ActionSheetDialog(WriteCircleActivity.this).builder().setCancelable(false)
                            .setCanceledOnTouchOutside(false)
                            .addSheetItem(list.get(0).getName().toString(), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                                public void onClick(int which) {
                                    tv_address.setText(list.get(0).getName().toString());
                                }
                            }).addSheetItem(list.get(1).getName().toString(), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        public void onClick(int which) {
                            tv_address.setText(list.get(1).getName().toString());
                        }
                    }).addSheetItem(list.get(2).getName().toString(), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        public void onClick(int which) {
                            tv_address.setText(list.get(2).getName().toString());
                        }
                    })
                            .addSheetItem(list.get(3).getName().toString(), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                                public void onClick(int which) {
                                    tv_address.setText(list.get(3).getName().toString());
                                }
                            })
                            .addSheetItem(list.get(4).getName().toString(), ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                                public void onClick(int which) {
                                    tv_address.setText(list.get(4).getName().toString());
                                }
                            })
                            .show();
                    break;
//                case ADDRESS:
//                    location.getPoiList() = (List<String>) msg.obj;
//                    
//                    break;
                default:
                    break;
            }
        }

        ;
    };


    @Subscribe
    public void onShowPhotoEvent(ShowPhotoEvent event) {
        img_photo.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
    }

    private String getDevice() {
        return SystemInfoTools.getBuildInfo();//型号
    }
}
