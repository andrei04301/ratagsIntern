package com.raTags.mobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSignup extends AppCompatActivity {
    private Button btn_login, btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_signup);

    btn_login = (Button) findViewById(R.id.btn_login);
    btn_signup = (Button) findViewById(R.id.btn_signup);

        btn_login.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                Intent intent = new Intent(LoginSignup.this, HowToUse.class);
                startActivity(intent);
            }
            else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                Intent intent = new Intent(LoginSignup.this, HowToUse.class);
                startActivity(intent);
            }

            return true;
        }
    });

        btn_signup.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                Intent intent = new Intent(LoginSignup.this, Categories.class);
                startActivity(intent);
            }
            else if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                Intent intent = new Intent(LoginSignup.this, Categories.class);
                startActivity(intent);
            }
            return true;
        }
    });
}
}
