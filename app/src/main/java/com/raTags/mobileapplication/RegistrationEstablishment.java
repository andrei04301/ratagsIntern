package com.raTags.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationEstablishment extends AppCompatActivity {
    private EditText estNameEditText;
    private EditText estTypeEditText;
    private EditText managerEmailEditText;
    private EditText estLocEditText;
    private EditText estLatEditText;
    private Button btnRegisterEst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_establishment);

        estNameEditText = (EditText) findViewById(R.id.editNameEst);
        estTypeEditText = (EditText) findViewById(R.id.editTypeEst);
        managerEmailEditText = (EditText) findViewById(R.id.editEmailManager);
        estLocEditText = (EditText) findViewById(R.id.editLocationLong);
        estLatEditText = (EditText) findViewById(R.id.editLocationLat);

        Button btnRegisterEst = (Button) findViewById(R.id.btnRegisterEst);

    }
}