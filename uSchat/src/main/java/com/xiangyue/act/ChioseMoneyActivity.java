package com.xiangyue.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import c.b.BP;
import c.b.PListener;

/**
 * Created by wWX321637 on 2016/6/3.
 */
public class ChioseMoneyActivity extends BaseActivity implements View.OnClickListener {
    private Button submit;
    private RelativeLayout rel_wechatpay, rel_alipay;
    private ImageView img_alipay, img_wechat;
    private TextView tv_money1, tv_money2, tv_money3, tv_money4, tv_money5, tv_money6;
    private String choisemoney = "0";
    private String choisemethod = "0";
    private double money = 0;
    private boolean method = false;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.choise_main);
        initView();
    }

    private void initView() {
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        rel_alipay = (RelativeLayout) findViewById(R.id.rel_alipay);
        rel_alipay.setOnClickListener(this);
        rel_wechatpay = (RelativeLayout) findViewById(R.id.rel_wechatpay);
        rel_wechatpay.setOnClickListener(this);
        img_alipay = (ImageView) findViewById(R.id.img_alipay);
        img_wechat = (ImageView) findViewById(R.id.img_wechat);
        tv_money1 = (TextView) findViewById(R.id.tv_money1);
        tv_money2 = (TextView) findViewById(R.id.tv_money2);
        tv_money3 = (TextView) findViewById(R.id.tv_money3);
        tv_money4 = (TextView) findViewById(R.id.tv_money4);
        tv_money5 = (TextView) findViewById(R.id.tv_money5);
        tv_money6 = (TextView) findViewById(R.id.tv_money6);
        tv_money1.setOnClickListener(this);
        tv_money2.setOnClickListener(this);
        tv_money3.setOnClickListener(this);
        tv_money4.setOnClickListener(this);
        tv_money5.setOnClickListener(this);
        tv_money6.setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_money1:
                choisemoney = "1";
                money = 1;
                tv_money1.setBackgroundResource(R.drawable.money_bg1);
                tv_money1.setTextColor(Color.WHITE);
                tv_money2.setBackgroundResource(R.drawable.money_bg);
                tv_money2.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money3.setBackgroundResource(R.drawable.money_bg);
                tv_money3.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money4.setBackgroundResource(R.drawable.money_bg);
                tv_money4.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money5.setBackgroundResource(R.drawable.money_bg);
                tv_money5.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money6.setBackgroundResource(R.drawable.money_bg);
                tv_money6.setTextColor(getResources().getColor(R.color.userlist_gray));
                break;
            case R.id.tv_money2:
                choisemoney = "1";
                money = 2;
                tv_money1.setBackgroundResource(R.drawable.money_bg);
                tv_money1.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money2.setBackgroundResource(R.drawable.money_bg1);
                tv_money2.setTextColor(Color.WHITE);
                tv_money3.setBackgroundResource(R.drawable.money_bg);
                tv_money3.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money4.setBackgroundResource(R.drawable.money_bg);
                tv_money4.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money5.setBackgroundResource(R.drawable.money_bg);
                tv_money5.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money6.setBackgroundResource(R.drawable.money_bg);
                tv_money6.setTextColor(getResources().getColor(R.color.userlist_gray));
                break;
            case R.id.tv_money3:
                choisemoney = "1";
                money = 5;
                tv_money1.setBackgroundResource(R.drawable.money_bg);
                tv_money1.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money3.setBackgroundResource(R.drawable.money_bg1);
                tv_money3.setTextColor(Color.WHITE);
                tv_money2.setBackgroundResource(R.drawable.money_bg);
                tv_money2.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money4.setBackgroundResource(R.drawable.money_bg);
                tv_money4.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money5.setBackgroundResource(R.drawable.money_bg);
                tv_money5.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money6.setBackgroundResource(R.drawable.money_bg);
                tv_money6.setTextColor(getResources().getColor(R.color.userlist_gray));
                break;
            case R.id.tv_money4:
                choisemoney = "1";
                money = 8;
                tv_money1.setBackgroundResource(R.drawable.money_bg);
                tv_money1.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money4.setBackgroundResource(R.drawable.money_bg1);
                tv_money4.setTextColor(Color.WHITE);
                tv_money3.setBackgroundResource(R.drawable.money_bg);
                tv_money3.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money2.setBackgroundResource(R.drawable.money_bg);
                tv_money2.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money5.setBackgroundResource(R.drawable.money_bg);
                tv_money5.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money6.setBackgroundResource(R.drawable.money_bg);
                tv_money6.setTextColor(getResources().getColor(R.color.userlist_gray));
                break;
            case R.id.tv_money5:
                choisemoney = "1";
                money = 10;
                tv_money1.setBackgroundResource(R.drawable.money_bg);
                tv_money1.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money5.setBackgroundResource(R.drawable.money_bg1);
                tv_money5.setTextColor(Color.WHITE);
                tv_money3.setBackgroundResource(R.drawable.money_bg);
                tv_money3.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money4.setBackgroundResource(R.drawable.money_bg);
                tv_money4.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money2.setBackgroundResource(R.drawable.money_bg);
                tv_money2.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money6.setBackgroundResource(R.drawable.money_bg);
                tv_money6.setTextColor(getResources().getColor(R.color.userlist_gray));
                break;
            case R.id.tv_money6:
                choisemoney = "1";
                money = 20;
                tv_money1.setBackgroundResource(R.drawable.money_bg);
                tv_money1.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money6.setBackgroundResource(R.drawable.money_bg1);
                tv_money6.setTextColor(Color.WHITE);
                tv_money3.setBackgroundResource(R.drawable.money_bg);
                tv_money3.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money4.setBackgroundResource(R.drawable.money_bg);
                tv_money4.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money5.setBackgroundResource(R.drawable.money_bg);
                tv_money5.setTextColor(getResources().getColor(R.color.userlist_gray));
                tv_money2.setBackgroundResource(R.drawable.money_bg);
                tv_money2.setTextColor(getResources().getColor(R.color.userlist_gray));
                break;
            case R.id.rel_wechatpay:
                choisemethod = "1";
                method = false;
                img_wechat.setImageResource(R.drawable.contacts_main_select_check);
                img_alipay.setImageResource(R.drawable.contacts_main_select_nor);
                break;
            case R.id.rel_alipay:
                choisemethod = "1";
                method = true;
                img_wechat.setImageResource(R.drawable.contacts_main_select_nor);
                img_alipay.setImageResource(R.drawable.contacts_main_select_check);
                break;
            case R.id.submit:

                if (choisemoney.equals("0")) {
                    Toast.makeText(ChioseMoneyActivity.this, "请选择金额", Toast.LENGTH_LONG).show();
                    return;
                }
                if (choisemethod.equals("0")) {
                    Toast.makeText(ChioseMoneyActivity.this, "请选择支付方式", Toast.LENGTH_LONG).show();
                    return;
                }

                BP.pay("打赏", "打赏捡缘", money, method, new PListener() {
                    @Override
                    public void unknow() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void succeed() {
                        // TODO Auto-generated method stub
                        Toast.makeText(ChioseMoneyActivity.this, "成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void orderId(String arg0) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void fail(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        if (arg0 == -3) {
                            new AlertDialog.Builder(ChioseMoneyActivity.this)
                                    .setMessage(
                                            "监测到你尚未安装支付插件,无法进行微信支付,请选择安装插件(已打包在本地,无流量消耗)")
                                    .setPositiveButton("安装",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    installBmobPayPlugin("bp.db");
                                                }
                                            })
                                    .setNegativeButton("取消",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                }
                                            }).create().show();
                        } else {
                            Toast.makeText(ChioseMoneyActivity.this, arg1,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}