package com.xiangyue.updateversion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.xiangyue.act.MainActivity;
import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadWcsApkService extends Service {

    private final int SUCCESS_DOWN_LOAD = 12;
    private final int CANCEL_DOWNLOAD = 11;
    private final int DOWNLOAD_ERROR = 14;
    private final int SET_PROGRESS = 13;

    private static final int NOTIFY_ID = 0;
    private int progress;
    private NotificationManager mNotificationManager;
    private boolean canceled;

    // 返回的安装包url
    private String apkUrl = "";
    private String ACTION_CANCEL_DOWNLOAD_APK = "action_cancel_download_apk";
    private String ACTION_PAUSE_DOWNLOAD_APK = "action_pause_download_apk";

    // private String apkUrl = MyApp.downloadApkUrl;
    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/wcs/";

    private static final String saveFileName = savePath + "wcs.apk";
    private ICallbackResult callback;
    private DownloadBinder binder;
    // private MyApplication app;
    private boolean serviceIsDestroy = false;

    private Context mContext = this;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_DOWN_LOAD:
                    // app.setDownload(false);
                    // 下载完毕
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    installApk();
                    break;
                case CANCEL_DOWNLOAD:
                    // app.setDownload(false);
                    // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                case DOWNLOAD_ERROR:
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                case SET_PROGRESS:
                    int rate = msg.arg1;
                    // app.setDownload(true);
                    if (rate < 100) {
                        RemoteViews contentview = mNotification.contentView;
                        contentview.setTextViewText(R.id.tv_progress, rate + "%");
                        contentview.setProgressBar(R.id.progressbar, 100, rate, false);

                    } else {
                        System.out.println("下载完毕!!!!!!!!!!!");
                        // 下载完毕后变换通知形式
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        mNotification.contentView = null;
                        Intent intent = new Intent(mContext, MainActivity.class);
                        // 告知已完成
                        intent.putExtra("completed", "yes");
                        // 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
                        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        mNotification.setLatestEventInfo(mContext, "下载完成", "文件已下载完毕", contentIntent);
                        //
                        serviceIsDestroy = true;
                        stopSelf();// 停掉服务自身
                    }
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
            }
        }
    };

    //
    // @Override
    // public int onStartCommand(Intent intent, int flags, int startId) {
    // return START_STICKY;
    // }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("是否执行了 onBind");
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("downloadservice ondestroy");
        // 假如被销毁了，无论如何都默认取消了。
        // app.setDownload(false);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("downloadservice onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {

        super.onRebind(intent);
        System.out.println("downloadservice onRebind");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO;;
        apkUrl = BaseApplication.getApkurl();
        binder = new DownloadBinder();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // setForeground(true);// 这个不确定是否有作用
        // app = (MyApplication) getApplication();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CANCEL_DOWNLOAD_APK);
        registerReceiver(onclickCancelListener, filter);

    }

    public class DownloadBinder extends Binder {
        public void start() {
            if (downLoadThread == null || !downLoadThread.isAlive()) {
                progress = 0;
                setUpNotification();
                new Thread() {
                    public void run() {
                        startDownload();
                    }

                    ;
                }.start();
            }
        }

        public void cancel() {
            canceled = true;
        }

        public int getProgress() {
            return progress;
        }

        public boolean isCanceled() {
            return canceled;
        }

        public boolean serviceIsDestroy() {
            return serviceIsDestroy;
        }

        public void cancelNotification() {
            mHandler.sendEmptyMessage(CANCEL_DOWNLOAD);
        }

        public void addCallback(ICallbackResult callback) {
            DownloadWcsApkService.this.callback = callback;
        }
    }

    private void startDownload() {
        canceled = false;
        downloadApk();
    }

    BroadcastReceiver onclickCancelListener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_CANCEL_DOWNLOAD_APK)) {
                // TODO;;
                // app.setDownload(false);
                // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
                // 取消通知
                mNotificationManager.cancel(NOTIFY_ID);
                binder.cancel();
                binder.cancelNotification();
                if (binder != null && binder.isCanceled()) {
                    stopSelf();
                }
                callback.OnBackResult("cancel");
            }
        }
    };
    BroadcastReceiver onClickPauseLisener = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_PAUSE_DOWNLOAD_APK)) {
                // TODO;;
                // app.setDownload(false);
                // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
                // 取消通知
                // mNotificationManager.cancel(NOTIFY_ID);
                // binder.cancel();
                // binder.cancelNotification();

                callback.OnBackResult("cancel");
            }
        }
    };

    //
    Notification mNotification;

    // 通知栏

    /**
     * 创建通知
     */
    private void setUpNotification() {
        int icon = R.drawable.app_icon;
        CharSequence tickerText = "开始下载";
        long when = System.currentTimeMillis();
        mNotification = new Notification(icon, tickerText, when);
        ;
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.download_notification_layout);
        contentView.setTextColor(R.id.name, 0xffffffff);
        contentView.setTextViewText(R.id.name, "捡缘.apk 正在下载...");
        // 指定个性化视图
        mNotification.contentView = contentView;

        Intent btnCancelIntent = new Intent(ACTION_CANCEL_DOWNLOAD_APK);
        PendingIntent pendButtonIntent = PendingIntent.getBroadcast(this, 0, btnCancelIntent, 0);
        contentView.setOnClickPendingIntent(R.id.ivDelete, pendButtonIntent);
        // R.id.trackname为你要监听按钮的id
        // mRemoteViews.setOnClickPendingIntent(R.id.trackname,
        // pendButtonIntent);

        Intent intent = new Intent(this, MainActivity.class);
        // 下面两句是 在按home后，点击通知栏，返回之前activity 状态;
        // 有下面两句的话，假如service还在后台下载， 在点击程序图片重新进入程序时，直接到下载界面，相当于把程序MAIN 入口改了 - -
        // 是这么理解么。。。
        // intent.setAction(Intent.ACTION_MAIN);
        // intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 指定内容意图
        mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    //
    /**
     * 下载apk
     *
     * @param url
     */
    private Thread downLoadThread;

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable1);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
        callback.OnBackResult("finish");

    }

    private int lastRate = 0;
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int totalLength = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);

                if (null != ApkFile && ApkFile.length() < totalLength) {
                    ApkFile.delete();
                    ApkFile.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / totalLength) * 100);
                    // 更新进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress >= lastRate + 1) {
                        mHandler.sendMessage(msg);
                        lastRate = progress;
                        if (callback != null)
                            callback.OnBackResult(progress);
                    }
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(SUCCESS_DOWN_LOAD);
                        // 下载完了，cancelled也要设置
                        canceled = true;
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!canceled);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable mdownApkRunnable1 = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setUseCaches(true);
                conn.setConnectTimeout(2 * 1000);
                conn.setRequestMethod("GET");
                long totalLenth = (long) conn.getContentLength();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                File fileApk = new File(saveFileName);
                if (!fileApk.exists()) {
                    try {
                        fileApk.createNewFile();
                    } catch (Exception e) {
                        // Toast.makeText(context, "没有存储设备，无法下载最新版本",
                        // Toast.LENGTH_SHORT).show();
                    }
                }
                if (null != fileApk && fileApk.length() < totalLenth) {
                    fileApk.delete();
                    fileApk.createNewFile();
                }
                if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    return;
                }

                InputStream in = conn.getInputStream();
                FileOutputStream fileOut = new FileOutputStream(fileApk);
                byte[] buffer = new byte[1024];
                int length = -1;
                int currentCount = 0;
                try {
                    while ((length = in.read(buffer)) != -1 && !canceled) {
                        // int numread = in.read(buffer);
                        // currentCount += numread;
                        progress = (int) (((float) fileApk.length() / totalLenth) * 100);
                        // 更新进度
                        Message msg = mHandler.obtainMessage();
                        msg.what = SET_PROGRESS;
                        msg.arg1 = progress;
                        if (progress >= lastRate + 1) {
                            mHandler.sendMessage(msg);
                            lastRate = progress;
                            if (callback != null)
                                callback.OnBackResult(progress);
                        }
                        // fileOut.write(data, 0, numread);
                        fileOut.write(buffer, 0, length);
                    }
                    if (canceled) {
                        Message msgDownloadCancel = new Message();
                        msgDownloadCancel.what = CANCEL_DOWNLOAD;
                        mHandler.sendMessage(msgDownloadCancel);
                        return;
                    }
                    mHandler.sendEmptyMessage(SUCCESS_DOWN_LOAD);
                    // 下载完了，cancelled也要设置
                    canceled = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msgError = new Message();
                    msgError.what = DOWNLOAD_ERROR;
                    mHandler.sendMessage(msgError);
                } finally {
                    if (null != in) {
                        in.close();
                    }
                    if (null != conn) {
                        conn.disconnect();
                    }
                    if (null != fileOut) {
                        fileOut.flush();
                        fileOut.close();
                    }
                }

            } catch (Exception e) {
                Message msgError = new Message();
                msgError.what = DOWNLOAD_ERROR;
                mHandler.sendMessage(msgError);
            }
        }
    };

}
