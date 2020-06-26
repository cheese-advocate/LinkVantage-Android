package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Toast.makeText(getApplicationContext(), "To reset your password an OTP will be sent to you either via\nEmail or\nTo your account on the website", Toast.LENGTH_LONG).show();

        Button returnToLogin = (Button) findViewById(R.id.backToLogin);

        returnToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent backToLogin = new Intent(v.getContext(), LoginActivity.class);
                startActivity(backToLogin);
            }
        });

        Button resetPasswordSubmit = (Button) findViewById(R.id.reset_password_submit);

        resetPasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPassword = new Intent(v.getContext(), NewPassword.class);
                startActivity(newPassword);
            }
        });
    }
}
