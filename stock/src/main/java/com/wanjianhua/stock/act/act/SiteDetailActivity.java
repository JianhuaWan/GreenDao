package com.wanjianhua.stock.act.act;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.base.BaseActivity;
import com.wanjianhua.stock.act.bean.SiteInfo;
import com.wanjianhua.stock.act.bean.Updateprice;
import com.wanjianhua.stock.act.utils.RefreshMessage;
import com.wanjianhua.stock.act.utils.RxBus;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.wanjianhua.stock.R.string.nowprice;
import static com.wanjianhua.stock.R.string.site;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class SiteDetailActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout linear_detail;
    private View childView, emtpylayout;
    private TextView tv_levelchild, tv_waveband, tv_waveband_update_price;
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
    private Float diefu1_1, diefu1_2, diefu2_0, diefu2_1, diefu2_2, diefu3_0, diefu3_1, diefu3_2;
    private String objectid;

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
        if (diefu3_2 != null) {
            Float diefu = diefu3_2 * 100;
            tv_appreciate.setText("-" + diefu + "%");
        }
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
        name.setText(siteInfo.getName() + "(" + siteInfo.getCode() + ")");
        String tem = ((int) (totalprice / price)) + "";
        tv_totalcount.setText(tem.substring(0, tem.length() - 2) + "00");
    }

    private int toatlbalance = 0;
    private String updateprice;

    private void getIntentData() {
        siteInfo = (SiteInfo) getIntent().getSerializableExtra("info");
        totalprice = Integer.parseInt(siteInfo.getTotalprice());
        updateprice = siteInfo.getUpdateprice();
        price = Float.parseFloat(siteInfo.getSingleprice());
        balance = siteInfo.getBalance();
        objectid = siteInfo.getObjid();
        String[] temp = balance.split(":");
        balancearray[0] = Integer.parseInt(temp[0]);
        balancearray[1] = Integer.parseInt(temp[1]);
        balancearray[2] = Integer.parseInt(temp[2]);
        toatlbalance = balancearray[0] + balancearray[1] + balancearray[2];
        Updateprice mupdateprice = null;
        if (updateprice != null) {
            if (!updateprice.equals("")) {
                String[] tempupdate = updateprice.split(";");
                for (int i = 0; i < tempupdate.length; i++) {
                    String singleupdate = tempupdate[i];
                    String[] tempover = singleupdate.split(",");
                    mupdateprice = new Updateprice();
                    mupdateprice.setI(tempover[0]);
                    mupdateprice.setJ(tempover[1]);
                    mupdateprice.setUpprice(tempover[2]);
                    updateprices.add(mupdateprice);
                }
            }
        }

    }

    public List<Updateprice> updateprices = new ArrayList<>();

    /**
     * 每一波段信息
     */
    private TextView tv_nowprice, tv_nowappreciate, singlecount, tv_singleprice, maxwin, nowtv_maxloser;
    private LinearLayout linear_top;

    private synchronized void initView() {
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
                tv_waveband_update_price = (TextView) childView.findViewById(R.id.tv_waveband_update_price);
                if (i == 0 && j == 0) {
                    tv_waveband_update_price.setVisibility(View.GONE);
                }
                final int finalI = i;
                final int finalJ = j;
                tv_waveband_update_price.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //修改当前买入价格,自动计算跌幅
                        final EditText inputServer = new EditText(SiteDetailActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(SiteDetailActivity.this);
                        builder.setTitle(getString(R.string.updateprice)).setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                                .setNegativeButton(getString(R.string.cancle), null);
                        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                if (inputServer.getText().toString().trim().equals("")) {
                                    return;
                                }
                                refreshData(finalI, finalJ, inputServer.getText().toString());
                            }
                        });
                        builder.show();
                    }
                });
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
                    if (j == 0) {
                        //默认比例1:1:3()
                        float nowprice = (float) (price * (1 - 0.000));
                        //构造方法的字符格式这里如果小数不足2位,会以0补足.
//                        tv_nowprice.setText(decimalFormat.format(nowprice));
                        tv_nowprice.setText(nowprice + "");
                        tv_nowappreciate.setText("-0%");
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
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("0") && updateprices.get(k).getJ().equals("1")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    tv_waveband.setText(getString(R.string.wareband2));
                                    linear_top.setVisibility(View.GONE);
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu1_1 = (1 - (updateprice / price));
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
                                    maxwin.setText(win1_2 + "元");
                                    nowtv_maxloser.setText(win1_1 + "元");
                                    break;
                                } else {
                                    tv_waveband.setText(getString(R.string.wareband2));
                                    linear_top.setVisibility(View.GONE);
                                    float nowprice = (float) (price * (1 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    diefu1_1 = Float.parseFloat("0.033");
                                    tv_nowappreciate.setText("-3.3%");
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
                                    maxwin.setText(win1_2 + "元");
                                    nowtv_maxloser.setText(win1_1 + "元");
                                }
                            }
                        } else {
                            tv_waveband.setText(getString(R.string.wareband2));
                            linear_top.setVisibility(View.GONE);
                            float nowprice = (float) (price * (1 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            tv_nowappreciate.setText("-3.3%");
                            diefu1_1 = Float.parseFloat("0.033");
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
                            maxwin.setText(win1_2 + "元");
                            nowtv_maxloser.setText(win1_1 + "元");
                        }
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        linear_top.setVisibility(View.GONE);
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("0") && updateprices.get(k).getJ().equals("2")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");

                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu1_2 = (1 - (updateprice / price));
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
                                    maxwin.setText(win1_3 + "元");
                                    nowtv_maxloser.setText(win1_2 + "元");
                                    break;
                                } else {
                                    float nowprice = (float) (price * (1 - diefu1_1 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (nowprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu1_2 = diefu / 100;
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
                                    maxwin.setText(win1_3 + "元");
                                    nowtv_maxloser.setText(win1_2 + "元");
                                }
                            }
                        } else {
                            float nowprice = (float) (price * (1 - diefu1_1 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            Float diefu = (1 - (nowprice / price)) * 100;
                            tv_nowappreciate.setText("-" + diefu + "%");
                            diefu1_2 = diefu / 100;
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
                            maxwin.setText(win1_3 + "元");
                            nowtv_maxloser.setText(win1_2 + "元");
                        }
                    }
                    tv_levelchild.setText(getString(R.string.level1));
                } else if (i == 1) {
                    if (j == 0) {
                        //默认比例1:1:3
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("1") && updateprices.get(k).getJ().equals("0")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");


                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu2_0 = (1 - (updateprice / price));
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
                                    maxwin.setText(win2_1 + "元");
                                    nowtv_maxloser.setText(win1_3 + "元");
                                    tv_waveband.setText(getString(R.string.wareband1));
                                    break;
                                } else {
                                    float nowprice = (float) (price * (1 - diefu1_2 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (nowprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu2_0 = diefu / 100;
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
                                    maxwin.setText(win2_1 + "元");
                                    nowtv_maxloser.setText(win1_3 + "元");
                                    tv_waveband.setText(getString(R.string.wareband1));
                                }
                            }
                        } else {
                            float nowprice = (float) (price * (1 - diefu1_2 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            Float diefu = (1 - (nowprice / price)) * 100;
                            tv_nowappreciate.setText("-" + diefu + "%");
                            diefu2_0 = diefu / 100;
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
                            maxwin.setText(win2_1 + "元");
                            nowtv_maxloser.setText(win1_3 + "元");
                            tv_waveband.setText(getString(R.string.wareband1));
                        }
                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        linear_top.setVisibility(View.GONE);
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("1") && updateprices.get(k).getJ().equals("1")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu2_1 = (1 - (updateprice / price));
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
                                    maxwin.setText(win2_2 + "元");
                                    nowtv_maxloser.setText(win2_1 + "元");
                                    break;
                                } else {
                                    float nowprice = (float) (price * (1 - diefu2_0 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (nowprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu2_1 = diefu / 100;
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
                                    maxwin.setText(win2_2 + "元");
                                    nowtv_maxloser.setText(win2_1 + "元");
                                }
                            }
                        } else {
                            float nowprice = (float) (price * (1 - diefu2_0 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            Float diefu = (1 - (nowprice / price)) * 100;
                            tv_nowappreciate.setText("-" + diefu + "%");
                            diefu2_1 = diefu / 100;
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
                            maxwin.setText(win2_2 + "元");
                            nowtv_maxloser.setText(win2_1 + "元");
                        }
                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        linear_top.setVisibility(View.GONE);
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("1") && updateprices.get(k).getJ().equals("2")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu2_2 = (1 - (updateprice / price));
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
                                    maxwin.setText(win2_3 + "元");
                                    nowtv_maxloser.setText(win2_2 + "元");
                                    break;
                                } else {
                                    float nowprice = (float) (price * (1 - diefu2_1 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (nowprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu2_2 = diefu / 100;
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
                                    maxwin.setText(win2_3 + "元");
                                    nowtv_maxloser.setText(win2_2 + "元");
                                }
                            }
                        } else {
                            float nowprice = (float) (price * (1 - diefu2_1 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            Float diefu = (1 - (nowprice / price)) * 100;
                            tv_nowappreciate.setText("-" + diefu + "%");
                            diefu2_2 = diefu / 100;
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
                            maxwin.setText(win2_3 + "元");
                            nowtv_maxloser.setText(win2_2 + "元");
                        }
                    }
                    tv_levelchild.setText(getString(R.string.level2));
                } else if (i == 2) {
                    if (j == 0) {
                        tv_waveband.setText(getString(R.string.wareband1));
                        //默认比例1:1:3
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("2") && updateprices.get(k).getJ().equals("0")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu3_0 = (1 - (updateprice / price));
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
                                    maxwin.setText(win3_1 + "元");
                                    nowtv_maxloser.setText(win2_3 + "元");
                                    break;
                                } else {
                                    float nowprice = (float) (price * (1 - diefu2_2 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (nowprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu3_0 = diefu / 100;
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
                                    maxwin.setText(win3_1 + "元");
                                    nowtv_maxloser.setText(win2_3 + "元");
                                }
                            }
                        } else {
                            float nowprice = (float) (price * (1 - diefu2_2 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            Float diefu = (1 - (nowprice / price)) * 100;
                            tv_nowappreciate.setText("-" + diefu + "%");
                            diefu3_0 = diefu / 100;
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
                            maxwin.setText(win3_1 + "元");
                            nowtv_maxloser.setText(win2_3 + "元");
                        }

                    } else if (j == 1) {
                        tv_waveband.setText(getString(R.string.wareband2));
                        linear_top.setVisibility(View.GONE);
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("2") && updateprices.get(k).getJ().equals("1")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu3_1 = (1 - (updateprice / price));
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
                                    maxwin.setText(win3_2 + "元");
                                    nowtv_maxloser.setText(win3_1 + "元");
                                    break;
                                } else {
                                    float nowprice = (float) (price * (1 - diefu3_0 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (nowprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu3_1 = diefu / 100;
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
                                    maxwin.setText(win3_2 + "元");
                                    nowtv_maxloser.setText(win3_1 + "元");
                                }
                            }
                        } else {
                            float nowprice = (float) (price * (1 - diefu3_0 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            Float diefu = (1 - (nowprice / price)) * 100;
                            tv_nowappreciate.setText("-" + diefu + "%");
                            diefu3_1 = diefu / 100;
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
                            maxwin.setText(win3_2 + "元");
                            nowtv_maxloser.setText(win3_1 + "元");
                        }

                    } else if (j == 2) {
                        tv_waveband.setText(getString(R.string.wareband3));
                        linear_top.setVisibility(View.GONE);
                        if (updateprices.size() > 0) {
                            for (int k = 0; k < updateprices.size(); k++) {
                                if (updateprices.get(k).getI().equals("2") && updateprices.get(k).getJ().equals("2")) {
                                    Float updateprice = Float.parseFloat(updateprices.get(k).getUpprice());
                                    float nowprice = updateprice;
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (updateprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu3_2 = (1 - (updateprice / price));
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
                                    maxwin.setText(win3_3 + "元");
                                    nowtv_maxloser.setText(win3_2 + "元");
                                    break;
                                } else {

                                    float nowprice = (float) (price * (1 - diefu3_1 - 0.033));
                                    tv_nowprice.setText(nowprice + "");
                                    Float diefu = (1 - (nowprice / price)) * 100;
                                    tv_nowappreciate.setText("-" + diefu + "%");
                                    diefu3_2 = diefu / 100;
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
                                    maxwin.setText(win3_3 + "元");
                                    nowtv_maxloser.setText(win3_2 + "元");
                                }
                            }
                        } else {
                            float nowprice = (float) (price * (1 - diefu3_1 - 0.033));
                            tv_nowprice.setText(nowprice + "");
                            Float diefu = (1 - (nowprice / price)) * 100;
                            tv_nowappreciate.setText("-" + diefu + "%");
                            diefu3_2 = diefu / 100;
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
                            maxwin.setText(win3_3 + "元");
                            nowtv_maxloser.setText(win3_2 + "元");
                        }

                    }
                    tv_levelchild.setText(getString(R.string.level3));
                }
            }

        }
        emtpylayout = LayoutInflater.from(SiteDetailActivity.this).inflate(
                R.layout.empty, null);
        linear_detail.addView(emtpylayout, 12);
    }

    private void refreshData(int i, int j, String updateprice) {
        SiteInfo siteInfo = new SiteInfo();
        StringBuffer stringBuffer = new StringBuffer();
        boolean hasvalue = false;
        //判断原有数据是否有值,如果有值,继续判断是添加数据,还是替换数据.
        if (updateprices.size() > 0) {
            for (int m = 0; m < updateprices.size(); m++) {
                if (updateprices.get(m).getI().equals("" + i) && updateprices.get(m).getJ().equals("" + j)) {
                    //这里是替换数据
                    hasvalue = true;
                    updateprices.get(m).setUpprice(updateprice);
                }
            }
            if (hasvalue) {
                //更改数据
                for (int m = 0; m < updateprices.size(); m++) {
                    stringBuffer.append(updateprices.get(m).getI() + "," + updateprices.get(m).getJ() + "," + updateprices.get(m).getUpprice() + ";");
                }
            } else {
                //添加数据
                for (int m = 0; m < updateprices.size(); m++) {
                    stringBuffer.append(updateprices.get(m).getI() + "," + updateprices.get(m).getJ() + "," + updateprices.get(m).getUpprice() + ";" + i + "," + j + "," + updateprice + ";");
                }
            }
            siteInfo.setUpdateprice(stringBuffer.toString());
        } else {
            siteInfo.setUpdateprice(i + "," + j + "," + updateprice + ";");
        }
        siteInfo.update(objectid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    RxBus.getInstance().post(new RefreshMessage());
                    finish();
                    Toast.makeText(SiteDetailActivity.this, "更改成功!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SiteDetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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
