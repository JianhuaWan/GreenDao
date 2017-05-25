package com.wanjianhua.stock.act.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wanjianhua.stock.R;
import com.wanjianhua.stock.act.base.BaseActivity;
import com.wanjianhua.stock.act.bean.SiteInfo;
import com.wanjianhua.stock.act.utils.ActionSheetDialog;
import com.wanjianhua.stock.act.utils.MicroRecruitSettings;
import com.wanjianhua.stock.act.utils.RefreshBalance;
import com.wanjianhua.stock.act.utils.RefreshMessage;
import com.wanjianhua.stock.act.utils.RxBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscription;
import rx.functions.Action1;

import static com.wanjianhua.stock.R.id.list_info;

/**
 * Created by wanjianhua on 2017/4/5.
 */

public class AddSiteActivity extends BaseActivity implements View.OnClickListener {
    private View childView;
    private LinearLayout linear_detail;
    private TextView tv_waveband, tv_levelchild;
    private ImageView img_back;
    private TextView tv_add;
    private EditText tv_name, tv_code, tv_point_transfrom, tv_totalPrice;
    private TextView tv_appreciate;
    private MicroRecruitSettings settings;
    private LinearLayout linear_balance;
    private TextView tv_pricebalance;
    Subscription rxbus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsite_main);
        settings = new MicroRecruitSettings(this);
        initView();
        setLisenr();
    }

    private void initView() {
        tv_name = (EditText) findViewById(R.id.tv_name);
        tv_code = (EditText) findViewById(R.id.tv_code);
        tv_point_transfrom = (EditText) findViewById(R.id.tv_point_transfrom);
        tv_totalPrice = (EditText) findViewById(R.id.tv_totalPrice);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        linear_detail = (LinearLayout) findViewById(R.id.linear_detail);
        tv_appreciate = (TextView) findViewById(R.id.tv_appreciate);
        linear_balance = (LinearLayout) findViewById(R.id.linear_balance);
        linear_balance.setOnClickListener(this);
        tv_pricebalance = (TextView) findViewById(R.id.tv_pricebalance);
    }

    private void setLisenr() {
        rxbus = RxBus.getInstance().toObserverable(RefreshBalance.class)
                .subscribe(new Action1<RefreshBalance>() {
                    @Override
                    public void call(RefreshBalance refreshMessage) {
                        tv_pricebalance.setText(refreshMessage.getBalance1() + ":" + refreshMessage.getBalance2() + ":" + refreshMessage.getBalance3());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_add:
                if (settings.PHONE.getValue().equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(AddSiteActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (tv_name.getText().toString().isEmpty() || tv_code.getText().toString().isEmpty() || tv_totalPrice.getText().toString().isEmpty() || tv_point_transfrom.getText().toString().isEmpty()) {
                        Toast.makeText(AddSiteActivity.this, getString(R.string.infonotwrite), Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        //添加一行数据
                        SiteInfo siteInfo = new SiteInfo();
                        siteInfo.setPhone(settings.PHONE.getValue().toString());
                        siteInfo.setName(tv_name.getText().toString());
                        siteInfo.setCode(tv_code.getText().toString());
                        siteInfo.setSingleprice(tv_point_transfrom.getText().toString());
                        siteInfo.setTotalprice(tv_totalPrice.getText().toString());
                        siteInfo.setBalance(tv_pricebalance.getText().toString());
                        siteInfo.setAppreciate("-30%");
                        siteInfo.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    RxBus.getInstance().post(new RefreshMessage());
                                    Toast.makeText(AddSiteActivity.this, getString(R.string.savepass), Toast.LENGTH_LONG).show();
                                    tv_name.setText("");
                                    tv_code.setText("");
                                    tv_point_transfrom.setText("");
                                    tv_totalPrice.setText("");
                                } else {
                                    Toast.makeText(AddSiteActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
                break;
            case R.id.linear_balance:
                Intent intent = new Intent();
                intent.setClass(AddSiteActivity.this, BalanceDialog.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
