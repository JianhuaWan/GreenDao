package com.xiangyue.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.ui.ChatActivity;
import com.im.util.ViewUtil;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleBean;
import com.xiangyue.bean.circlrmebgphpto;
import com.xiangyue.bean.coin;
import com.xiangyue.bean.laud;
import com.xiangyue.type.User;
import com.xiangyue.util.CountUtils;
import com.xiangyue.weight.LoginDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class OtherInfoDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView other_photo;
    SwipeRefreshLayout swipe_me;
    private LinearLayout startct;
    private TextView tv_name;
    private LinearLayout linear_ste;
    private ImageView myheadIcon;
    private String username;
    private MicroRecruitSettings settings;
    private Drawable[] drawable_Anims;// 点赞动画
    private Drawable[] drawable_Animsbystep;// 踩动画
    private ImageView img_assist;
    private LinearLayout linear_assist, linear_step;
    private TextView tv_nickname;
    private TextView tv_age;
    private TextView tv_school;
    private TextView tv_city;
    private TextView tv_regtime;
    private TextView tv_star;
    private TextView tv_alicode;
    private TextView tv_style;
    private TextView tv_gz;
    private LinearLayout linear_gz;
    private ImageView img_sexbg;
    private TextView tv_assist, tv_step;
    private String headIcon;
    private String usercirlcebg;
    private ImageView img_bg;
    private TextView tv_othercirlce;
    private ImageView img_01, img_02, img_03, img_04, img_05, img_06, img_07;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.otherinfodetail_main);
        initTool();
        initView();
        setUp();
        initVoiceAnimResbystep();
        initVoiceAnimRes();
        getOtherInfoData();
        getAttention();
        getLuad();
        getBg();
        getCircleCount();
        getDaySign();
    }

    private void getDaySign() {
        BmobQuery<coin> coins = new BmobQuery<>();
        coins.addWhereEqualTo("username", getIntent().getStringExtra("userid"));
        coins.findObjects(getActivity(), new FindListener<coin>() {
            @Override
            public void onSuccess(List<coin> list) {
                if (list.size() != 0) {
                    int countday = Integer.parseInt(list.get(0).getCount());
                    count(CountUtils.Count(countday));
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCircleCount() {
        BmobQuery<CircleBean> circleBeanBmobQuery = new BmobQuery<>();
        circleBeanBmobQuery.include("username");
        circleBeanBmobQuery.addWhereEqualTo("username", getIntent().getStringExtra("objectId"));
        circleBeanBmobQuery.count(getActivity(), CircleBean.class, new CountListener() {
            @Override
            public void onSuccess(int i) {
                tv_othercirlce.setText(i + "");
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getBg() {
        BmobQuery<circlrmebgphpto> circlrmebgphptosBmobQuery = new BmobQuery<>();
        circlrmebgphptosBmobQuery.addWhereEqualTo("username", getIntent().getStringExtra("userid"));
        circlrmebgphptosBmobQuery.findObjects(OtherInfoDetailActivity.this, new FindListener<circlrmebgphpto>() {
            @Override
            public void onSuccess(List<circlrmebgphpto> list) {
                if (list.size() != 0) {
                    if (list.get(0).getMebg() != null) {
                        ViewUtil.setPicture(OtherInfoDetailActivity.this, list.get(0).getMebg().getFileUrl(OtherInfoDetailActivity.this), R.drawable.default_head, img_bg, null);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    private void getLuad() {
        final BmobQuery<laud> laudBmobQuery = new BmobQuery<>();
//                laudBmobQuery.addWhereStartsWith("updateAt", sdf.format(date).substring(0, 10));
        laudBmobQuery.addWhereEqualTo("username", username);
        laudBmobQuery.findObjects(OtherInfoDetailActivity.this, new FindListener<laud>() {
            @Override
            public void onSuccess(List<laud> list) {
                if (list.size() == 0) {
                    tv_assist.setText("赞(0)");
                    tv_step.setText("踩(0)");
                } else {
                    tv_assist.setText("赞(" + list.get(0).getLaudcont() + ")");
                    tv_step.setText("踩(" + list.get(0).getStampcont() + ")");
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getAttention() {
        BmobQuery<com.xiangyue.type.User> query = new BmobQuery<com.xiangyue.type.User>();
        query.getObject(getActivity(), settings.OBJECT_ID.getValue(), new GetListener<com.xiangyue.type.User>() {

            @Override
            public void onSuccess(User user) {
                if (user.getAttention() == null) {
                    attens = new ArrayList<String>();
                    tv_gz.setText("关注");
                } else {
                    attens = user.getAttention();
                    if (user.getAttention().contains(username)) {
                        tv_gz.setText("已关注");
                    } else {
                        tv_gz.setText("关注");
                    }
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

    }


    LoginDialog dialog;
    User userover;

    private void getOtherInfoData() {
        dialog.show();
        if (getIntent().hasExtra("objectId")) {
            BmobQuery<com.xiangyue.type.User> query = new BmobQuery<com.xiangyue.type.User>();
            query.getObject(getActivity(), getIntent().getStringExtra("objectId"), new GetListener<com.xiangyue.type.User>() {

                        @Override
                        public void onSuccess(com.xiangyue.type.User user) {
                            userover = user;
                            swipe_me.setRefreshing(false);
                            dialog.dismiss();
                            tv_name.setText(user.getNickName());
//                            Picasso.with(OtherInfoDetailActivity.this).load(user.getHeadImage().getFileUrl(OtherInfoDetailActivity.this)).placeholder(R.drawable.default_head).error(R.drawable.default_head).into(myheadIcon);
                            headIcon = user.getHeadImage().getFileUrl(OtherInfoDetailActivity.this);
                            ViewUtil.setPicture(OtherInfoDetailActivity.this, user.getHeadImage().getFileUrl(OtherInfoDetailActivity.this), R.drawable.default_head, myheadIcon, null);
                            tv_nickname.setText(user.getNickName());
                            tv_age.setText(user.getAge().replace("岁", ""));
                            if (user.getSex().equals("男")) {
                                img_sexbg.setImageDrawable(getResources().getDrawable(R.drawable.eos));
                                tv_age.setBackgroundResource(R.drawable.sex_man);
                            } else if (user.getSex().equals("女")) {
                                img_sexbg.setImageDrawable(getResources().getDrawable(R.drawable.eom));
                                tv_age.setBackgroundResource(R.drawable.sex_women);
                            }
                            if (user.getSign() == null) {
                                tv_school.setText("暂无个性签名");
                            } else {
                                tv_school.setText(user.getSign());
                            }
                            if (user.getCity() == null) {
                                tv_city.setText("未知");
                            } else {
                                tv_city.setText(user.getCity());
                            }
                            tv_regtime.setText(user.getCreatedAt().substring(0, 10));
                            if (user.getStar() == null) {
                                tv_star.setText("未知");
                            } else {
                                tv_star.setText(user.getStar());
                            }
                            if (user.getAlicodestate() == null) {
                                tv_alicode.setText("未认证");
                            } else {
                                tv_alicode.setText("已认证");
                            }
                            if (user.getStylebq() != null) {
                                tv_style.setText(user.getStylebq().get(0));
                            } else {
                                tv_style.setText("未知");
                            }

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            dialog.dismiss();
                            swipe_me.setRefreshing(false);
                            Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    }

            );
        }
    }


    private void initVoiceAnimRes() {
        drawable_Anims = new Drawable[]{
                getResources().getDrawable(R.drawable.mva),
                getResources().getDrawable(R.drawable.mvb),
                getResources().getDrawable(R.drawable.mvc),
                getResources().getDrawable(R.drawable.mvd),
                getResources().getDrawable(R.drawable.mvo)};
    }

    private void initVoiceAnimResbystep() {
        drawable_Animsbystep = new Drawable[]{
                getResources().getDrawable(R.drawable.mvo),
                getResources().getDrawable(R.drawable.mvd),
                getResources().getDrawable(R.drawable.mvc),
                getResources().getDrawable(R.drawable.mvb),
                getResources().getDrawable(R.drawable.mva)};
    }

    private void initTool() {
        dialog = new LoginDialog(OtherInfoDetailActivity.this, "");
        dialog.setCanceledOnTouchOutside(false);
        settings = new MicroRecruitSettings(OtherInfoDetailActivity.this);
    }

    private void setUp() {
        swipe_me.setEnabled(true);
        swipe_me.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //下拉加载
        swipe_me.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOtherInfoData();
                getAttention();
                getBg();
                getCircleCount();
                getDaySign();
            }
        });
    }

    private void initView() {
        img_01 = (ImageView) findViewById(R.id.img_01);
        img_02 = (ImageView) findViewById(R.id.img_02);
        img_03 = (ImageView) findViewById(R.id.img_03);
        img_04 = (ImageView) findViewById(R.id.img_04);
        img_05 = (ImageView) findViewById(R.id.img_05);
        img_06 = (ImageView) findViewById(R.id.img_06);
        img_07 = (ImageView) findViewById(R.id.img_07);
        tv_othercirlce = (TextView) findViewById(R.id.tv_othercirlce);
        img_bg = (ImageView) findViewById(R.id.img_bg);
        tv_assist = (TextView) findViewById(R.id.tv_assist);
        tv_step = (TextView) findViewById(R.id.tv_step);
        img_sexbg = (ImageView) findViewById(R.id.img_sexbg);
        linear_gz = (LinearLayout) findViewById(R.id.linear_gz);
        linear_gz.setOnClickListener(this);
        tv_gz = (TextView) findViewById(R.id.tv_gz);
        tv_style = (TextView) findViewById(R.id.tv_style);
        tv_alicode = (TextView) findViewById(R.id.tv_alicode);
        tv_star = (TextView) findViewById(R.id.tv_star);
        tv_regtime = (TextView) findViewById(R.id.tv_regtime);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_school = (TextView) findViewById(R.id.tv_school);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        linear_step = (LinearLayout) findViewById(R.id.linear_step);
        linear_step.setOnClickListener(this);
        linear_assist = (LinearLayout) findViewById(R.id.linear_assist);
        linear_assist.setOnClickListener(this);
        img_assist = (ImageView) findViewById(R.id.img_assist);
        myheadIcon = (ImageView) findViewById(R.id.myheadIcon);
        myheadIcon.setOnClickListener(this);
        linear_ste = (LinearLayout) findViewById(R.id.linear_ste);
        linear_ste.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        swipe_me = (SwipeRefreshLayout) findViewById(R.id.swipe_me);
        other_photo = (ImageView) findViewById(R.id.other_photo);
        other_photo.setOnClickListener(this);
        startct = (LinearLayout) findViewById(R.id.startct);
        startct.setOnClickListener(this);
        if (getIntent().hasExtra("userid")) {
            username = getIntent().getStringExtra("userid");
        }
    }


    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.other_photo:
                Intent intent = new Intent();
                intent.putExtra("username", username);
                intent.setClass(OtherInfoDetailActivity.this, MyPhotosActivity.class);
                startActivity(intent);
                break;
            case R.id.startct:
                BmobQuery<circlrmebgphpto> circlemephotos = new BmobQuery<>();
                circlemephotos.addWhereEqualTo("username", getIntent().getStringExtra("userid"));
                circlemephotos.findObjects(OtherInfoDetailActivity.this, new FindListener<circlrmebgphpto>() {
                    @Override
                    public void onSuccess(List<circlrmebgphpto> list) {
                        Intent intent1 = new Intent();
                        intent1.putExtra("type", "other");
                        intent1.putExtra("othername", tv_name.getText().toString());
                        intent1.putExtra("othericon", headIcon);
                        intent1.putExtra("objectId", getIntent().getStringExtra("objectId"));
                        if (list.size() == 0) {
                            intent1.putExtra("otherbg", "0");
                            intent1.setClass(OtherInfoDetailActivity.this, OtherCircleActivity.class);
                            startActivity(intent1);
                        } else if (list.get(0).getCirclebg() == null) {
                            intent1.putExtra("otherbg", "0");
                            intent1.setClass(OtherInfoDetailActivity.this, OtherCircleActivity.class);
                            startActivity(intent1);
                        } else {
                            intent1.putExtra("otherbg", list.get(0).getCirclebg().getFileUrl(OtherInfoDetailActivity.this));
                            intent1.setClass(OtherInfoDetailActivity.this, OtherCircleActivity.class);
                            startActivity(intent1);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });

                break;
            case R.id.linear_ste:
                //聊天
                if (username.equals(settings.phone.getValue().toString())) {
                    Toast.makeText(OtherInfoDetailActivity.this, "不能与自己聊天", Toast.LENGTH_LONG).show();
                    return;
                }
                BmobIMUserInfo info = new BmobIMUserInfo(userover.getObjectId(), userover.getNickName(), userover.getHeadImage().getFileUrl(OtherInfoDetailActivity.this));
                //如果需要更新用户资料，开发者只需要传新的info进去就可以了
                BmobIM.getInstance().startPrivateConversation(info, new ConversationListener() {
                    @Override
                    public void done(BmobIMConversation c, BmobException e) {
                        if (e == null) {
                            Bundle bundle = new Bundle();
                            c.setConversationTitle(userover.getNickName());
                            bundle.putSerializable("c", c);
                            bundle.putString("nickName", userover.getNickName());
                            startActivity(ChatActivity.class, bundle, false);
                        } else {
                            toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                        }
                    }
                });
                break;
            case R.id.myheadIcon:
                Intent intent2 = new Intent();
                if (username.equals(settings.phone.getValue().toString())) {
                    intent2.putExtra("headIcon", settings.HEADICON.getValue().toString());
                } else {
                    intent2.putExtra("headIcon", userover.getHeadImage().getFileUrl(OtherInfoDetailActivity.this));
                }
                intent2.setClass(OtherInfoDetailActivity.this, QueryHeadActivity.class);
                startActivity(intent2);
                break;
            case R.id.linear_assist:
                //点赞
                BmobQuery<laud> laudBmobQuery = new BmobQuery<>();

//                laudBmobQuery.addWhereStartsWith("updateAt", sdf.format(date).substring(0, 10));
                laudBmobQuery.addWhereEqualTo("username", username);
                laudBmobQuery.findObjects(OtherInfoDetailActivity.this, new FindListener<laud>() {
                    @Override
                    public void onSuccess(List<laud> list) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        sdf.format(date);
                        if (list.size() == 0) {
                            //可以操作
                            laud lauds = new laud();
                            lauds.setUsername(username);
                            lauds.setLaudcont("1");
                            lauds.setStampcont("0");
                            lauds.save(OtherInfoDetailActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    tv_assist.setText("赞(1)");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                                }
                            });
                        } else if (list.get(0).getUpdatedAt().startsWith(sdf.format(date).substring(0, 10))) {

                            //不能再操作了
                            Toast.makeText(OtherInfoDetailActivity.this, "赞和踩一天只能操作一次哦", Toast.LENGTH_LONG).show();
                        } else {
                            int laud = Integer.parseInt(list.get(0).getLaudcont()) + 1;
                            tv_assist.setText("赞(" + laud + ")");
                            laud lauds = new laud();
                            lauds.setUsername(username);
                            lauds.setLaudcont(laud + "");
                            //一定注意ObjectId是点赞表里面的id,而不是用户User表里面的ObjectId,不能从MicroRecruitSettings里面取
                            lauds.update(OtherInfoDetailActivity.this, list.get(0).getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();

                    }
                });
                img_assist.setVisibility(View.VISIBLE);
                img_assist.setImageDrawable(drawable_Anims[0]);
                handler.postDelayed(runnable, 200);
                break;
            case R.id.linear_step:
                BmobQuery<laud> laudBmobQuery1 = new BmobQuery<>();

//                laudBmobQuery.addWhereStartsWith("updateAt", sdf.format(date).substring(0, 10));
                laudBmobQuery1.addWhereEqualTo("username", username);
                laudBmobQuery1.findObjects(OtherInfoDetailActivity.this, new FindListener<laud>() {
                    @Override
                    public void onSuccess(List<laud> list) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        sdf.format(date);
                        if (list.size() == 0) {
                            //可以操作
                            laud lauds = new laud();
                            lauds.setUsername(username);
                            lauds.setLaudcont("0");
                            lauds.setStampcont("1");
                            lauds.save(OtherInfoDetailActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    tv_step.setText("踩(1)");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                                }
                            });
                        } else if (list.get(0).getUpdatedAt().startsWith(sdf.format(date).substring(0, 10))) {

                            //不能再操作了
                            Toast.makeText(OtherInfoDetailActivity.this, "赞和踩一天只能操作一次哦", Toast.LENGTH_LONG).show();
                        } else {
                            int laud = Integer.parseInt(list.get(0).getStampcont()) + 1;
                            tv_step.setText("踩(" + laud + ")");
                            laud lauds = new laud();
                            lauds.setUsername(username);
                            lauds.setStampcont(laud + "");
                            lauds.update(OtherInfoDetailActivity.this, list.get(0).getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();

                    }
                });

                img_assist.setVisibility(View.VISIBLE);
                img_assist.setImageDrawable(drawable_Animsbystep[0]);
                handler.postDelayed(runnablestep, 200);
                break;
            case R.id.linear_gz:
                if (tv_gz.getText().equals("关注")) {
                    //关注
                    attens.add(username);
                    User user = new User();
                    user.setAttention(attens);
                    user.update(OtherInfoDetailActivity.this, settings.OBJECT_ID.getValue(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            tv_gz.setText("已关注");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    //取消关注
                    User user = new User();
                    if (attens.contains(username)) {
                        attens.remove(username);
                    }
                    user.setAttention(attens);
                    user.update(OtherInfoDetailActivity.this, settings.OBJECT_ID.getValue(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            tv_gz.setText("关注");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(OtherInfoDetailActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private List<String> attens = new ArrayList<>();

    private int temp = 0;
    private static final int UPDATE = 1229;


    private int nowtime;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            nowtime++;
            if (nowtime == 5) {
                img_assist.setVisibility(View.GONE);
                handler.removeCallbacks(runnable);// 移除线程
                nowtime = 0;
                return;
            }
            img_assist.setImageDrawable(drawable_Anims[nowtime]);
            handler.postDelayed(this, 200);
        }
    };
    Runnable runnablestep = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            nowtime++;
            if (nowtime == 5) {
                img_assist.setVisibility(View.GONE);
                handler.removeCallbacks(runnablestep);// 移除线程
                nowtime = 0;
                return;
            }
            img_assist.setImageDrawable(drawable_Animsbystep[nowtime]);
            handler.postDelayed(this, 200);
        }
    };


    public void startActivity(Class<? extends Activity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        if (finish)
            finish();
    }

    private Toast toast;
    protected final static String NULL = "";

    protected void runOnMain(Runnable runnable) {
        runOnUiThread(runnable);
    }

    public void toast(final Object obj) {
        try {
            runOnMain(new Runnable() {

                @Override
                public void run() {
                    if (toast == null)
                        toast = Toast.makeText(OtherInfoDetailActivity.this, NULL, Toast.LENGTH_SHORT);
                    toast.setText(obj.toString());
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void count(int count) {
        //比较
        if (count == 0) {
        } else if (count == 1) {
            img_01.setVisibility(View.VISIBLE);
        } else if (count == 2) {
            img_01.setVisibility(View.VISIBLE);
            img_02.setVisibility(View.VISIBLE);
        } else if (count == 3) {
            img_01.setVisibility(View.VISIBLE);
            img_02.setVisibility(View.VISIBLE);
            img_03.setVisibility(View.VISIBLE);
        } else if (count == 4) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
        } else if (count == 5) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 6) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 7) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_04.setVisibility(View.VISIBLE);
            img_04.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 8) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
        } else if (count == 9) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 10) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_04.setVisibility(View.VISIBLE);
            img_04.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 11) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_04.setVisibility(View.VISIBLE);
            img_04.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_05.setVisibility(View.VISIBLE);
            img_05.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 12) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
        } else if (count == 13) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_04.setVisibility(View.VISIBLE);
            img_04.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 14) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_04.setVisibility(View.VISIBLE);
            img_04.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_05.setVisibility(View.VISIBLE);
            img_05.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 15) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
            img_04.setVisibility(View.VISIBLE);
            img_04.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_05.setVisibility(View.VISIBLE);
            img_05.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_06.setVisibility(View.VISIBLE);
            img_06.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
        } else if (count == 16) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdr));
        } else if (count == 17) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdr));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdt));

        } else if (count == 18) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdr));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdt));

        } else if (count == 19) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdr));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_03.setVisibility(View.VISIBLE);
            img_03.setImageDrawable(getResources().getDrawable(R.drawable.hdt));
            img_04.setVisibility(View.VISIBLE);
            img_04.setImageDrawable(getResources().getDrawable(R.drawable.hdt));

        } else if (count == 20) {
            img_01.setVisibility(View.VISIBLE);
            img_01.setImageDrawable(getResources().getDrawable(R.drawable.hdr));
            img_02.setVisibility(View.VISIBLE);
            img_02.setImageDrawable(getResources().getDrawable(R.drawable.hdu));
        }
    }
}
