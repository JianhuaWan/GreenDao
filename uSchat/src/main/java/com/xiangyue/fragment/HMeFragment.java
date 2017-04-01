package com.xiangyue.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.util.ViewUtil;
import com.xiangyue.act.BlackBywActivity;
import com.xiangyue.act.ChoiseStarActivity;
import com.xiangyue.act.DayActivity;
import com.xiangyue.act.MyAddPeopleActivity;
import com.xiangyue.act.NameEditActivity;
import com.xiangyue.act.OtherCircleActivity;
import com.xiangyue.act.PayAliActivity;
import com.xiangyue.act.R;
import com.xiangyue.act.SetUpActivity;
import com.xiangyue.act.SystemMainActivity;
import com.xiangyue.act.TitleStyleActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.CircleBean;
import com.xiangyue.bean.circlrmebgphpto;
import com.xiangyue.bean.coin;
import com.xiangyue.bean.laud;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.type.User;
import com.xiangyue.util.CountUtils;
import com.xiangyue.view.ActionSheetDialog;
import com.xiangyue.weight.LoginDialog;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * @author Administrator
 */
public class HMeFragment extends Fragment implements OnClickListener {
    private View root;
    private LinearLayout payali, title_style, startcl;
    private TextView tv_nj, tv_star, tv_registtime, tv_lastaddress;
    private TextView tv_nickname, tv_age, tv_school;
    private ImageView myheadIcon;
    private MicroRecruitSettings settings;
    private ImageView img_setUp;
    private LinearLayout line_day, linear_add;
    private LinearLayout linear_step, linear_assist, systemcount;
    private TextView tv_assist, tv_step;
    private ImageView img_bg;
    SwipeRefreshLayout swipe_me;
    private LinearLayout startct;
    private TextView tv_city;
    private TextView tv_regtime;
    private TextView tv_aliatate;

