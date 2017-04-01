package com.xiangyue.act;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.adpter.HeadAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleassisBean;
import com.xiangyue.type.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by wWX321637 on 2016/5/28.
 */
public class AssistsActivity extends BaseActivity implements View.OnClickListener {
    private String uuid;//该条动态的唯一id
    private ImageView img_situp;
    private MicroRecruitSettings settings;
    private GridView gridView;
    private HeadAdapter headAdapter;
    private List<User> users = new ArrayList<>();
    private LinearLayout liner_nodata;
    private TextView tv_title_assis;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.assist_main);
        getintents();
        initView();

        getData();
    }

    private void initView() {
        tv_title_assis = (TextView) findViewById(R.id.tv_title_assis);
        liner_nodata = (LinearLayout) findViewById(R.id.liner_nodata);
        gridView = (GridView) findViewById(R.id.gridView);
        headAdapter = new HeadAdapter(AssistsActivity.this, users);
        gridView.setAdapter(headAdapter);
        img_situp = (ImageView) findViewById(R.id.img_situp);
        img_situp.setOnClickListener(this);
    }

    private void getData() {
        BmobQuery<CircleassisBean> circleassisBeanBmobQuery = new BmobQuery<>();
        circleassisBeanBmobQuery.addWhereEqualTo("uuid", uuid);
        circleassisBeanBmobQuery.findObjects(AssistsActivity.this, new FindListener<CircleassisBean>() {
            @Override
            public void onSuccess(List<CircleassisBean> list) {
                if (list.size() != 0) {
                    //条件搜索出满足的所有用户集合
                    BmobQuery<User> userBmobQuery = new BmobQuery<User>();
                    userBmobQuery.addWhereContainedIn("username", list.get(0).getPeoson());
                    tv_title_assis.setText("赞(" + list.get(0).getPeoson().size() + ")");
                    userBmobQuery.findObjects(AssistsActivity.this, new FindListener<User>() {
                        @Override
                        public void onSuccess(List<User> list) {
                            //取值填充
                            if (list.size() != 0) {
                                liner_nodata.setVisibility(View.GONE);
                                gridView.setVisibility(View.VISIBLE);
//                                for (int i = 0; i < 50; i++) {
                                users.addAll(list);
//                                }
                                headAdapter.setData(users);
                            }
                        }

                        @Override
                        public void onError(int i, String s) {
                            Toast.makeText(AssistsActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
//                    Toast.makeText(AssistsActivity.this, "暂无点赞", Toast.LENGTH_LONG).show();
                    tv_title_assis.setText("赞(0)");
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(AssistsActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getintents() {
        settings = new MicroRecruitSettings(AssistsActivity.this);
        uuid = getIntent().getStringExtra("uuid");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_situp:
                //显示点赞动画
//                AnimationSet set = new AnimationSet(false);
//                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//                alphaAnimation.setDuration(500);
//                set.addAnimation(alphaAnimation);
//                img_situp.setAnimation(set);
                //判断
                BmobQuery<CircleassisBean> circleassisBeanBmobQuery = new BmobQuery<>();
                circleassisBeanBmobQuery.addWhereEqualTo("uuid", uuid);
                circleassisBeanBmobQuery.findObjects(AssistsActivity.this, new FindListener<CircleassisBean>() {
                    @Override
                    public void onSuccess(List<CircleassisBean> list) {
                        if (list.size() == 0) {
                            CircleassisBean circleassisBean = new CircleassisBean();
                            List<String> peoson = new ArrayList<String>();
                            peoson.add(settings.phone.getValue());
                            circleassisBean.setUuid(uuid);
                            circleassisBean.setPeoson(peoson);
                            circleassisBean.save(AssistsActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    //重新刷新时候先清空之前数据
                                    if (users.size() != 0) {
                                        users.clear();
                                    }
                                    getData();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(AssistsActivity.this, s, Toast.LENGTH_LONG).show();
                                }
                            });
                            //显示
                        } else {
                            if (list.get(0).getPeoson().contains(settings.phone.getValue())) {
                                Toast.makeText(AssistsActivity.this, "已赞", Toast.LENGTH_LONG).show();
                            } else {
                                CircleassisBean circleassisBean = new CircleassisBean();
                                List<String> peoson = new ArrayList<String>();
                                peoson.addAll(list.get(0).getPeoson());
                                peoson.add(settings.phone.getValue());
                                circleassisBean.setUuid(uuid);
                                circleassisBean.setPeoson(peoson);
                                circleassisBean.update(AssistsActivity.this, list.get(0).getObjectId(),
                                        new UpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                //重新刷新
                                                if (users.size() != 0) {
                                                    users.clear();
                                                }
                                                getData();
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                Toast.makeText(AssistsActivity.this, s, Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(AssistsActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
