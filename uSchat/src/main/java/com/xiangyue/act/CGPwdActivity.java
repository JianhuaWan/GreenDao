package com.xiangyue.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.type.User;

import cn.bmob.v3.listener.UpdateListener;

public class CGPwdActivity extends BaseActivity implements OnClickListener {
    private EditText fpwd, tpwd;
    private Button btn_submit;
    private MicroRecruitSettings settings;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.cgpwd_main);
        initView();
        initSet();
    }

    public void back(View v) {
        finish();
    }

    public void initSet() {
        settings = new MicroRecruitSettings(CGPwdActivity.this);
    }

    public void initView() {
        fpwd = (EditText) findViewById(R.id.fpwd);
        tpwd = (EditText) findViewById(R.id.tpwd);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @SuppressWarnings("static-access")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_submit:
                User user = new User();
                user.updateCurrentUserPassword(CGPwdActivity.this, fpwd.getText().toString(), tpwd.getText().toString(),
                        new UpdateListener() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                Toast.makeText(getApplicationContext(), "修改成功,请重新登录", Toast.LENGTH_LONG).show();
                                settings.isFirstLogin.setValue(true);
                                Intent intent = new Intent();
                                intent.setClass(CGPwdActivity.this, StartActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(int arg0, String arg1) {
                                // TODO Auto-generated method stub
                                Toast.makeText(getApplicationContext(), "失败:" + arg1, Toast.LENGTH_LONG).show();
                            }
                        });
                break;

            default:
                break;
        }
    }

}
