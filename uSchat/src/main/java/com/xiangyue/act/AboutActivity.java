package com.xiangyue.act;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;

public class AboutActivity extends BaseActivity {
    private Context context;
    private LinearLayout linear_qq, linear_sina, linear_copyright;
    private TextView tv_qq;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private TextView tv_sina;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        context = this;
        setContentView(R.layout.about_main);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        initView();
    }

    public void initView() {
        linear_copyright = (LinearLayout) findViewById(R.id.linear_copyright);
        linear_copyright.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //nothing
            }
        });
        tv_sina = (TextView) findViewById(R.id.tv_sina);
        linear_sina = (LinearLayout) findViewById(R.id.linear_sina);
        linear_sina.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myClip = ClipData.newPlainText("text", tv_sina.getText().toString());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(AboutActivity.this, "已复制到剪切板", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        tv_qq = (TextView) findViewById(R.id.tv_qq);
        linear_qq = (LinearLayout) findViewById(R.id.linear_qq);
        linear_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AboutActivity.this, WebpactActivity.class);
                startActivity(intent);
            }

        });
    }

    public void back(View view) {
        finish();
    }


}
