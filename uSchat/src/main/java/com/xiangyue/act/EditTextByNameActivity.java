package com.xiangyue.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.widget.EditText;

import com.im.base.BaseActivity;

/**
 * Created by wWX321637 on 2016/4/25.
 */
public class EditTextByNameActivity extends BaseActivity {
    private EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_name);
        initview();
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.putExtra("name", et_name.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initview() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setText(getIntent().getStringExtra("name"));
        Editable etext = et_name.getText();
        Selection.setSelection(etext, etext.length());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("name", et_name.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
