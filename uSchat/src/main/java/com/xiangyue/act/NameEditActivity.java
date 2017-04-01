package com.xiangyue.act;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.squareup.otto.Subscribe;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.provider.PhotoHeadRefreshEvent;
import com.xiangyue.tusdk.CameraComponentSimple;
import com.xiangyue.tusdk.EditMultipleComponentSimple;
import com.xiangyue.type.User;
import com.xiangyue.util.MeDialogUtils;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.view.ActionSheetDialog.OnSheetItemClickListener;
import com.xiangyue.view.ActionSheetDialog.SheetItemColor;
import com.xiangyue.weight.AlertDialog;
import com.xiangyue.weight.LoginDialog;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;
import org.lasque.tusdk.core.seles.tusdk.FilterManager.FilterManagerDelegate;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class NameEditActivity extends TuFragmentActivity implements OnClickListener {
    private LinearLayout linear_sex, linear_age;
    private TextView tv_sex, tv_age;
    private TextView tv_nickname;
    private int mYear;
    private int mMonth;
    private int mDay;
    int tempage;
    private ImageView img_headIcon;
    private MicroRecruitSettings settings;
    private LinearLayout lin_head;
    public static final int layoutId = R.layout.nameedit_main;
    private ImageView img_myphoto;
    private LinearLayout attestation;
    private LinearLayout lin_name;
    private LinearLayout mysing;
    private TextView tv_sign;
    private TextView tv_id;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void initActivity() {
        // TODO Auto-generated method stub
        super.initActivity();
        BusProvider.getInstance().register(this);
        this.setRootView(layoutId, 0);

    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        super.initView();
        tv_sign = (TextView) findViewById(R.id.tv_sign);
        tv_sign.setText(getIntent().getStringExtra("sign"));
        mysing = (LinearLayout) findViewById(R.id.mysing);
        mysing.setOnClickListener(this);
        lin_name = (LinearLayout) findViewById(R.id.lin_name);
        lin_name.setOnClickListener(this);
        tv_id = (TextView) findViewById(R.id.tv_id);

        attestation = (LinearLayout) findViewById(R.id.attestation);
        attestation.setOnClickListener(this);
        img_myphoto = (ImageView) findViewById(R.id.img_myphoto);
        img_myphoto.setOnClickListener(this);
        lin_head = (LinearLayout) findViewById(R.id.lin_head);
        img_headIcon = (ImageView) findViewById(R.id.img_headIcon);
        tv_age = (TextView) findViewById(R.id.tv_age);
        linear_age = (LinearLayout) findViewById(R.id.lin_age);
        linear_age.setOnClickListener(this);
        linear_sex = (LinearLayout) findViewById(R.id.lin_sex);
        linear_sex.setOnClickListener(this);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_sex.setText(getIntent().getStringExtra("sexy"));
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_nickname.setText(getIntent().getStringExtra("nick"));
        lin_head.setOnClickListener(this);
        lodingdialog = new LoginDialog(NameEditActivity.this, "");
        lodingdialog.setCanceledOnTouchOutside(false);
        TuSdk.messageHub().dismissRightNow();
        TuSdk.checkFilterManager(mFilterManagerDelegate);
        setUp();

        dialog = new LoginDialog(NameEditActivity.this, "");
        dialog.setCanceledOnTouchOutside(false);
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

    public void setUp() {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        settings = new MicroRecruitSettings(getApplicationContext());
        mYear = 1989;
        mMonth = 12;
        mDay = 1;
        // 显示当前时间
        if (!settings.HEADICON.getValue().toString().equals("")) {
//            Picasso.with(NameEditActivity.this).load(settings.HEADICON.getValue().toString())
//                    .error(R.drawable.ic_launcher).placeholder(R.drawable.ic_launcher).into(img_headIcon);
            ViewUtil.setPicture(NameEditActivity.this, settings.HEADICON.getValue().toString(), R.drawable.default_head, img_headIcon, null);
        }
        tv_id.setText(settings.OBJECT_ID.getValue());
    }


    public void back(View view) {
        new AlertDialog(NameEditActivity.this).builder().setTitle("提示")
                .setMsg("请确认保存修改信息")
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                        //提交信息
                        User user = new User();
                        user.setNickName(tv_nickname.getText().toString());
                        user.setAge(tv_age.getText().toString());
                        user.setSex(tv_sex.getText().toString());
                        user.setSign(tv_sign.getText().toString());
                        user.update(NameEditActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                dialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("nickname", tv_nickname.getText().toString());
                                intent.putExtra("sex", tv_sex.getText().toString());
                                intent.putExtra("age", tv_age.getText().toString());
                                intent.putExtra("sign", tv_sign.getText().toString());
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                dialog.dismiss();
                                Toast.makeText(NameEditActivity.this, s, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).show();
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.lin_sex:
                new ActionSheetDialog(NameEditActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("男", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_sex.setText("男");
                            }
                        }).addSheetItem("女", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_sex.setText("女");
                    }
                }).show();
                break;
            case R.id.lin_age:
                showTimeDialog();
                break;
            case R.id.lin_head:
                // 上传头像
                new ActionSheetDialog(NameEditActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                showCameraComponent();
                            }
                        }).addSheetItem("从相册中选择", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        showEditorComponent();
                    }
                }).show();
                break;
            case R.id.img_myphoto:
                //进入相册
                Intent intent2 = new Intent();
                intent2.putExtra("username", settings.phone.getValue());
                intent2.setClass(NameEditActivity.this, MyPhotosActivity.class);
                startActivity(intent2);
                break;
            case R.id.attestation:
                //复制
                myClip = ClipData.newPlainText("text", tv_id.getText().toString());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(NameEditActivity.this, "已复制到剪切板", Toast.LENGTH_LONG).show();
                break;
            case R.id.lin_name:
                Intent intent3 = new Intent();
                intent3.setClass(NameEditActivity.this, EditTextByNameActivity.class);
                intent3.putExtra("name", tv_nickname.getText().toString());
                startActivityForResult(intent3, 2694);
                break;
            case R.id.mysing:
                Intent intent4 = new Intent();
                intent4.setClass(NameEditActivity.this, SignStyleActivity.class);
                intent4.putExtra("sign", tv_sign.getText().toString());
                startActivityForResult(intent4, 2698);
                break;
            default:
                break;
        }
    }

    /**
     * 打开相机组件
     */
    private void showCameraComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("3");
        new CameraComponentSimple().showSimple(this);
    }

    /**
     * 打开多功能编辑组件
     */
    private void showEditorComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("3");
        new EditMultipleComponentSimple().showSimple(this);
    }

    public void showTimeDialog() {
        Calendar cal = Calendar.getInstance();

        final int mYeardel = cal.get(Calendar.YEAR);//获取当前时间的年份
//        mMonth = cal.get(Calendar.MONTH) + 1;//获取当前时间的年份
//        mDay = cal.get(Calendar.DATE);//获取当前时间的年份
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            cal.setTime(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        new MeDialogUtils(this)
                .builder(mYear, mMonth, mDay)
                .setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setOk(new MeDialogUtils.onDataTimeClickListener() {

                    @Override
                    public void onClick(View v, int mY, int mM, int mD) {
                        mYear = mY;
                        mMonth = mM;
                        mDay = mD;
                        Calendar cal = Calendar.getInstance();//获取当前时间
                        cal.set(mYear, mMonth - 1, mDay);
                        DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                        DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = sf.format(cal.getTime());
                        //设置时间
                        if (currentDate != null) {
                            int age = mYeardel - mYear;
                            tv_age.setText(age + "岁");
                        }
                    }
                })
                .show();
    }

    private File path = null;
    private LoginDialog lodingdialog;
    BmobFile bmobFile;

    private String uri;

    @Subscribe
    public void onPhotoHeadRefreshEvent(PhotoHeadRefreshEvent event) {
        // path = new File(Environment.getExternalStorageDirectory().getPath()
        // + event.path.substring(event.path.lastIndexOf("/DCIM"),
        // event.path.length()));
        uri = event.path;
        path = new File(event.path);
        // 上传头像
        if (lodingdialog == null) {
            lodingdialog = new LoginDialog(NameEditActivity.this, "");
            lodingdialog.setCanceledOnTouchOutside(false);
        }
        lodingdialog.show();
        bmobFile = new BmobFile(path);
        bmobFile.uploadblock(NameEditActivity.this, new UploadFileListener() {
            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                User user = new User();
                user.setHeadImage(bmobFile);
                user.update(NameEditActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        lodingdialog.dismiss();
//                        Picasso.with(NameEditActivity.this).load(path).error(R.drawable.ic_launcher)
//                                .placeholder(R.drawable.ic_launcher).into(img_headIcon);

                        ViewUtil.setPicture(NameEditActivity.this, bmobFile.getFileUrl(NameEditActivity.this), R.drawable.default_head, img_headIcon, null);
                        settings.HEADICON.setValue(bmobFile.getFileUrl(NameEditActivity.this));
                        // 这里采用广播
                        Intent intent = new Intent();
                        intent.setAction("changeIcon");
                        sendBroadcast(intent);
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        lodingdialog.dismiss();
                        Toast.makeText(NameEditActivity.this, "失败:" + arg1, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                lodingdialog.dismiss();
                Toast.makeText(NameEditActivity.this, "失败:" + arg1, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case 2694:
                tv_nickname.setText(data.getStringExtra("name"));
                break;
            case 2698:
                tv_sign.setText(data.getStringExtra("sign"));
                break;
            default:
                break;
        }
    }

    LoginDialog dialog;

    @Override
    public void onBackPressed() {
        new AlertDialog(NameEditActivity.this).builder().setTitle("提示")
                .setMsg("请确认保存修改信息")
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                        //提交信息
                        User user = new User();
                        user.setNickName(tv_nickname.getText().toString());
                        user.setAge(tv_age.getText().toString());
                        user.setSex(tv_sex.getText().toString());
                        user.setSign(tv_sign.getText().toString());
                        user.update(NameEditActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                dialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("nickname", tv_nickname.getText().toString());
                                intent.putExtra("sex", tv_sex.getText().toString());
                                intent.putExtra("age", tv_age.getText().toString());
                                intent.putExtra("sign", tv_sign.getText().toString());
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                dialog.dismiss();
                                Toast.makeText(NameEditActivity.this, s, Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).show();
    }

//    private void test() {
//        dialogrr = new ProgressDialog(this);
//        dialogrr.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        dialogrr.setTitle("�ϴ���...");
//        dialogrr.setIndeterminate(false);
//        dialogrr.setCancelable(true);
//        dialogrr.setCanceledOnTouchOutside(false);
//        dialogrr.show();
//        path = new File("/storage/emulated/0/DCIM/Camera/LSQ_20160515_103256215.jpg");
//        // 上传头像
//        if (lodingdialog == null) {
//            lodingdialog = new LoginDialog(NameEditActivity.this, "");
//            lodingdialog.setCanceledOnTouchOutside(false);
//        }
//        lodingdialog.show();
//        bmobFile = new BmobFile(path);
//        bmobFile.uploadblock(NameEditActivity.this, new UploadFileListener() {
//            @Override
//            public void onProgress(Integer value) {
//                super.onProgress(value);
//                dialogrr.setProgress(value);
//            }
//
//            @Override
//            public void onSuccess() {
//                // TODO Auto-generated method stub
//                User user = new User();
//                user.setHeadImage(bmobFile);
//                user.update(NameEditActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {
//
//                    @Override
//                    public void onSuccess() {
//                        // TODO Auto-generated method stub
//                        lodingdialog.dismiss();
//                        Picasso.with(NameEditActivity.this).load(path).error(R.drawable.ic_launcher)
//                                .placeholder(R.drawable.ic_launcher).into(img_headIcon);
//                        settings.HEADICON.setValue(bmobFile.getFileUrl(NameEditActivity.this));
//                        // 这里采用广播
//                        Intent intent = new Intent();
//                        intent.setAction("changeIcon");
//                        sendBroadcast(intent);
//                    }
//
//                    @Override
//                    public void onFailure(int arg0, String arg1) {
//                        // TODO Auto-generated method stub
//                        lodingdialog.dismiss();
//                        Toast.makeText(NameEditActivity.this, "失败:" + arg1, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(int arg0, String arg1) {
//                // TODO Auto-generated method stub
//                lodingdialog.dismiss();
//                Toast.makeText(NameEditActivity.this, "失败:" + arg1, Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}
