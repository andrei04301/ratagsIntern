package com.raTags.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Categories extends NavigationDrawer implements View.OnClickListener {
    private CardView CardTouristSpots, CardFoodSpots, CardCoffeeMilkTeaShops,
            CardAmusementSpots, CardHotels, CardBanks, CardHospitals,
            CardGovernmentOffices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.activity_categories, null, false);

        drawer.addView(v, 0);

        CardTouristSpots = (CardView) findViewById(R.id.CardTouristSpots);
        CardFoodSpots = (CardView) findViewById(R.id.CardFoodSpots);
        CardCoffeeMilkTeaShops = (CardView) findViewById(R.id.CardCoffeeMilkTeaShops);
        CardAmusementSpots = (CardView) findViewById(R.id.CardAmusementSpots);
        CardHotels = (CardView) findViewById(R.id.CardHotels);
        CardBanks = (CardView) findViewById(R.id.CardBanks);
        CardHospitals = (CardView) findViewById(R.id.CardHospitals);
        CardGovernmentOffices = (CardView) findViewById(R.id.CardGovernmentOffices);

        CardTouristSpots.setOnClickListener((View.OnClickListener) this);
        CardFoodSpots.setOnClickListener((View.OnClickListener) this);
        CardCoffeeMilkTeaShops.setOnClickListener((View.OnClickListener) this);
        CardAmusementSpots.setOnClickListener((View.OnClickListener) this);
        CardHotels.setOnClickListener((View.OnClickListener) this);
        CardBanks.setOnClickListener((View.OnClickListener) this);
        CardHospitals.setOnClickListener((View.OnClickListener) this);
        CardGovernmentOffices.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.CardTouristSpots:
                i = new Intent(this, CardTouristSpots.class);
                startActivity(i);
                break;
            case R.id.CardFoodSpots:
                i = new Intent(this, CardFoodSpots.class);
                startActivity(i);
                break;
            case R.id.CardCoffeeMilkTeaShops:
                i = new Intent(this, CardCoffeeMilkteaShops.class);
                startActivity(i);
                break;
            case R.id.CardAmusementSpots:
                i = new Intent(this, CardAmusementSpots.class);
                startActivity(i);
                break;
            case R.id.CardHotels:
                i = new Intent(this, CardHotels.class);
                startActivity(i);
                break;
            case R.id.CardBanks:
                i = new Intent(this, CardBanks.class);
                startActivity(i);
                break;
            case R.id.CardHospitals:
                i = new Intent(this, CardHospitals.class);
                startActivity(i);
                break;
            case R.id.CardGovernmentOffices:
                i = new Intent(this, CardGovernmentOffices.class);
                startActivity(i);
                break;

        }
        {}
//        setContentView(R.layout.activity_categories);
//
//        RecyclerView recyclerView = findViewById(R.id.categories);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        CategoryData[] categoryData = new CategoryData[]{
//                new CategoryData("Tourist Spots"),
//                new CategoryData("Food Spots"),
//                new CategoryData("Coffee & Milk Tea Shops"),
//                new CategoryData("Amusement Spots"),
//                new CategoryData("Hotels"),
//                new CategoryData("Banks"),
//                new CategoryData("Hospitals"),
//                new CategoryData("Government Offices"),
//        };
//
//        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryData, Categories.this);
//        recyclerView.setAdapter(categoryAdapter);
//    }
    }
}