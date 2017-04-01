package com.xiangyue.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.view.ActionSheetDialog.OnSheetItemClickListener;
import com.xiangyue.view.ActionSheetDialog.SheetItemColor;

public class ChoiseLeaveActivity extends BaseActivity implements OnClickListener {
    private LinearLayout linearleaver;
    private TextView tv_nj;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.choiseleave_main);
        linearleaver = (LinearLayout) findViewById(R.id.linearlever);
        linearleaver.setOnClickListener(this);
        tv_nj = (TextView) findViewById(R.id.tv_nj);

    }

    public void back(View v) {
        Intent intent = new Intent();
        intent.putExtra("leaver", tv_nj.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.putExtra("leaver", tv_nj.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.linearlever:
                new ActionSheetDialog(getActivity()).builder().setCancelable(false).setCanceledOnTouchOutside(false)
                        .addSheetItem("大一", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_nj.setText("大一");
                            }
                        }).addSheetItem("大二", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_nj.setText("大二");
                    }
                }).addSheetItem("大三", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_nj.setText("大三");
                    }
                }).addSheetItem("大四", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tv_nj.setText("大四");
                    }
                })

                        .addSheetItem("往届", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv_nj.setText("往届");
                            }
                        }).show();
                break;

            default:
                break;
        }
    }

}
