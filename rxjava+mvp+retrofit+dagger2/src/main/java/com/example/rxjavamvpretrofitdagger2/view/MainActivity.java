package com.example.rxjavamvpretrofitdagger2.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rxjavamvpretrofitdagger2.R;
import com.example.rxjavamvpretrofitdagger2.base.BaseActivity;
import com.example.rxjavamvpretrofitdagger2.presenter.UserPresenter;

public class MainActivity extends BaseActivity implements IUserView, View.OnClickListener {
    UserPresenter presenter;
    EditText id, username, pwd;
    Button save, load, testRetrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initParams();
        initView();

    }

    private void initParams() {
        presenter = new UserPresenter(this);
    }

    private void initView() {
        id = (EditText) findViewById(R.id.id);
        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.pwd);
        save = (Button) findViewById(R.id.save);
        load = (Button) findViewById(R.id.load);
        testRetrofit = (Button) findViewById(R.id.testRetrofit);
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        testRetrofit.setOnClickListener(this);

    }


    @Override
    public int getID() {
        return new Integer(id.getText().toString());
    }

    @Override
    public String getUserName() {
        return username.getText().toString();
    }

    @Override
    public String getPwd() {
        return pwd.getText().toString();
    }

    @Override
    public void setUserName(String userName) {
        username.setText(userName);
    }

    @Override
    public void setPwd(String pwd) {
        this.pwd.setText(pwd);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                presenter.saveUser(getID(), getUserName(), getPwd());
                presenter.loading(MainActivity.this);
                break;
            case R.id.load:
                presenter.loadUser(getID());
                break;
            case R.id.testRetrofit:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RetrofitActivity.class);
                startActivity(intent);
                break;
        }
    }
}
