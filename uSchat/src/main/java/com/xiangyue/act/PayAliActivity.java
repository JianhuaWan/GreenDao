package com.xiangyue.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.provider.RefreshMeInfoEvent;
import com.xiangyue.type.User;
import com.xiangyue.weight.LoginDialog;

import cn.bmob.v3.listener.UpdateListener;

/**
 * 支付帐号管理
 *
 * @author Administrator
 */
public class PayAliActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout liner_nodata;
    private ImageView img_addcode;
    private LinearLayout tips, li_content;
    private TextView tv_code;
    private MicroRecruitSettings settings;
    private TextView tv_nick;
    private ImageView img_head;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.my_pay_main);
        setTools();
        initView();
        setViewGoneOrVisibility();
        setLickListener();
    }

    private void setTools() {
        BusProvider.getInstance().register(PayAliActivity.this);
        dialog = new LoginDialog(PayAliActivity.this, "");
        dialog.setCanceledOnTouchOutside(false);
        settings = new MicroRecruitSettings(PayAliActivity.this);
    }

    private void setLickListener() {
        img_addcode.setOnClickListener(this);
    }

    private void setViewGoneOrVisibility() {
        if (getIntent().getStringExtra("type").equals("1")) {
            liner_nodata.setVisibility(View.GONE);
            img_addcode.setVisibility(View.GONE);
            tips.setVisibility(View.VISIBLE);
            li_content.setVisibility(View.VISIBLE);
            setDate();
        } else if (getIntent().getStringExtra("type").equals("0")) {
            liner_nodata.setVisibility(View.VISIBLE);
            img_addcode.setVisibility(View.VISIBLE);
            tips.setVisibility(View.GONE);
            li_content.setVisibility(View.GONE);
        }
    }

    private void setDate() {
        tv_code.setText(getIntent().getStringExtra("code"));
        tv_nick.setText(getIntent().getStringExtra("nick"));
//        Picasso.with(PayAliActivity.this).load(settings.HEADICON.getValue().toString()).error(R.drawable.default_head).placeholder(R.drawable.default_head).into(img_head);
        ViewUtil.setPicture(PayAliActivity.this, settings.HEADICON.getValue().toString(), R.drawable.default_head, img_head, null);
    }

    public void back(View view) {
        finish();
    }

    public void initView() {
        img_head = (ImageView) findViewById(R.id.img_head);
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        liner_nodata = (LinearLayout) findViewById(R.id.liner_nodata);
        img_addcode = (ImageView) findViewById(R.id.img_addcode);
        tips = (LinearLayout) findViewById(R.id.tips);
        li_content = (LinearLayout) findViewById(R.id.li_content);
        tv_code = (TextView) findViewById(R.id.tv_code);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_addcode) {
            Intent intent = new Intent();
            intent.setClass(PayAliActivity.this, EditPayAliActivity.class);
            startActivityForResult(intent, 1032);
        }
    }

    LoginDialog dialog;
    private String code = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != getActivity().RESULT_OK)
            return;
        switch (requestCode) {
            case 1032:
                dialog.show();
                //提交支付宝账号
                User user = new User();
                code = data.getStringExtra("code");
                user.setAlicode(data.getStringExtra("code"));
                user.setAlicodestate("1");
                user.update(PayAliActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {
                    @Override
                    public void onSuccess() {

                        dialog.dismiss();
                        Toast.makeText(PayAliActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                        changeView();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        dialog.dismiss();
                        Toast.makeText(PayAliActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            default:
                break;
        }
    }

    private void changeView() {
        liner_nodata.setVisibility(View.GONE);
        img_addcode.setVisibility(View.GONE);
        tips.setVisibility(View.VISIBLE);
        li_content.setVisibility(View.VISIBLE);
        tv_code.setText(code);
        tv_nick.setText(getIntent().getStringExtra("nick"));
//        Picasso.with(PayAliActivity.this).load(settings.HEADICON.getValue().toString()).error(R.drawable.default_head).placeholder(R.drawable.default_head).into(img_head);
        ViewUtil.setPicture(PayAliActivity.this, settings.HEADICON.getValue().toString(), R.drawable.default_head, img_head, null);
        BusProvider.getInstance().post(new RefreshMeInfoEvent());
    }
}
