package com.wanjianhua.stock.act.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.base.BaseActivity;
import com.wanjianhua.stock.act.bean.SiteInfo;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteDetailActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout linear_detail;
    private View childView, emtpylayout;
    private TextView tv_levelchild, tv_waveband;
    private SiteInfo siteInfo;
    private TextView name;
    private TextView tv_totalprice, tv_appreciate, tv_totalcount, tv_maxloser, tv_wintotal;
    private ImageView img_back;
    private int totalprice;
    private float price;//原始价格
    DecimalFormat decimalFormat = new DecimalFormat(".00");
    private String loser1_1, loser1_2, loser1_3;
    private String win1_1, win1_2, win1_3;
    private String win2_1, win2_2, win2_3;
    private String win3_1, win3_2, win3_3;
    private String balance;
    private int[] balancearray = new int[3];
    private TextView tv_balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_detail);
        getIntentData();
        initView();
        setView();
        setLoserandWin();
    }

    private void setLoserandWin() {
        tv_maxloser.setText(win3_2 + "元");
        tv_wintotal.setText(win3_3 + "元");
    }

    private void setView() {
        String overprice = "";
        Float temp = Float.parseFloat(siteInfo.getTotalprice()) / 10000;
        if ((temp + "").contains(".")) {
            String[] tempprice = (temp + "").split("\\.");
            if (tempprice[1].startsWith("0")) {
                overprice = tempprice[0];
            } else {
                overprice = temp + "";
            }
        }
        tv_totalprice.setText(overprice + "W");
        tv_appreciate.setText("30%");
        name.setText(siteInfo.getName() + "(" + siteInfo.getCode() + ")");
        String tem = ((int) (totalprice / price)) + "";
        tv_totalcount.setText(tem.substring(0, tem.length() - 2) + "00");
    }

    private int toatlbalance = 0;

    private void getIntentData() {
        siteInfo = (SiteInfo) getIntent().getSerializableExtra("info");
        totalprice = Integer.parseInt(siteInfo.getTotalprice());
        price = Float.parseFloat(siteInfo.getSingleprice());
        balance = siteInfo.getBalance();
        String[] temp = balance.split(":");
        balancearray[0] = Integer.parseInt(temp[0]);
        balancearray[1] = Integer.parseInt(temp[1]);
        balancearray[2] = Integer.parseInt(temp[2]);
        toatlbalance = balancearray[0] + balancearray[1] + balancearray[2];

    }

    /**
     * 每一波段信息
     */
    private TextView tv_nowprice, tv_nowappreciate, singlecount, tv_singleprice, maxwin, nowtv_maxloser;
    private LinearLayout linear_top;

    private void initView() {
        tv_wintotal = (TextView) findViewById(R.id.tv_wintotal);
        name = (TextView) findViewById(R.id.name);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tv_totalprice = (TextView) findViewById(R.id.tv_totalprice);
        tv_appreciate = (TextView) findViewById(R.id.tv_appreciate);
        tv_totalcount = (TextView) findViewById(R.id.tv_totalcount);
        tv_maxloser = (TextView) findViewById(R.id.tv_maxloser);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_balance.setText(balance);
        linear_detail = (LinearLayout) findViewById(R.id.linear_detail);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                childView = LayoutInflater.from(SiteDetailActivity.this).inflate(
                        R.layout.detail_child, null);
                tv_levelchild = (TextView) childView.findViewById(R.id.tv_level);
                tv_waveband = (TextView) childView.findViewById(R.id.tv_waveband);
                //==============================================================
                tv_nowprice = (TextView) childView.findViewById(R.id.tv_nowprice);
                linear_top = (LinearLayout) childView.findViewById(R.id.linear_top);
                tv_nowappreciate = (TextView) childView.findViewById(R.id.tv_nowappreciate);
                singlecount = (TextView) childView.findViewById(R.id.singlecount);
                tv_singleprice = (TextView) childView.findViewById(R.id.tv_singleprice);
                maxwin = (TextView) childView.findViewById(R.id.maxwin);
                nowtv_maxloser = (TextView) childView.findViewById(R.id.tv_nowmaxloser);
                //===============================================================
                linear_detail.addView(childView);
                if (i == 0) {
                    tv_levelchild.setText(getString(R.string.level1));
                    if (j == 0) {
                        //默认比例1:1:3()
                        float nowprice = (float) (price * (1 - 0.033));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-3.3%");
//                        int count = (int) (totalprice /5 / 5 / nowprice);//改动2017年5月25日 14:58:41
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[0] * balancearray[0] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[0] * balancearray[0] + "");
                        win1_1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
//                        if (win1_1.contains(".")) {
//                            String temps[] = win1_1.split("\\.");
//                            win1_1 = temps[0];
//                        }
                        maxwin.setText(win1_1 + "元");
                        nowtv_maxloser.setText(0 + "元");
                        tv_waveband.setText(getString(R.string.wareband1));
                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        linear_top.setVisibility(View.GONE);
                        float nowprice = (float) (price * (1 - 0.066));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-6.6%");
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[0] * balancearray[1] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win1_2 = (Float.parseFloat(tem1) + Float.parseFloat(win1_1)) + "";
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[0] * balancearray[1] + "");
//                        if (win1_2.contains(".")) {
//                            String tems[] = win1_2.split("\\.");
//                            win1_2 = tems[0];
//                        }
                        maxwin.setText(win1_2 + "元");
                        nowtv_maxloser.setText(win1_1 + "元");
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        linear_top.setVisibility(View.GONE);
                        float nowprice = (float) (price * (1 - 0.099));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-9.9%");
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[0] * balancearray[2] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[0] * balancearray[2] + "");
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win1_3 = (Float.parseFloat(tem1) + Float.parseFloat(win1_2)) + "";
//                        if (win1_3.contains(".")) {
//                            String temps[] = win1_3.split("\\.");
//                            win1_3 = temps[0];
//                        }
                        maxwin.setText(win1_3 + "元");
                        nowtv_maxloser.setText(win1_2 + "元");
                    }
                } else if (i == 1) {
                    tv_levelchild.setText(getString(R.string.level2));
                    if (j == 0) {
                        //默认比例1:1:3
                        float nowprice = (float) (price * (1 - 0.033 - 0.099));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-13.2%");

                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[1] * balancearray[0] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[1] * balancearray[0] + "");
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win2_1 = (Float.parseFloat(tem1) + Float.parseFloat(win1_3)) + "";
//                        if (win2_1.contains(".")) {
//                            String tems[] = win2_1.split("\\.");
//                            win2_1 = tems[0];
//                        }
                        maxwin.setText(win2_1 + "元");
                        nowtv_maxloser.setText(win1_3 + "元");
                        tv_waveband.setText(getString(R.string.wareband1));
                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        linear_top.setVisibility(View.GONE);
                        float nowprice = (float) (price * (1 - 0.066 - 0.099));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-19.8%");
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[1] * balancearray[1] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win2_2 = (Float.parseFloat(tem1) + Float.parseFloat(win2_1)) + "";
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[1] * balancearray[1] + "");
//                        if (win2_2.contains(".")) {
//                            String tems[] = win2_2.split("\\.");
//                            win2_2 = tems[0];
//                        }
                        maxwin.setText(win2_2 + "元");
                        nowtv_maxloser.setText(win2_1 + "元");
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        linear_top.setVisibility(View.GONE);
                        float nowprice = (float) (price * (1 - 0.099 - 0.099));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-29.7%");
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[1] * balancearray[2] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[1] * balancearray[2] + "");
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win2_3 = (Float.parseFloat(tem1) + Float.parseFloat(win2_2)) + "";
//                        if (win2_3.contains(".")) {
//                            String tems[] = win2_3.split("\\.");
//                            win2_3 = tems[0];
//                        }
                        maxwin.setText(win2_3 + "元");
                        nowtv_maxloser.setText(win2_2 + "元");

                    }
                } else if (i == 2) {
                    tv_levelchild.setText(getString(R.string.level3));
                    if (j == 0) {
                        tv_waveband.setText(getString(R.string.wareband1));
                        //默认比例1:1:3
                        float nowprice = (float) (price * (1 - 0.033 - 0.198));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-33%");
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[2] * balancearray[0] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[2] * balancearray[0] + "");
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win3_1 = (Float.parseFloat(tem1) + Float.parseFloat(win2_3)) + "";
//                        if (win3_1.contains(".")) {
//                            String tems[] = win3_1.split("\\.");
//                            win3_1 = tems[0];
//                        }
                        maxwin.setText(win3_1 + "元");
                        nowtv_maxloser.setText(win2_3 + "元");
                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        linear_top.setVisibility(View.GONE);
                        float nowprice = (float) (price * (1 - 0.066 - 0.198));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-39.6%");
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[2] * balancearray[1] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win3_2 = (Float.parseFloat(tem1) + Float.parseFloat(win3_1)) + "";
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[2] * balancearray[1] + "");
//                        if (win3_2.contains(".")) {
//                            String tems[] = win3_2.split("\\.");
//                            win3_2 = tems[0];
//                        }
                        maxwin.setText(win3_2 + "元");
                        nowtv_maxloser.setText(win3_1 + "元");
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        linear_top.setVisibility(View.GONE);
                        float nowprice = (float) (price * (1 - 0.099 - 0.198));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-49.5%");
                        int count = (int) (totalprice / toatlbalance / toatlbalance * balancearray[2] * balancearray[2] / nowprice);
                        String temp = count + "";
                        String overcount = temp.substring(0, temp.length() - 2);
                        if (count < 100) {
                            singlecount.setText(count + getString(R.string.no100));
                        } else {
                            singlecount.setText(overcount + "00");
                        }
                        tv_singleprice.setText(totalprice / toatlbalance / toatlbalance * balancearray[2] * balancearray[2] + "");
                        String tem1 = (price - nowprice) * Integer.parseInt(overcount + "00") + "";
                        win3_3 = (Float.parseFloat(tem1) + Float.parseFloat(win3_2)) + "";
//                        if (win3_3.contains(".")) {
//                            String tems[] = win3_3.split("\\.");
//                            win3_3 = tems[0];
//                        }
                        maxwin.setText(win3_3 + "元");
                        nowtv_maxloser.setText(win3_2 + "元");
                    }
                }
            }

        }
        emtpylayout = LayoutInflater.from(SiteDetailActivity.this).inflate(
                R.layout.empty, null);
        linear_detail.addView(emtpylayout, 12);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
