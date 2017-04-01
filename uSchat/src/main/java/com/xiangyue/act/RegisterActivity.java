package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.util.CountDownButtonHelper;
import com.xiangyue.util.SysApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements OnClickListener, Callback {
    private Button btn_bind_phone;
    private EditText et_myphone, ed_num, et_password;// 手机号
    private Button btn_regster;
    // 计时器
    private CountDownButtonHelper helper;
    private String phone;
    private LinearLayout linear_userRead;
    private Context context;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.register_main);
        context = this;
        btn_bind_phone = (Button) findViewById(R.id.btn_bind_phone);
        btn_regster = (Button) findViewById(R.id.btn_regster);
        btn_regster.setOnClickListener(this);
        linear_userRead = (LinearLayout) findViewById(R.id.linear_userRead);
        linear_userRead.setOnClickListener(this);
        et_myphone = (EditText) findViewById(R.id.et_myphone);
        et_myphone.addTextChangedListener(watcher);
        btn_bind_phone.setOnClickListener(this);
        ed_num = (EditText) findViewById(R.id.ed_num);
        et_password = (EditText) findViewById(R.id.et_password);
        SMSSDK.initSDK(this, "64440412a530", "0c3ae6f64163d4c62ffd5be0fb48cf04");
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub
        return false;
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_bind_phone:
                if (BaseApplication.getInstance().isNetworkConnected(context)) {
                    // 发送验证码
                    helper = new CountDownButtonHelper(btn_bind_phone, "重新发送", 60, 1);
                    // helper.setOnFinishListener(onFinishListener);
                    helper.start();
                    SMSSDK.getVerificationCode("86", et_myphone.getText().toString());
                    btn_bind_phone.setEnabled(false);
                } else {
                    Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_regster:
                if (BaseApplication.getInstance().isNetworkConnected(context)) {
                    // 进行下一步
                    if ("".equals(et_password.getText().toString()) || "".equals(ed_num.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "请补全信息", Toast.LENGTH_LONG).show();
                    } else {
                        SMSSDK.submitVerificationCode("86", et_myphone.getText().toString(), ed_num.getText().toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.linear_userRead:
                redictToActivity(RegisterActivity.this, WebpactActivity.class, null);
            default:
                break;
        }

    }

    // 监听
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if (et_myphone.getText().length() == 11) {
                btn_bind_phone.setEnabled(true);
            } else {
                btn_bind_phone.setEnabled(false);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }

    };
    private EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int arg0, int arg1, final Object arg2) {
            switch (arg0) {
                // 获取注册短信验证码
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                    if (SMSSDK.RESULT_COMPLETE == arg1) {
                        // 获取注册短信验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                et_myphone.setEnabled(false);
                                Toast.makeText(getApplicationContext(), "请注意查收", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        // 获取注册短信验证码失败
                        // dismissLoadingDialog();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((Throwable) arg2).printStackTrace();
                                Throwable throwable = (Throwable) arg2;
                                JSONObject object;
                                try {
                                    object = new JSONObject(throwable.getMessage());
                                    String des = object.optString("detail");
                                    // mBtLogin_verify.setEnabled(true);
                                    helper.setNull();
                                    // mEtLogin_userName.setEnabled(true);
                                    if (!TextUtils.isEmpty(des)) {
                                        Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;

                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                    if (SMSSDK.RESULT_COMPLETE == arg1) {
                        Map<String, Object> result = (Map<String, Object>) arg2;
                        phone = (String) result.get("phone");
                        toRegister();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((Throwable) arg2).printStackTrace();
                                Throwable throwable = (Throwable) arg2;
                                JSONObject object;
                                try {
                                    object = new JSONObject(throwable.getMessage());
                                    String des = object.optString("detail");
                                    if (!TextUtils.isEmpty(des)) {
                                        Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;

                default:
                    break;
            }
        }
    };


    private void toRegister() {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("pwd", et_password.getText().toString());
        intent.setClass(RegisterActivity.this, ChoiceRoleActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
