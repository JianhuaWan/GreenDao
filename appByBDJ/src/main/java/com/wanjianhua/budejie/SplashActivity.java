package com.wanjianhua.budejie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by machenike on 2016/5/25.
 */
public class SplashActivity extends Activity {

    private ImageView bg_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bg_img = (ImageView) findViewById(R.id.img_splash_bg);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(bg_img, "alpha", 0.0f, 1.0f);
        objectAnimator.setDuration(3000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
        objectAnimator.start();


    }
}
