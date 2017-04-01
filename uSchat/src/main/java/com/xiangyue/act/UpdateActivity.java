package com.xiangyue.act;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.updateversion.DownloadWcsApkService;
import com.xiangyue.updateversion.ICallbackResult;

import java.io.File;

/**
 * Created by wWX321637 on 2016/6/14.
 */
public class UpdateActivity extends BaseActivity implements View.OnClickListener {
    private TextView umeng_update_content;
    private CheckBox umeng_update_id_check;
    private Button umeng_update_id_ok, umeng_update_id_cancel;
    private MicroRecruitSettings settings;

    private Context context;

    private boolean isBinded = false;

    private ProgressDialog proDia;

    private static final int DOWN_OVER = 2;
    private static final int DOWN_ERROR = 3;

    private static final int SET_DOWNLOAD_MAX = 15;
    private static final int REFRESS_DOWNLOAD_PROGRESS = 16;
    private static final int STOP_DOWNLOAD_APK = 17;

    private Long totalLenth;
    private Long readLenth;
    private boolean isDownloadOver = false;

    private DownloadWcsApkService.DownloadBinder binder;

    private boolean isStopDownload = false;

    /**
     * 项目缓存地址
     */
    public static String APKCachePath = Environment.getExternalStorageDirectory() + "/";
    private static final String saveFileName = APKCachePath + "sssssssss.apk";
    private String apkUrl;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.umeng_update_dialog);
        context = UpdateActivity.this;
        initTools();
        initView();
    }

    private void initTools() {
        settings = new MicroRecruitSettings(UpdateActivity.this);
        apkUrl = getIntent().getStringExtra("apkurl");
        BaseApplication.getInstance().setApkurl(apkUrl);
    }

    private void initView() {
        umeng_update_content = (TextView) findViewById(R.id.umeng_update_content);
        umeng_update_content.setText(getIntent().getStringExtra("updateinfo"));
        umeng_update_id_check = (CheckBox) findViewById(R.id.umeng_update_id_check);
        umeng_update_id_ok = (Button) findViewById(R.id.umeng_update_id_ok);
        umeng_update_id_ok.setOnClickListener(this);
        umeng_update_id_cancel = (Button) findViewById(R.id.umeng_update_id_cancel);
        umeng_update_id_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.umeng_update_id_cancel:
                if (umeng_update_id_check.isChecked()) {
                    settings.UPDATE_TIPS.setValue("1");//不提示
                } else {
                    settings.UPDATE_TIPS.setValue("");
                }
                finish();
                break;
            case R.id.umeng_update_id_ok:
                //通知栏下载
                Intent intentDownload = new Intent(UpdateActivity.this, DownloadWcsApkService.class);
                intentDownload.putExtra("url", getIntent().getStringExtra("apkurl"));
                bindService(intentDownload, conn, Context.BIND_AUTO_CREATE);
                finish();
                break;
            default:
                break;
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (DownloadWcsApkService.DownloadBinder) service;
            System.out.println("服务启动!!!");
            // 开始下载
            binder = (DownloadWcsApkService.DownloadBinder) service;
            System.out.println("服务启动!!!");
            // 开始下载
            isBinded = true;
            binder.addCallback(callback);
            binder.start();
        }

    };
    private ICallbackResult callback = new ICallbackResult() {
        @Override
        public void OnBackResult(Object result) {
            if ("finish".equals(result)) {
                finish();
                return;
            }
        }
    };

    /**
     * 安装apk
     *
     * @par
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(i);
    }

    private Handler handler = new Handler() {
        @SuppressLint("NewApi")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case STOP_DOWNLOAD_APK:
                    if (null != proDia) {
                        proDia.dismiss();
                    }
                    proDia = null;
                    isStopDownload = false;// 便于下次你点击立即更新的时候还可以下载，也可以取消下载这里有个bug
                    break;
                case SET_DOWNLOAD_MAX:
                    isDownloadOver = false;
                    if (null == proDia) {
                        return;
                    }
                    int maxValue = (Integer) msg.obj;
                    proDia.setMax(maxValue);
                    proDia.setTitle("正在下载文件，总共  " + maxValue / 1024 + "kb");
                    break;
                case REFRESS_DOWNLOAD_PROGRESS:
                    if (null == proDia) {
                        return;
                    }
                    isDownloadOver = false;
                    int currentProgress = (Integer) msg.obj;
                    proDia.setMessage("已下载  " + currentProgress / 1024 + "kb");

                    proDia.setProgress(currentProgress);
                    break;
                case DOWN_OVER:
                    isDownloadOver = true;
                    if (null != proDia) {
                        proDia.dismiss();
                    }
                    installApk();
                    break;
                case DOWN_ERROR:
                    if (null != proDia) {
                        proDia.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };
}

