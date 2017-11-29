package com.example.sahin.navigasyon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener{

    ImageView imglogo;
    public Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imglogo=(ImageView)findViewById(R.id.imgsecreenlogo);

        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animsecreen);
        animation.setAnimationListener(this);
        imglogo.startAnimation(animation);
        Timer tm= new Timer();
        TimerTask tmtsk=new TimerTask() {
            @Override
            public void run()
            {
                Intent in=new Intent(SplashScreen.this,MapsActivity.class);
                startActivity(in);
            }
        };
        tm.schedule(tmtsk,2000);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
