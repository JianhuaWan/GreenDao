package com.xiangyue.act;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.suggestion;

import cn.bmob.v3.listener.SaveListener;

public class SuggestionActivity extends BaseActivity implements OnClickListener {
    private EditText tv_suggestion;
    private Button btn_submit;
    private TextView tv_count;
    private MicroRecruitSettings settings;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.suggestion_main);
        initView();
        setUp();
    }

    public void setUp() {
        settings = new MicroRecruitSettings(SuggestionActivity.this);
    }

    private CharSequence temp;

    public void initView() {
        tv_suggestion = (EditText) findViewById(R.id.tv_suggestion);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_suggestion.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                temp = s;

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (temp.length() > 100) {
                    tv_suggestion.setFocusable(false);
                    tv_suggestion.setEnabled(false);
                    return;
                } else {
                    tv_count.setText("(" + s.length() + "/100)");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_submit:
                if (!tv_suggestion.getText().toString().equals("")) {
                    suggestion suggestion = new suggestion();
                    suggestion.setUsername(settings.phone.getValue().toString());
                    suggestion.setContent(tv_suggestion.getText().toString());
                    suggestion.save(SuggestionActivity.this, new SaveListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getApplicationContext(), "失败:" + arg1, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "请先输入建议内容", Toast.LENGTH_LONG).show();
                }

                break;

            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
