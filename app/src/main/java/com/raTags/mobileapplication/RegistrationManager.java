package com.raTags.mobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationManager extends AppCompatActivity {
    private EditText managerNameEditText;
    private EditText managerLastEditText;
    private EditText managerEmailEditText;
    private EditText managerPhoneEditText;
    private EditText managerPasswordEditText;
    private EditText managerConfirmPasswordEditText;
    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_manager);

        managerNameEditText = (EditText) findViewById(R.id.editFirstNameManager);
        managerLastEditText = (EditText) findViewById(R.id.editLastNameManager);
        managerEmailEditText = (EditText) findViewById(R.id.editEmailManager);
        managerPhoneEditText = (EditText) findViewById(R.id.editCellphoneManager);
        managerPasswordEditText = (EditText) findViewById(R.id.editPassword1Manager);
        managerConfirmPasswordEditText = (EditText) findViewById(R.id.editPassword2Manager);

        Button continueBtn = (Button) findViewById(R.id.btnContinueManager);

    }
}