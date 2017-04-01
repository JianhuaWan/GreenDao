package com.xiangyue.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiangyue.base.BaseActivity;

/**
 * Created by wWX321637 on 2016/5/4.
 */
public class EditPayAliActivity extends BaseActivity {
    private EditText et_ali_name;
    private Button btn_sub;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.editpay_main);
        initView();

    }

    private void initView() {
        et_ali_name = (EditText) findViewById(R.id.et_ali_name);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("code", et_ali_name.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
