//package com.xiangyue.act;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.xiangyue.base.BaseActivity;
//import com.xiangyue.util.SysApplication;
//
//public class MyCenterActivity extends BaseActivity implements OnClickListener {
//    private LinearLayout myphoto, attestation;
//    private ImageView img_situp;
//    private LinearLayout lastLoc, signtime;
//
//    @Override
//    protected void onCreate(Bundle arg0) {
//        // TODO Auto-generated method stub
//        super.onCreate(arg0);
//        SysApplication.getInstance().addActivity(this);
//        setContentView(R.layout.mycenter_main);
//        initView();
//    }
//
//    public void back(View view) {
//        finish();
//    }
//
//    public void initView() {
//        lastLoc = (LinearLayout) findViewById(R.id.lastLoc);
//        signtime = (LinearLayout) findViewById(R.id.signtime);
//        signtime.setOnClickListener(this);
//        lastLoc.setOnClickListener(this);
//        img_situp = (ImageView) findViewById(R.id.img_situp);
//        myphoto = (LinearLayout) findViewById(R.id.myphoto);
//        attestation = (LinearLayout) findViewById(R.id.attestation);
//        myphoto.setOnClickListener(this);
//        img_situp.setOnClickListener(this);
//        attestation.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.myphoto:
//                Intent intent2 = new Intent();
//                intent2.setClass(getActivity(), MyPhotosActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.attestation:
//                Intent intent1 = new Intent();
//                intent1.setClass(getActivity(), VerificationActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.img_situp:
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), SetUpActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.signtime:
////                Toast.makeText(this, "signtime", 1).show();
//                break;
//            case R.id.lastLoc:
////                Toast.makeText(this, "lastLoc", 1).show();
//                break;
//            default:
//                break;
//        }
//    }
//
//}
