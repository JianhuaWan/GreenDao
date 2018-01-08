//package com.wanjianhua.aooshop.act.act;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.wanjianhua.aooshop.act.bean.UserPhone;
//import com.wanjianhua.aooshop.act.utils.NetUtils;
//import com.wanjianhua.stock.R;
//import com.wanjianhua.aooshop.act.base.BaseActivity;
//import com.wanjianhua.aooshop.act.utils.MicroRecruitSettings;
//
//import java.util.List;
//
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.FindListener;
//
///**
// * author：wanjianhua on 2017/4/6 22:25
// * email：1243381493@qq.com
// */
//
//public class LoginActivity extends BaseActivity implements View.OnClickListener {
//    private ImageView img_back;
//    private EditText et_phone, et_pwd;
//    private Button btn_login;
//    private MicroRecruitSettings settings;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_main);
//        settings = new MicroRecruitSettings(this);
//        initView();
//    }
//
//    private void initView() {
//        img_back = (ImageView) findViewById(R.id.img_back);
//        img_back.setOnClickListener(this);
//        et_phone = (EditText) findViewById(R.id.et_phone);
//        et_pwd = (EditText) findViewById(R.id.et_pwd);
//        btn_login = (Button) findViewById(R.id.btn_login);
//        btn_login.setOnClickListener(this);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_back:
//                finish();
//                break;
//            case R.id.btn_login:
//                if (NetUtils.isNetworkAvailable(LoginActivity.this)) {
//                    BmobQuery<UserPhone> query = new BmobQuery<>();
//                    query.addWhereEqualTo("phone", et_phone.getText().toString());
//                    query.findObjects(new FindListener<UserPhone>() {
//                        @Override
//                        public void done(List<UserPhone> list, BmobException e) {
//                            if (list != null && list.size() > 0) {
//                                if (list.get(0).getPwd().equals(et_pwd.getText().toString())) {
//                                    settings.PHONE.setValue(list.get(0).getPhone().toString());
//                                    finish();
//                                } else {
//                                    Toast.makeText(LoginActivity.this, getString(R.string.pwderror), Toast.LENGTH_LONG).show();
//                                }
//                            } else {
//                                Toast.makeText(LoginActivity.this, getString(R.string.loginfial), Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//                } else {
//                    Toast.makeText(LoginActivity.this, getString(R.string.netfial), Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
//    }
//}
