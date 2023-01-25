package com.raTags.mobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);



        drawer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        navView = findViewById(R.id.navView);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.Categories) {
                    Intent intent = new Intent(NavigationDrawer.this, Categories.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.AboutUs) {
                    Intent intent = new Intent(NavigationDrawer.this, AboutUs.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.HowtoUse) {
                    Intent intent = new Intent(NavigationDrawer.this, HowToUse.class);
                    startActivity(intent);
                    return true;
                } else {
                    Intent intent = new Intent(NavigationDrawer.this, AboutTagaytayPage.class);
                    startActivity(intent);
                    return true;

                }
            }
        });

//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.Categories:
//                        Toast.makeText(getApplicationContext(),"Categories",Toast.LENGTH_LONG).show();
//                        drawer.closeDrawer(GravityCompat.START);
//                }
//
//                }
//            }
//        }));
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {

    }
}