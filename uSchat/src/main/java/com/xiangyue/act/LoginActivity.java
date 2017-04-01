package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.type.User;
import com.xiangyue.util.SysApplication;
import com.xiangyue.weight.LoginDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private Button btn_login;
    private EditText editpwd, editphone;
    private ImageView img_head;
    private LoginDialog loginDialog;
    private Context context;
    private MicroRecruitSettings settings;
    private TextView tv_unpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_loginfirst);
        context = this;
        settings = new MicroRecruitSettings(context);
        img_head = (ImageView) findViewById(R.id.img_head);
        btn_login = (Button) findViewById(R.id.btn_login);
        editpwd = (EditText) findViewById(R.id.editpwd);
        editphone = (EditText) findViewById(R.id.editphone);
        editphone.addTextChangedListener(mTextWatcher);// 后续扩展
        btn_login.setOnClickListener(this);
        loginDialog = new LoginDialog(LoginActivity.this, "");
        loginDialog.setCanceledOnTouchOutside(false);
        tv_unpwd = (TextView) findViewById(R.id.tv_unpwd);
        tv_unpwd.setOnClickListener(this);
        if (getIntent().hasExtra("phone")) {
            editphone.setText(getIntent().getStringExtra("phone"));
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unpwd:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ChangePwdActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (BaseApplication.getInstance().isNetworkConnected(context)) {
                    // TODO Auto-generated method stub
                    loginDialog.show();
                    final User user = new User();
                    user.setUsername(editphone.getText().toString());
                    user.setPassword(editpwd.getText().toString());
                    user.login(this, new SaveListener() {

                        @Override
                        public void onSuccess() {
                            settings.PWD.setValue(editpwd.getText().toString());
                            token = user.getSessionToken();
                            objectId = user.getObjectId();
                            settings.OBJECT_ID.setValue(objectId);
                            toSaveToken();
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            loginDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "用户名密码不正确", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }

    }

    public void toSaveToken() {
        User user = new User();
        user.setToken(token);
        user.update(LoginActivity.this, objectId, new UpdateListener() {

            @Override
            public void onSuccess() {
                settings.isFirstLogin.setValue(false);
//                redictToActivity(LoginActivity.this, MainActivity.class, null);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                loginDialog.dismiss();
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(LoginActivity.this, "保存token失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String objectId;
    private String token;
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (BaseApplication.getInstance().isNetworkConnected(context)) {
                // TODO Auto-generated method stub
                if (temp.length() == 11) {
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.addWhereEqualTo("username", editphone.getText().toString());
                    // 执行查询方法
                    query.findObjects(getApplicationContext(), new FindListener<User>() {

                        @Override
                        public void onError(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            @SuppressWarnings("unused")
                            String sd = arg1;
                            Toast.makeText(getApplicationContext(), arg1, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(List<User> arg0) {
                            if (arg0.size() != 0) {
//                                Picasso.with(getApplicationContext())
//                                        .load(arg0.get(0).getHeadImage().getFileUrl(getApplicationContext()))
//                                        .placeholder(R.drawable.chat_photo).error(R.drawable.chat_photo).into(img_head);
                                ViewUtil.setPicture(LoginActivity.this, arg0.get(0).getHeadImage().getFileUrl(getApplicationContext()), R.drawable.default_head, img_head, null);
                                MicroRecruitSettings settings = new MicroRecruitSettings(context);
                                settings.phone.setValue(arg0.get(0).getUsername());
                                settings.HEADICON.setValue(arg0.get(0).getHeadImage()
                                        .getFileUrl(getApplicationContext()));
                            }
                        }

                    });
                }
            } else {
                Toast.makeText(getApplicationContext(), "网络未连接", Toast.LENGTH_LONG).show();
            }

        }
    };

}