    private TextView tv_stylebq;
    private TextView tv_attention;
    private ImageView img_sexbg;
    private TextView tv_signqd;
    private TextView tv_circlecount, tv_sys;
    private ImageView img_01, img_02, img_03, img_04, img_05, img_06, img_07;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.me_main, container, false);
        setUp();
        init();
        initBro();
        queryInfo();
        getLuad();
        getBgIcon();
        getData();
        getCircleCount();
        getDaySign();

        //实时监听user表功能
        userLisenter();
        return root;
    }

    private void getDaySign() {
        BmobQuery<coin> coins = new BmobQuery<>();
        coins.addWhereEqualTo("username", settings.phone.getValue());
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
        circleBeanBmobQuery.addWhereEqualTo("username", settings.OBJECT_ID.getValue());
        circleBeanBmobQuery.count(getActivity(), CircleBean.class, new CountListener() {
            @Override
            public void onSuccess(int i) {
                tv_circlecount.setText(i + "");
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getLuad() {
        final BmobQuery<laud> laudBmobQuery = new BmobQuery<>();
//                laudBmobQuery.addWhereStartsWith("updateAt", sdf.format(date).substring(0, 10));
        laudBmobQuery.addWhereEqualTo("username", settings.phone.getValue());
        laudBmobQuery.findObjects(getActivity(), new FindListener<laud>() {
            @Override
            public void onSuccess(List<laud> list) {
                if (list.size() == 0) {
                    tv_assist.setText("赞(0)");
                    tv_step.setText("踩(0)");
                } else {
                    settings.OBJECT_IDBYLAUD.setValue(list.get(0).getObjectId());
                    tv_assist.setText("赞(" + list.get(0).getLaudcont() + ")");
                    tv_step.setText("踩(" + list.get(0).getStampcont() + ")");
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

            }
        });
    }


    BmobRealTimeData rtd, rtdlaud;

    private void userLisenter() {
        rtd = new BmobRealTimeData();
        rtd.start(getActivity(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                Log.e("xiangyue", "连接成功:" + rtd.isConnected());
                if (rtd.isConnected()) {
                    rtd.subRowUpdate("_User", settings.OBJECT_ID.getValue());
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                Log.e("xiangyue", jsonObject.toString());
                queryInfo();
            }
        });

        if (!settings.OBJECT_IDBYLAUD.getValue().equals("")) {
            rtdlaud = new BmobRealTimeData();
            rtdlaud.start(getActivity(), new ValueEventListener() {
                @Override
                public void onConnectCompleted() {
                    if (rtdlaud.isConnected()) {
                        rtdlaud.subRowUpdate("laud", settings.OBJECT_IDBYLAUD.getValue());
                    }
                }

                @Override
                public void onDataChange(JSONObject jsonObject) {
                    Log.e("xiangyue", jsonObject.toString());
                    getLuad();
                }
            });
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        rtd.unsubRowUpdate("_User", settings.OBJECT_ID.getValue());
        rtdlaud.unsubRowUpdate("laud", settings.OBJECT_IDBYLAUD.getValue());
    }

    private ArrayList<String> attem = new ArrayList<>();
    private ArrayList<String> stylechoise = new ArrayList<>();

    private void queryInfo() {
        //查询
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(getActivity(), settings.OBJECT_ID.getValue(), new GetListener<User>() {

            @Override
            public void onSuccess(User object) {
                if (object.getCity() == null) {
                    tv_city.setText("未知");
                } else {
                    tv_city.setText(object.getCity());
                }
//                Picasso.with(getActivity()).load(object.getHeadImage().getFileUrl(getActivity())).error(R.drawable.default_head).placeholder(R.drawable.default_head).into(myheadIcon);
                ViewUtil.setPicture(getActivity(), object.getHeadImage().getFileUrl(getActivity()), R.drawable.default_head, myheadIcon, null);
                tv_nickname.setText(object.getNickName());
                tv_age.setText(object.getAge().replace("岁", ""));
                if (object.getSex().equals("男")) {
                    img_sexbg.setImageDrawable(getResources().getDrawable(R.drawable.eos));
                    tv_age.setBackgroundResource(R.drawable.sex_man);
                } else if (object.getSex().equals("女")) {
                    img_sexbg.setImageDrawable(getResources().getDrawable(R.drawable.eom));
                    tv_age.setBackgroundResource(R.drawable.sex_women);
                }
                if (object.getSign() == null) {
                    tv_school.setText("暂无个性签名...");
                } else {
                    tv_school.setText(object.getSign());
                }
                if (object.getStar() == null) {
                    tv_star.setText("未知");
                } else {
                    tv_star.setText(object.getStar());
                }
                tv_regtime.setText(object.getCreatedAt().substring(0, 10));
                if (object.getAlicodestate() == null) {
                    alicodestate = "0";
                    tv_aliatate.setText("未认证");
                } else {
                    tv_aliatate.setText("已认证");
                    code = object.getAlicode();
                    alicodestate = "1";
                }
                if (object.getStylebq() == null) {
                    stylechoise = null;

                } else {
                    stylechoise = (ArrayList<String>) object.getStylebq();
//                    for (int i = 0; i < stylechoise.size(); i++) {
                    tv_stylebq.setText(stylechoise.get(0));
//                    }

                }
                if (object.getAttention() != null) {
                    attem = (ArrayList) object.getAttention();
                    tv_attention.setText(attem.size() + "");
                } else {
                    tv_attention.setText("0");
                }
                swipe_me.setRefreshing(false);
            }

            @Override
            public void onFailure(int code, String arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), arg0, Toast.LENGTH_LONG).show();
                swipe_me.setRefreshing(false);
            }
        });
    }


    private String alicodestate;

    public void initBro() {
        Receiver bReceiver = new Receiver();
//        IntentFilter intentFilter = new IntentFilter("changeIcon");
//        getActivity().registerReceiver(bReceiver, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("bgme");
        getActivity().registerReceiver(bReceiver, intentFilter1);
    }

    public void setUp() {
        dialog = new LoginDialog(getActivity(), "");
        dialog.setCanceledOnTouchOutside(false);
        BusProvider.getInstance().register(getActivity());
        settings = new MicroRecruitSettings(getActivity());
    }

    private void getBgIcon() {
        BmobQuery<circlrmebgphpto> circlrmebgphptoBmobQuery = new BmobQuery<>();
        circlrmebgphptoBmobQuery.addWhereEqualTo("username", settings.phone.getValue());
        circlrmebgphptoBmobQuery.findObjects(getActivity(), new FindListener<circlrmebgphpto>() {
            @Override
            public void onSuccess(List<circlrmebgphpto> list) {
                if (list.size() != 0) {
                    settings.OBJECT_IDBYCIRCLEBG.setValue(list.get(0).getObjectId());
                    if (list.get(0).getMebg() != null) {
                        ViewUtil.setPicture(getActivity(), list.get(0).getMebg().getFileUrl(getActivity()), R.drawable.yuwan_official_bg, img_bg, null);
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void init() {
        // pay = (Button) root.findViewById(R.id.pay);
        // pay.setOnClickListener(this);
        img_01 = (ImageView) root.findViewById(R.id.img_01);
        img_02 = (ImageView) root.findViewById(R.id.img_02);
        img_03 = (ImageView) root.findViewById(R.id.img_03);
        img_04 = (ImageView) root.findViewById(R.id.img_04);
        img_05 = (ImageView) root.findViewById(R.id.img_05);
        img_06 = (ImageView) root.findViewById(R.id.img_06);
        img_07 = (ImageView) root.findViewById(R.id.img_07);
        tv_circlecount = (TextView) root.findViewById(R.id.tv_circlecount);
        tv_signqd = (TextView) root.findViewById(R.id.tv_signqd);
        img_sexbg = (ImageView) root.findViewById(R.id.img_sexbg);
        tv_attention = (TextView) root.findViewById(R.id.tv_attention);
        tv_stylebq = (TextView) root.findViewById(R.id.tv_stylebq);
        tv_aliatate = (TextView) root.findViewById(R.id.tv_aliatate);
        tv_regtime = (TextView) root.findViewById(R.id.tv_regtime);
        tv_city = (TextView) root.findViewById(R.id.tv_city);
        startct = (LinearLayout) root.findViewById(R.id.startct);
        startct.setOnClickListener(this);
        swipe_me = (SwipeRefreshLayout) root.findViewById(R.id.swipe_me);
        img_bg = (ImageView) root.findViewById(R.id.img_bg);
        img_bg.setOnClickListener(this);
        systemcount = (LinearLayout) root.findViewById(R.id.systemcount);
        tv_sys = (TextView) root.findViewById(R.id.tv_sys);
        systemcount.setOnClickListener(this);
        tv_assist = (TextView) root.findViewById(R.id.tv_assist);
        tv_step = (TextView) root.findViewById(R.id.tv_step);
        linear_assist = (LinearLayout) root.findViewById(R.id.linear_assist);
        linear_assist.setOnClickListener(this);
        linear_step = (LinearLayout) root.findViewById(R.id.linear_step);
        linear_step.setOnClickListener(this);
        linear_add = (LinearLayout) root.findViewById(R.id.linear_add);
        linear_add.setOnClickListener(this);
        line_day = (LinearLayout) root.findViewById(R.id.line_day);
        line_day.setOnClickListener(this);
        img_setUp = (ImageView) root.findViewById(R.id.img_setUp);
        img_setUp.setOnClickListener(this);
        myheadIcon = (ImageView) root.findViewById(R.id.myheadIcon);
        myheadIcon.setOnClickListener(this);
        tv_nickname = (TextView) root.findViewById(R.id.tv_nickname);
        tv_age = (TextView) root.findViewById(R.id.tv_age);
        tv_school = (TextView) root.findViewById(R.id.tv_school);
        tv_lastaddress = (TextView) root.findViewById(R.id.tv_lastaddress);
        tv_registtime = (TextView) root.findViewById(R.id.tv_registtime);
        tv_star = (TextView) root.findViewById(R.id.tv_star);
        tv_nj = (TextView) root.findViewById(R.id.tv_nj);
        payali = (LinearLayout) root.findViewById(R.id.payali);
        payali.setOnClickListener(this);
        title_style = (LinearLayout) root.findViewById(R.id.title_style);
        title_style.setOnClickListener(this);
        startcl = (LinearLayout) root.findViewById(R.id.startcl);
        startcl.setOnClickListener(this);
        swipe_me.setEnabled(true);
        swipe_me.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipe_me.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryInfo();
                getLuad();
                getBgIcon();
                getData();
                getCircleCount();
                getDaySign();
            }
        });
        if (!settings.HEADICON.getValue().toString().equals("")) {
//            Picasso.with(getActivity()).load(settings.HEADICON.getValue().toString())
//                    .placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(myheadIcon);
            ViewUtil.setPicture(getActivity(), settings.HEADICON.getValue().toString(), R.drawable.default_head, myheadIcon, null);
        }
    }


    private String code;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.payali:
                // 帐号管理
                intent.setClass(getActivity(), PayAliActivity.class);
//                intent.putExtra("type", "1");
                intent.putExtra("type", alicodestate);
                if (alicodestate.equals("1")) {
                    intent.putExtra("code", code);
                }
                intent.putExtra("nick", tv_nickname.getText().toString());
                startActivity(intent);
                break;
            case R.id.title_style:
                // 标签
                intent.putStringArrayListExtra("stylechoise", stylechoise);
                intent.setClass(getActivity(), ChoiseStarActivity.class);
                startActivity(intent);
                break;
            case R.id.startcl:
                // systemtips
                intent.putExtra("star", tv_star.getText().toString());
                intent.setClass(getActivity(), TitleStyleActivity.class);
                startActivityForResult(intent, 12599);
                break;
            case R.id.myheadIcon:
                intent.putExtra("sign", tv_school.getText().toString());
                intent.putExtra("sexy", "男");
                intent.putExtra("nick", tv_nickname.getText().toString());
                intent.setClass(getActivity(), NameEditActivity.class);
                startActivityForResult(intent, 10086);
                break;
            case R.id.img_setUp:
                intent.setClass(getActivity(), SetUpActivity.class);
                startActivity(intent);
                break;
            case R.id.line_day:
                intent.setClass(getActivity(), DayActivity.class);
                startActivityForResult(intent, 4620);
                break;
            case R.id.linear_add:
                intent.putStringArrayListExtra("attentionlist", attem);
                intent.setClass(getActivity(), MyAddPeopleActivity.class);
                startActivity(intent);
                break;
            case R.id.linear_step:
                Toast.makeText(getActivity(), "不能踩自己哦", Toast.LENGTH_LONG).show();
                break;
            case R.id.linear_assist:
                Toast.makeText(getActivity(), "不能赞自己哦", Toast.LENGTH_LONG).show();
                break;
            case R.id.startct:
                BmobQuery<circlrmebgphpto> circlemephotos = new BmobQuery<>();
                circlemephotos.addWhereEqualTo("username", settings.phone.getValue());
                circlemephotos.findObjects(getActivity(), new FindListener<circlrmebgphpto>() {
                    @Override
                    public void onSuccess(List<circlrmebgphpto> list) {
                        Intent intent1 = new Intent();
                        intent1.putExtra("type", "me");
                        if (list.size() == 0) {
                            intent1.putExtra("otherbg", "0");
                            intent1.setClass(getActivity(), OtherCircleActivity.class);
                            startActivity(intent1);
                        } else if (list.get(0).getCirclebg() == null) {
                            intent1.putExtra("otherbg", "0");
                            intent1.setClass(getActivity(), OtherCircleActivity.class);
                            startActivity(intent1);
                        } else {
                            intent1.putExtra("otherbg", list.get(0).getCirclebg().getFileUrl(getActivity()));
                            intent1.setClass(getActivity(), OtherCircleActivity.class);
                            startActivity(intent1);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.img_bg:
                //更换背景
                new ActionSheetDialog(getActivity()).builder().setCancelable(false)
                        .setCanceledOnTouchOutside(false)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = new Intent();
                                intent.putExtra("type", "1");
                                intent.setClass(getActivity(), BlackBywActivity.class);
                                startActivity(intent);

                            }
                        }).addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Intent intent = new Intent();
                        intent.putExtra("type", "2");
                        intent.setClass(getActivity(), BlackBywActivity.class);
                        startActivity(intent);
                    }
                }).show();

                break;
            case R.id.systemcount:
                tv_sys.setVisibility(View.GONE);
                intent.setClass(getActivity(), SystemMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != getActivity().RESULT_OK)
            return;
        switch (requestCode) {
            case 4620:
                tv_signqd.setVisibility(View.VISIBLE);
                break;
            case 12580:
                tv_nj.setText(data.getStringExtra("leaver"));
                break;

            case 12599:
                tv_star.setText(data.getStringExtra("star"));
                break;
            case 10086:
                tv_nickname.setText(data.getStringExtra("nickname"));
                tv_age.setText(data.getStringExtra("age").replace("岁", ""));
                tv_school.setText(data.getStringExtra("school"));
                if (data.getStringExtra("sex").equals(getString(R.string.boy))) {
                    tv_age.setBackgroundResource(R.drawable.sex_man);
                } else if (data.getStringExtra("sex").equals(getString(R.string.girl))) {
                    tv_age.setBackgroundResource(R.drawable.sex_women);
                }
                tv_school.setText(data.getStringExtra("sign"));
                break;
            default:
                break;
        }
    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
//            Picasso.with(getActivity()).load(settings.HEADICON.getValue().toString()).error(R.drawable.ic_launcher)
//                    .placeholder(R.drawable.ic_launcher).into(myheadIcon);
//            ViewUtil.setPicture(getActivity(), settings.HEADICON.getValue().toString(), R.drawable.default_head, myheadIcon, null);
            Upload(intent.getStringExtra("path"));
        }
    }

    LoginDialog dialog;
    BmobFile bmobFile;

    public void Upload(String event) {
        String uri = event;
        File path = new File(event);
        dialog.show();
        bmobFile = new BmobFile(path);
        bmobFile.uploadblock(getActivity(), new UploadFileListener() {
            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                circlrmebgphpto circlrmebgphptos = new circlrmebgphpto();
                circlrmebgphptos.setMebg(bmobFile);
                circlrmebgphptos.update(getActivity(), settings.OBJECT_IDBYCIRCLEBG.getValue().toString(), new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
//                        Picasso.with(NameEditActivity.this).load(path).error(R.drawable.ic_launcher)
//                                .placeholder(R.drawable.ic_launcher).into(img_headIcon);
                        ViewUtil.setPicture(getActivity(), bmobFile.getFileUrl(getActivity()), R.drawable.default_head, img_bg, null);
//                        settings.OBJECT_IDBYCIRMEBG.setValue(bmobFile.getFileUrl(getActivity()));
                    }

                    @Override
                    public void onFailure(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        Toast.makeText(getActivity(), arg1, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Toast.makeText(getActivity(), arg1, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData() {
        BmobQuery<coin> coins = new BmobQuery<>();
        coins.addWhereEqualTo("username", settings.phone.getValue());
        coins.findObjects(getActivity(), new FindListener<coin>() {
            @Override
            public void onSuccess(List<coin> list) {
                if (list.size() == 0) {
                    tv_signqd.setVisibility(View.GONE);
                    //如果没有创建表,则默认给值为0,然后创建
                } else {
                    //判断是否已经签到
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    sdf.format(date);
                    if (list.get(0).getUpdatedAt().startsWith(sdf.format(date).substring(0, 10)) && !list.get(0).getCount().equals("0")) {
                        //代表已经签到
                        tv_signqd.setVisibility(View.VISIBLE);
                    } else {
                        tv_signqd.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
            }
        });

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
