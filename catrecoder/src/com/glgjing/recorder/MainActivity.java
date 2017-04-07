package com.glgjing.recorder;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mIv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        mIv_test = (ImageView) findViewById(R.id.iv_test);
    }

    public void dianji(View view) {
//        mydia dia = new mydia(MainActivity.this);
//        dia.show();

        View view2 = getWindow().getDecorView();
        Display display = this.getWindowManager().getDefaultDisplay();
        Rect frame = new Rect();
        view2.getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int bottom = frame.bottom;
        view2.layout(0, 0, display.getWidth(), display.getHeight());
        view2.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view2.getDrawingCache(), 0, statusBarHeight, display.getWidth(), display.getHeight() - statusBarHeight - SizeUtil.getNavigationBarHeight(this));

        if (bmp != null) {
            bmp = Utilsmap.BoxBlurFilter(bmp);
            mIv_test.setImageBitmap(bmp);
        }

//        传递bitmap
//        Bitmap bitmap = bmp;
//        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte [] bitmapByte =baos.toByteArray();
//        Bundle bundle = new Bundle();
//        bundle.putByteArray("bitmap", bitmapByte);
//        Intent intent = new Intent();
//        intent.setClass(MainActivity.this,Main2Activity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//
//        mydia dia = new mydia(MainActivity.this);
//        dia.show();

    }
}
