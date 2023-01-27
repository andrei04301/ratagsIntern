package com.raTags.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button btnHow, btnCategories;

    //Variables
    Animation topAnim, bottomAnim, scaleUp, scaleDown;
    ImageView logo;
    Button splash_btn1, splash_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        //Hooks
        logo = findViewById(R.id.applogo);
        splash_btn1 = findViewById(R.id.btnHow);
        splash_btn2 = findViewById(R.id.btnCategories);

        logo.setAnimation(topAnim);
        splash_btn1.setAnimation(bottomAnim);
        splash_btn2.setAnimation(bottomAnim);

        btnHow = (Button) findViewById(R.id.btnHow);
        btnCategories = (Button) findViewById(R.id.btnCategories);
//        btnHow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openActivity2();
//            }
//        });
//    }
//    public void openActivity2(){
//        Intent intent = new Intent(this, SignupForm.class);
//        startActivity((intent));
//    }

        btnHow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnHow.startAnimation(scaleUp);
                    Intent intent = new Intent(MainActivity.this, RegistrationUser.class);
                    startActivity(intent);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnHow.startAnimation(scaleDown);
                    Intent intent = new Intent(MainActivity.this, RegistrationUser .class);
                    startActivity(intent);
                }

                return true;
            }
        });

        btnCategories.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    btnCategories.startAnimation(scaleUp);
                    Intent intent = new Intent(MainActivity.this, RegistrationUser.class);
                    startActivity(intent);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    btnCategories.startAnimation(scaleDown);
                    Intent intent = new Intent(MainActivity.this, RegistrationUser.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
}
