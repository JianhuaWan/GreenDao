package com.xiangyue.act;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.coin;
import com.xiangyue.weight.AlertDialog;
import com.xiangyue.weight.LoginDialog;

import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by wWX321637 on 2016/5/11.
 */
public class GameActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private ImageView img_random1, img_random2, img_random3;
    private ImageView img_situp;
    private ImageView img_chage;
    private Drawable[] drawable_Anims;// 动画
    private MicroRecruitSettings settings;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.game_main);
        initTools();
        initView();
        startAnimation();
        getRandom();
        initVoiceAnimRes();
        showAnimation();

    }

    private void initTools() {
        settings = new MicroRecruitSettings(GameActivity.this);
    }

    private void showAnimation() {
        handler.postDelayed(runnable, 100);
    }

    private int nowtime;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            nowtime++;
            if (nowtime == 3) {
                nowtime = 0;
            }
            img_chage.setImageDrawable(drawable_Anims[nowtime]);
            handler.postDelayed(this, 100);
        }
    };


    private void initVoiceAnimRes() {
        drawable_Anims = new Drawable[]{
                getResources().getDrawable(R.drawable.chat_room_challenge_scissors_icon),
                getResources().getDrawable(R.drawable.chat_room_challenge_paper_icon),
                getResources().getDrawable(R.drawable.chat_room_challenge_rock_icon)};
    }

    private int overr;

    private void getRandom() {
        Random random = new Random();
        overr = random.nextInt(4);
    }

    Animation animation;

    private void startAnimation() {

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        img_random1.startAnimation(animation);
        img_random2.startAnimation(animation);
        img_random3.startAnimation(animation);

    }

    private void initView() {
        dialog = new LoginDialog(GameActivity.this, "");
        dialog.setCanceledOnTouchOutside(false);
        img_chage = (ImageView) findViewById(R.id.img_chage);
        img_situp = (ImageView) findViewById(R.id.img_situp);
        img_situp.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("三选一");
        img_random1 = (ImageView) findViewById(R.id.img_random1);
        img_random2 = (ImageView) findViewById(R.id.img_random2);
        img_random3 = (ImageView) findViewById(R.id.img_random3);
        img_random1.setOnClickListener(this);
        img_random2.setOnClickListener(this);
        img_random3.setOnClickListener(this);
    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animation != null) {
            animation = null;
        }
        img_random1.clearAnimation();
        img_random2.clearAnimation();
        img_random3.clearAnimation();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_situp:
                new AlertDialog(GameActivity.this).builder().setTitle("提示")
                        .setMsg("消耗10根辣条再玩一次?")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //先去判断数量是否满足条件
                                getCoin();

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
                break;
            case R.id.img_random1:
                handler.removeCallbacks(runnable);// 移除线程
                img_random1.setEnabled(false);
                img_random2.setEnabled(false);
                img_random3.setEnabled(false);
                if (overr == 0) {
                    //中
                    showShortToast().show();
                    img_chage.setImageDrawable(drawable_Anims[0]);
                    AddCoin();
                } else if (overr == 3) {
                    Toast.makeText(GameActivity.this, "平局", Toast.LENGTH_LONG).show();
                    getRandom();//重新获取一遍随机数
                    showAnimation();
                    img_random1.startAnimation(animation);
                    img_random2.startAnimation(animation);
                    img_random3.startAnimation(animation);
                    img_random1.setEnabled(true);
                    img_random2.setEnabled(true);
                    img_random3.setEnabled(true);

                } else {
                    //未中
                    Toast.makeText(GameActivity.this, "真可惜", Toast.LENGTH_LONG).show();
                    img_chage.setImageDrawable(drawable_Anims[1]);
                }
                break;
            case R.id.img_random2:
                handler.removeCallbacks(runnable);// 移除线程
                img_random1.setEnabled(false);
                img_random2.setEnabled(false);
                img_random3.setEnabled(false);
                if (overr == 1) {
                    //中
                    showShortToast().show();
                    img_chage.setImageDrawable(drawable_Anims[1]);
                    //中奖后再原有基础上+20
                    AddCoin();


                } else if (overr == 3) {
                    Toast.makeText(GameActivity.this, "平局", Toast.LENGTH_LONG).show();
                    getRandom();//重新获取一遍随机数
                    showAnimation();
                    img_random1.startAnimation(animation);
                    img_random2.startAnimation(animation);
                    img_random3.startAnimation(animation);
                    img_random1.setEnabled(true);
                    img_random2.setEnabled(true);
                    img_random3.setEnabled(true);

                } else {
                    //未中
                    img_chage.setImageDrawable(drawable_Anims[2]);
                    Toast.makeText(GameActivity.this, "真可惜", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.img_random3:
                handler.removeCallbacks(runnable);// 移除线程
                img_random1.setEnabled(false);
                img_random2.setEnabled(false);
                img_random3.setEnabled(false);
                if (overr == 2) {
                    //中
                    showShortToast().show();
                    img_chage.setImageDrawable(drawable_Anims[2]);
                    AddCoin();
                } else if (overr == 3) {
                    Toast.makeText(GameActivity.this, "平局", Toast.LENGTH_LONG).show();
                    getRandom();//重新获取一遍随机数
                    showAnimation();
                    img_random1.startAnimation(animation);
                    img_random2.startAnimation(animation);
                    img_random3.startAnimation(animation);
                    img_random1.setEnabled(true);
                    img_random2.setEnabled(true);
                    img_random3.setEnabled(true);
                } else {
                    //未中
                    img_chage.setImageDrawable(drawable_Anims[0]);
                    Toast.makeText(GameActivity.this, "真可惜", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    private void getCoin() {
        BmobQuery<coin> coinBmobQuery = new BmobQuery<>();
        coinBmobQuery.getObject(getActivity(), settings.OBJECT_IDBYCOIN.getValue(), new GetListener<coin>() {
            @Override
            public void onSuccess(coin coin) {
                if (Integer.parseInt(coin.getCoincount()) >= 10) {
                    coin coin1 = new coin();
                    coin1.setUsername(settings.phone.getValue());
                    int coinover = Integer.parseInt(coin.getCoincount()) - 10;
                    coin1.setCoincount(coinover + "");
                    coin1.update(getActivity(), settings.OBJECT_IDBYCOIN.getValue(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            getRandom();//重新获取一遍随机数
                            showAnimation();
                            img_random1.startAnimation(animation);
                            img_random2.startAnimation(animation);
                            img_random3.startAnimation(animation);
                            img_random1.setEnabled(true);
                            img_random2.setEnabled(true);
                            img_random3.setEnabled(true);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "辣条不足", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
                dialog.dismiss();
            }
        });

    }


    private void AddCoin() {
        BmobQuery<coin> coinBmobQuery = new BmobQuery<>();
        coinBmobQuery.getObject(getActivity(), settings.OBJECT_IDBYCOIN.getValue(), new GetListener<coin>() {
            @Override
            public void onSuccess(coin coin) {
                coin coin1 = new coin();
                coin1.setUsername(settings.phone.getValue());
                int coinover = Integer.parseInt(coin.getCoincount()) + 20;
                coin1.setCoincount(coinover + "");
                coin1.update(getActivity(), settings.OBJECT_IDBYCOIN.getValue(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
                dialog.dismiss();
            }
        });

    }


    LoginDialog dialog;

    private Toast toast;

    private Toast showShortToast() {
        if (toast == null) {
            toast = new Toast(this);
        }
        View view = LayoutInflater.from(this).inflate(
                R.layout.win_short, null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        return toast;
    }
}
