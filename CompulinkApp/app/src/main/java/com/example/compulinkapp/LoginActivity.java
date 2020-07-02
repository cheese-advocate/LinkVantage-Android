package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final TextView forgotPw = (TextView) findViewById(R.id.forgortPwLink);

        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                forgotPw.startAnimation(anim);
                Intent forgotPasswordOpen = new Intent(v.getContext(), ForgotPassword.class);
                startActivity(forgotPasswordOpen);
            }
        });

        final Button registerBut = (Button) findViewById(R.id.registerBut);

        registerBut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                registerBut.startAnimation(anim);
                Intent registerPageOpen = new Intent(v.getContext(), RegisterAccount.class);
                startActivity(registerPageOpen);
            }
        });

        final Button login = (Button) findViewById(R.id.loginBut);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                login.startAnimation(anim);
                Intent dash = new Intent(v.getContext(), Dash.class);
                if(loginValidation())
                {
                    startActivity(dash);
                }
                else Toast.makeText(getApplicationContext(), "Invalid login", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Method to perform basic input validation on login
     * @return true or false based on if validation passes
     */
    private boolean loginValidation()
    {
        boolean valid;

        TextView usernameInp = findViewById(R.id.usernameInput);
        TextView passwordInp = findViewById(R.id.pwInp);

        if(usernameInp.getText().toString().trim().equals(""))
        {
            valid = false;
        }
        else if(passwordInp.getText().toString().trim().equals(""))
        {
            valid = false;
        }
        else valid = true;

        if(valid)
        {
            return true;
        }
        else return false;
    };
}
