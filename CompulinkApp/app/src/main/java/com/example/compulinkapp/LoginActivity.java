package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        TextView forgotPw = (TextView) findViewById(R.id.forgortPwLink);

        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent forgotPasswordOpen = new Intent(v.getContext(), ForgotPassword.class);
                startActivity(forgotPasswordOpen);
            }
        });

        Button registerBut = (Button) findViewById(R.id.registerBut);

        registerBut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent registerPageOpen = new Intent(v.getContext(), RegisterAccount.class);
                startActivity(registerPageOpen);
            }
        });

        Button login = (Button) findViewById(R.id.loginBut);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(v.getContext(), Dash.class);
                startActivity(dash);
            }
        });
    }
}
