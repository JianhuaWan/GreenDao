package com.xiangyue.act;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangyue.adpter.DragAdapter;
import com.xiangyue.adpter.OtherAdapter;
import com.xiangyue.base.BaseActivity;
import com.xiangyue.base.MicroRecruitSettings;
import com.xiangyue.bean.StyleBean;
import com.xiangyue.bean.StyleKinds;
import com.xiangyue.type.User;
import com.xiangyue.weight.DragGrid;
import com.xiangyue.weight.OtherGridView;

import java.util.ArrayList;

import cn.bmob.v3.listener.UpdateListener;

public class ChoiseStarActivity extends BaseActivity implements OnItemClickListener, View.OnClickListener {

    private DragGrid userGridView;
    private OtherGridView otherGridView;
    private DragAdapter userAdapter;
    private OtherAdapter otherAdapter;
    private ArrayList<String> otherChannelList = new ArrayList<String>();
    private ArrayList<String> userChannelList = new ArrayList<String>();
    private boolean isMove = false;
    private static final String Subscription = "1";
    private static final String Has_not_subscribed = "0";
    private RelativeLayout profession_setting;
    private MicroRecruitSettings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.choisestar_main);
        initTools();
        inieView();
        initData();
    }

    private void initTools() {
        settings = new MicroRecruitSettings(ChoiseStarActivity.this);
    }

    private TextView img_situp;

    private void inieView() {
        img_situp = (TextView) findViewById(R.id.img_situp);
        userGridView = (DragGrid) findViewById(R.id.userGridView);
        otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
    }

    private void initData() {
        otherChannelList = styleKinds.getAll();
        if (getIntent().getStringArrayListExtra("stylechoise") == null) {
            Toast.makeText(ChoiseStarActivity.this, "目前尚未选择标签", Toast.LENGTH_LONG).show();
        } else {
            userChannelList = getIntent().getStringArrayListExtra("stylechoise");
            for (int i = 0; i < userChannelList.size(); i++) {
                for (int j = 0; j < otherChannelList.size(); j++) {
                    if (userChannelList.get(i).equals(otherChannelList.get(j))) {
                        otherChannelList.remove(j);
                    }
                }
            }
        }

        setData();
    }


    StyleKinds styleKinds = new StyleKinds();

    private void setData() {
        userAdapter = new DragAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(otherAdapter);
        // 设置GRIDVIEW的ITEM的点击监听
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
        img_situp.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view,
                            final int position, long id) {
        // 如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if (isMove) {
            return;
        }
        final ImageView moveImageView = getView(view);
        switch (parent.getId()) {
            case R.id.userGridView:
                // position为 0，1 的不可以进行任何操作
//                if (position != 0) {
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view
                            .findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((DragAdapter) parent
                            .getAdapter()).getItem(position);// 获取点击的频道内容
                    otherAdapter.setVisible(false);
                    // 添加到最后一个
                    otherAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                // 获取终点的坐标
                                otherGridView.getChildAt(
                                        otherGridView.getLastVisiblePosition())
                                        .getLocationInWindow(endLocation);
                                moveAnim(moveImageView, startLocation,
                                        endLocation, channel, userGridView);
                                userAdapter.setRemove(position);
                            } catch (Exception localException) {
                                localException.printStackTrace();
                            }
                        }
                    }, 50L);
                }
//                }
                break;
            case R.id.otherGridView:
//                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view
                            .findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((OtherAdapter) parent.getAdapter())
                            .getItem(position);
                    userAdapter.setVisible(false);
                    // 添加到最后一个
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                // 获取终点的坐标
                                userGridView.getChildAt(
                                        userGridView.getLastVisiblePosition())
                                        .getLocationInWindow(endLocation);
                                moveAnim(moveImageView, startLocation, endLocation,
                                        channel, otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                                localException.printStackTrace();
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 点击ITEM移动动画
     *
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void moveAnim(View moveView, int[] startLocation,
                          int[] endLocation, final String moveChannel,
                          final GridView clickGridView) {
        // 将当前栏目增加到改变过的listview中 若栏目已经存在删除点，不存在添加进去

        int[] initLocation = new int[2];
        // 获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        // 得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView,
                initLocation);
        // 创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);// 动画时间
        // 动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }


    /**
     * 获取点击的Item的对应View，
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_situp:
                if (userChannelList.size() == 0) {
                    Toast.makeText(ChoiseStarActivity.this, "至少选择一项", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User();
                    user.setStylebq(userChannelList);
                    user.update(ChoiseStarActivity.this, settings.OBJECT_ID.getValue().toString(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            //提交
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(ChoiseStarActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    });

                }
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
