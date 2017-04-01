//package com.xiangyue.act;
//
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.animation.PropertyValuesHolder;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.AccelerateInterpolator;
//import android.widget.LinearLayout;
//
//import com.xiangyue.base.BaseActivity;
//
//public class TestAnimationActivity extends BaseActivity implements OnClickListener {
//    DisplayMetrics metrics = null;
//    private LinearLayout linear_2, linear_3, linear_1;
//
//    @Override
//    protected void onCreate(Bundle arg0) {
//        // TODO Auto-generated method stub
//        super.onCreate(arg0);
//        setContentView(R.layout.testanimation_main);
//        metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        linear_1 = (LinearLayout) findViewById(R.id.linear_1);
//        linear_2 = (LinearLayout) findViewById(R.id.linear_2);
//        linear_3 = (LinearLayout) findViewById(R.id.linear_3);
//
//        rotateyAnimRun();
//    }
//
//    private static final float BALL_SIZE = 100f;
//    private static final int DURATION = 1500;
//
//    private void rotateyAnimRun() {
//        // 2
//        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", linear_2.getY() + 30, metrics.heightPixels / 2
//                + BALL_SIZE);
//        PropertyValuesHolder pvhY2 = PropertyValuesHolder.ofFloat("y", metrics.heightPixels / 2 + BALL_SIZE,
//                metrics.heightPixels / 2 - BALL_SIZE);
//        PropertyValuesHolder pvhY3 = PropertyValuesHolder.ofFloat("y", metrics.heightPixels / 2 - BALL_SIZE,
//                metrics.heightPixels / 2 - 100);
//        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 0.5f);
//        PropertyValuesHolder pvhAlpha2 = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
//        PropertyValuesHolder pvhAlpha3 = PropertyValuesHolder.ofFloat("alpha", 1f, 1f);
//        ObjectAnimator yAlphaBouncer = ObjectAnimator.ofPropertyValuesHolder(linear_2, pvhY, pvhAlpha).setDuration(400);
//        ObjectAnimator yAlphaBouncer2 = ObjectAnimator.ofPropertyValuesHolder(linear_2, pvhY2, pvhAlpha2).setDuration(
//                300);
//        ObjectAnimator yAlphaBouncer3 = ObjectAnimator.ofPropertyValuesHolder(linear_2, pvhY3, pvhAlpha3).setDuration(
//                300);
//        yAlphaBouncer.setInterpolator(new AccelerateInterpolator());
//        yAlphaBouncer2.setInterpolator(new AccelerateInterpolator());
//        yAlphaBouncer3.setInterpolator(new AccelerateInterpolator());
//        AnimatorSet bounceAnim = new AnimatorSet();
//        ((AnimatorSet) bounceAnim).play(yAlphaBouncer3).after(yAlphaBouncer2).after(yAlphaBouncer);
//        bounceAnim.start();
//    }
//
//    @Override
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.linear_2:
//                break;
//
//            default:
//                break;
//        }
//    }
//}
