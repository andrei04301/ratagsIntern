package com.raTags.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CardTouristDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_tourist_details);

        User model = (User) getIntent().getSerializableExtra("model");

        TextView card_tourist_name = findViewById(R.id.card_tourist_name);
        TextView card_tourist_address = findViewById(R.id.card_tourist_address);
//        card_tourist_name.setText(model.getName());
//        card_tourist_address.setText(model.getAddress());
    }
}