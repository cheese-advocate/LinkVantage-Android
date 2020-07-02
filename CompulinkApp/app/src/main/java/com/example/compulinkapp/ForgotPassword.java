package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        /**
         * General useful information to the user, adds to user friendliness
         */
        Toast.makeText(getApplicationContext(), "To reset your password an OTP will be sent to you either via\nEmail or\nTo your account on the website", Toast.LENGTH_LONG).show();

        final Button returnToLogin = (Button) findViewById(R.id.backToLogin);

        returnToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                returnToLogin.startAnimation(anim);
                Intent backToLogin = new Intent(v.getContext(), LoginActivity.class);
                startActivity(backToLogin);
            }
        });

        /**
         * Reset submission button variable
         */
        final Button resetPasswordSubmit = (Button) findViewById(R.id.reset_password_submit);
        /**
         * Variables used to validate the email entered when resetting password
         */
        final EditText email = findViewById(R.id.emailInp);
        final InputValidatorHelper helper = new InputValidatorHelper();

        resetPasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                resetPasswordSubmit.startAnimation(anim);
                if(helper.isEmail(email).equals("false"))//invalid email
                {
                    Toast.makeText(v.getContext(), "Invalid email entered\nEmail should look like the following:\nexample@example.com", Toast.LENGTH_LONG).show();
                }
                else//valid email
                {
                    Intent newPassword = new Intent(v.getContext(), NewPassword.class);
                    startActivity(newPassword);
                }

            }
        });
    }
}
