package com.xiangyue.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;

import com.xiangyue.base.BaseActivity;

/**
 * @author Administrator
 */
public class SignStyleActivity extends BaseActivity {
    private EditText et_sign;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.sign_main);
        initView();
    }

    private void initView() {
        et_sign = (EditText) findViewById(R.id.et_sign);
        et_sign.setText(getIntent().getStringExtra("sign"));
        Editable etext = et_sign.getText();
        Selection.setSelection(etext, etext.length());
    }


    public void back(View view) {
        Intent intent = new Intent();
        intent.putExtra("sign", et_sign.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("sign", et_sign.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
