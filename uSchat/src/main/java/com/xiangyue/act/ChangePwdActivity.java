package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.type.User;
import com.xiangyue.util.CountDownButtonHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

//修改密码
public class ChangePwdActivity extends BaseActivity implements OnClickListener {
    private Button btn_bind_phone;
    private EditText et_myphone, ed_num, et_password, et_passwordagin;// 手机号
    private Button btn_regster;
    // 计时器
    private CountDownButtonHelper helper;
    private String phone;
    private Context context;
    private MicroRecruitSettings settings;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.changpwd_main);
        context = this;
        btn_bind_phone = (Button) findViewById(R.id.btn_bind_phone);
        btn_regster = (Button) findViewById(R.id.btn_regster);
        btn_regster.setOnClickListener(this);
        et_myphone = (EditText) findViewById(R.id.et_myphone);
        et_myphone.addTextChangedListener(watcher);
        btn_bind_phone.setOnClickListener(this);
        ed_num = (EditText) findViewById(R.id.ed_num);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passwordagin = (EditText) findViewById(R.id.et_passwordagin);
        SMSSDK.initSDK(this, "64440412a530", "0c3ae6f64163d4c62ffd5be0fb48cf04");
        SMSSDK.registerEventHandler(eventHandler);
        settings = new MicroRecruitSettings(context);
    }

    public void back(View view) {
        finish();
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
                    } else if (!et_password.getText().toString().equals(et_passwordagin.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_LONG).show();
                    } else {
                        SMSSDK.submitVerificationCode("86", et_myphone.getText().toString(), ed_num.getText().toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }

    }

    private String objectId;

    private void toRegister() {
        // 查询账号是否存在,如果存在则返回objectId
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("username", phone);
        bmobQuery.findObjects(ChangePwdActivity.this, new FindListener<User>() {

            @Override
            public void onSuccess(List<User> arg0) {
                // TODO Auto-generated method stub
                User user = arg0.get(0);
                objectId = user.getObjectId();
                // 修改密码
                user.setPassword(et_password.getText().toString());
                user.setSessionToken(user.getToken());
                user.update(context, objectId, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "修改成功,请重新登录", Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub
                        Intent intent = new Intent();
                        intent.putExtra("phone", phone);
                        intent.setClass(ChangePwdActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), "失败:" + arg1, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "该账号未注册:" + arg1, Toast.LENGTH_LONG).show();
                return;
            }
        });

    }


}
