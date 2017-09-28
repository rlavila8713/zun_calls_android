package com.xkoders.zuncallandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.application.ZunCallAndroidApplication;

public class Splash extends Activity {
    private long splashDelay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView iv = (ImageView)findViewById(R.id.imageView1);
        final ImageView iv2 = (ImageView)findViewById(R.id.imageView2);

        final Animation appear = AnimationUtils.loadAnimation(Splash.this, R.anim.appear);
        appear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                final Animation shrink = AnimationUtils.loadAnimation(Splash.this, R.anim.shrink_from_topright_to_bottomleft);
                shrink.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        iv.setVisibility(View.INVISIBLE);
                        goOn(iv2);
                    }
                });

                iv.startAnimation(shrink);
            }
        });
        iv.startAnimation(appear);

    }

    public void goOn(final ImageView iv2){
        final Animation appear = AnimationUtils.loadAnimation(Splash.this, R.anim.appear);
        appear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                iv2.setVisibility(View.VISIBLE);
            }
        });

        iv2.startAnimation(appear);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                overridePendingTransition(R.anim.pull_in_from_right, R.anim.pull_out_to_left);
                finish();
            }
        }, splashDelay);
    }

}
