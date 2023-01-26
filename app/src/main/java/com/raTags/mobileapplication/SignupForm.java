package com.raTags.mobileapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupForm extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);

        firstNameEditText = (EditText) findViewById(R.id.editFirstName);
        lastNameEditText = (EditText) findViewById(R.id.editLastName);
        emailEditText = (EditText) findViewById(R.id.editEmail);
        phoneEditText = (EditText) findViewById(R.id.editCellphone);
        passwordEditText = (EditText) findViewById(R.id.editPassword1);
        confirmPasswordEditText = (EditText) findViewById(R.id.editPassword2);

        Button signupButton = (Button) findViewById(R.id.btnSignup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();

            }
        });
    }
}
