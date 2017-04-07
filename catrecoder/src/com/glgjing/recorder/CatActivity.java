package com.glgjing.recorder;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CatActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText time;
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;
    private static final int AUDIO_REQUEST_CODE = 103;

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private RecordService recordService;
    private Button recoder;
    private EditText timedelay;
    private Myhandle handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        setContentView(R.layout.cat_main);
        initView();
        handle = new Myhandle();
        if (ContextCompat.checkSelfPermission(CatActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(CatActivity.this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST_CODE);
        }

        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
            recoder.setEnabled(true);
            recoder.setText(recordService.isRunning() ? R.string.stop_record : R.string.start_record);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    private void initView() {
        timedelay = (EditText) findViewById(R.id.timedelay);
        recoder = (Button) findViewById(R.id.recoder);
        recoder.setEnabled(false);
        recoder.setOnClickListener(this);
        time = (EditText) findViewById(R.id.time);
    }

    public void dianji(View view) {
        timercat.schedule(new TimerTask() {
            public void run() {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        catCreen();
                    }
                });
            }
        }, 0, Integer.parseInt(timedelay.getText().toString()) * 1000);

    }


    class Myhandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }


    private void catCreen() {
        View view2 = getWindow().getDecorView();
        Display display = this.getWindowManager().getDefaultDisplay();
        Rect frame = new Rect();
        view2.getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        view2.layout(0, 0, display.getWidth(), display.getHeight());
        view2.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view2.getDrawingCache(), 0, statusBarHeight, display.getWidth(), display.getHeight() - statusBarHeight - SizeUtil.getNavigationBarHeight(this));
//        getWindow().getDecorView().setDrawingCacheEnabled(true);
//        Bitmap bmp = getWindow().getDecorView().getDrawingCache();
        if (bmp != null) {
//            bmp = Utilsmap.BoxBlurFilter(bmp);
            saveBitmapFile(bmp);
        }
    }


    Timer timercat = new Timer();
    Timer timer = new Timer();

    public void saveBitmapFile(Bitmap bitmap) {
        File file = new File(getsaveDirectory() + System.currentTimeMillis() + ".png");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getsaveDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String rootDir = Environment.getExternalStorageDirectory().getPath() + "/test/photo/";
            File file = new File(rootDir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }
            return rootDir;
        } else {
            return null;
        }
    }

    private int times = 0;
    private int i = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recoder:
                times = Integer.parseInt(time.getText().toString());
                if (recordService.isRunning()) {
                    recordService.stopRecord();
                    recoder.setText(R.string.start_record);
                } else {
                    Intent captureIntent = projectionManager.createScreenCaptureIntent();
                    startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
                }

                timer.schedule(new TimerTask() {
                    public void run() {
                        i++;
                        if (i > times) {
                            i = 0;
                            timer.cancel();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (recordService.isRunning()) {
                                        recordService.stopRecord();
                                        recoder.setText(R.string.start_record);
                                    } else {
                                        Intent captureIntent = projectionManager.createScreenCaptureIntent();
                                        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
                                    }
                                    //结束录屏
                                    Toast.makeText(getApplicationContext(), "录制结束", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }, 0, 1000);

                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        timer.cancel();
        timercat.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProject(mediaProjection);
            recordService.startRecord();
            recoder.setText(R.string.stop_record);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE || requestCode == AUDIO_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        }
    }
}
