package com.raTags.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class AboutUs extends NavigationDrawer {

    private Button button;
    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.activity_about_us, null, false);

        drawer.addView(v, 0);


        button = (Button) findViewById(R.id.btnExploreNow);
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    button.startAnimation(scaleUp);
                    Intent intent = new Intent(AboutUs.this, Categories.class);
                    startActivity(intent);
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    button.startAnimation(scaleDown);
                    Intent intent = new Intent(AboutUs.this, Categories.class);
                    startActivity(intent);
                }



                return false;
            }
        });
    }
}