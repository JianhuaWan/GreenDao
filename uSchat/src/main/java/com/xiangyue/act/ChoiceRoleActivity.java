package com.xiangyue.act;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.squareup.otto.Subscribe;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.provider.PhotoRefreshEvent;
import com.xiangyue.tusdk.CameraComponentSimple;
import com.xiangyue.tusdk.EditMultipleComponentSimple;
import com.xiangyue.type.User;
import com.xiangyue.util.MeDialogUtils;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.view.ActionSheetDialog.OnSheetItemClickListener;
import com.xiangyue.view.ActionSheetDialog.SheetItemColor;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;
import org.lasque.tusdk.core.seles.tusdk.FilterManager.FilterManagerDelegate;
import org.lasque.tusdk.impl.activity.TuFragmentActivity;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * @author Administrator
 */
public class ChoiceRoleActivity extends TuFragmentActivity implements OnClickListener {
    private Button btn_join;
    private ImageView img_left, img_right;
    private LinearLayout linear_uploadphoto;
    private ImageView headImage;
    private TextView mDateDisplay;
    private LinearLayout mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    int tempage;
    private LinearLayout linear_sex;
    private TextView tv_sexy;
    private Context context;
    public static final int layoutId = R.layout.role_main;
    private TextView tv_nick;
    private LinearLayout line_name;

    @Override
    protected void initActivity() {
        // TODO Auto-generated method stub
        super.initActivity();
        context = this;
        BusProvider.getInstance().register(this);
        this.setRootView(layoutId, 0);

    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        super.initView();
        line_name = (LinearLayout) findViewById(R.id.line_name);
        line_name.setOnClickListener(this);
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(this);
        linear_uploadphoto = (LinearLayout) findViewById(R.id.linear_uploadphoto);
        linear_uploadphoto.setOnClickListener(this);
        headImage = (ImageView) findViewById(R.id.headImage);
        tv_sexy = (TextView) findViewById(R.id.tv_sexy);
        linear_sex = (LinearLayout) findViewById(R.id.linear_sex);
        linear_sex.setOnClickListener(this);
        mDateDisplay = (TextView) findViewById(R.id.tv_age);

        mPickDate = (LinearLayout) findViewById(R.id.linear_age);
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        mYear = 1989;
        mMonth = 12;
        mDay = 1;
        // 显示当前时间
        TuSdk.messageHub().dismissRightNow();
        TuSdk.checkFilterManager(mFilterManagerDelegate);
    }

    /**
     * 滤镜管理器委托
     */
    private FilterManagerDelegate mFilterManagerDelegate = new FilterManagerDelegate() {
        @Override
        public void onFilterManagerInited(FilterManager manager) {
            // TuSdk.messageHub().showSuccess(MeAccountActivity.this,
            // R.string.lsq_inited);
            TuSdk.messageHub().dismissRightNow();// 去掉提示
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.btn_join:
                if (path != null && !tv_nick.getText().toString().trim().isEmpty()
                        && !mDateDisplay.getText().toString().trim().isEmpty()
                        && !tv_sexy.getText().toString().trim().isEmpty()
                        ) {
                    final BmobFile bmobFile = new BmobFile(path);
                    bmobFile.uploadblock(this, new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub

                            final User user = new User();
                            user.setNickName(tv_nick.getText().toString());
                            user.setSex(tv_sexy.getText().toString());
                            user.setAge(mDateDisplay.getText().toString());
                            user.setPassword(getIntent().getStringExtra("pwd"));
                            user.setUsername(getIntent().getStringExtra("phone"));// 手机号当用户名
                            user.setHeadImage(bmobFile);
                            user.signUp(context, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    MicroRecruitSettings settings = new MicroRecruitSettings(context);
                                    settings.OBJECT_ID.setValue(user.getObjectId());
                                    settings.phone.setValue(user.getUsername());
                                    settings.HEADICON.setValue(user.getHeadImage().getFileUrl(ChoiceRoleActivity.this));
                                    // TODO Auto-generated method stub
                                    Toast.makeText(getApplicationContext(), "添加成功,正在跳转...", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    intent.setClass(ChoiceRoleActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(int arg0, String arg1) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(getApplicationContext(), "添加失败" + arg1, Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            // TODO Auto-generated method stub
                        }
                    });
                } else {
                    Toast.makeText(context, "请补全信息", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.linear_uploadphoto:
                // 上传头像
                new ActionSheetDialog(ChoiceRoleActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            public void onClick(int which) {
                                showCameraComponent();
                            }
                        }).addSheetItem("从相册中选择", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    public void onClick(int which) {
                        showEditorComponent();
                    }
                }).show();
                break;
            case R.id.linear_sex:
                // 性别
                new ActionSheetDialog(ChoiceRoleActivity.this).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("男", SheetItemColor.Blue, new OnSheetItemClickListener() {
                            public void onClick(int which) {
                                tv_sexy.setText("男");
                            }
                        }).addSheetItem("女", SheetItemColor.Blue, new OnSheetItemClickListener() {
                    public void onClick(int which) {
                        tv_sexy.setText("女");
                    }
                }).show();
                break;
            default:
                break;
        }

    }

    /**
     * 打开相机组件
     */
    private void showCameraComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("1");
        new CameraComponentSimple().showSimple(this);
    }

    /**
     * 打开多功能编辑组件
     */
    private void showEditorComponent() {
        BaseApplication.getInstance().setPhotoorheadIcon("1");
        new EditMultipleComponentSimple().showSimple(this);
    }


    private File path = null;

    @Subscribe
    public void onPhotoRefreshEvent(PhotoRefreshEvent event) {
        path = new File(event.path);
//        Toast.makeText(context, event.path, Toast.LENGTH_LONG).show();
        ViewUtil.setPicture(ChoiceRoleActivity.this, "file:///" + event.path, R.drawable.default_head, headImage, null);
//        path = new File(event.path);
        // 上传头像
//        Picasso.with(ChoiceRoleActivity.this).load(path).error(R.drawable.chat_photo).error(R.drawable.chat_photo)
//                .into(headImage);
//        Toast.makeText(context, "执行了" + event.path, Toast.LENGTH_LONG).show();
    }

    public void back(View v) {
        finish();
    }

    public void showTimeDialog() {
        Calendar cal = Calendar.getInstance();

        final int mYeardel = cal.get(Calendar.YEAR);//获取当前时间的年份
//        mMonth = cal.get(Calendar.MONTH) + 1;//获取当前时间的年份
//        mDay = cal.get(Calendar.DATE);//获取当前时间的年份
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            cal.setTime(sdf.parse(sdf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        new MeDialogUtils(this)
                .builder(mYear, mMonth, mDay)
                .setCancel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setOk(new MeDialogUtils.onDataTimeClickListener() {

                    @Override
                    public void onClick(View v, int mY, int mM, int mD) {
                        mYear = mY;
                        mMonth = mM;
                        mDay = mD;
                        //2015-09-10 00:00:00
                        Calendar cal = Calendar.getInstance();//获取当前时间
                        cal.set(mYear, mMonth - 1, mDay);
                        DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                        DateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = sf.format(cal.getTime());
                        //设置时间
                        if (currentDate != null) {
                            int age = mYeardel - mYear;
                            mDateDisplay.setText(age + "岁");
                        }
                    }
                })
                .show();
    }
}
