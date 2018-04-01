package com.example.mohitrajpanday.mmusicplayer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Screen extends Activity {
ImageView iv1,iv2,iv3;
Animation an1,an2,an3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash__screen);


        iv1=findViewById(R.id.imageView);
        iv2=findViewById(R.id.imageView2);
        iv3=findViewById(R.id.imageView3);
        an1= AnimationUtils.loadAnimation(this,R.anim.animation1);
        an2= AnimationUtils.loadAnimation(this,R.anim.animation2);
        an3= AnimationUtils.loadAnimation(this,R.anim.animation3);


        an1.setStartTime(Animation.START_ON_FIRST_FRAME);
        iv1.startAnimation(an1);


        an2.setStartTime(0);
        iv2.startAnimation(an2);


        an3.setStartTime(0);
        iv3.startAnimation(an3);

        an3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
