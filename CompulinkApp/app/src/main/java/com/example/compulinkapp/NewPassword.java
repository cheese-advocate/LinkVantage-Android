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

public class NewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        final Button submit = (Button) findViewById(R.id.new_password_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                submit.startAnimation(anim);
                if(newPasswordValidation().equals("Empty"))
                {
                    Toast.makeText(v.getContext(), "One or more password fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(newPasswordValidation().equals("Invalid"))
                {
                    Toast.makeText(v.getContext(), "Invalid password received\nPassword should contain to following\nAt least one uppercase letter, one number, one special character and be 8 characters at least"
                            , Toast.LENGTH_LONG).show();
                }
                else if(newPasswordValidation().equals("No Match"))
                {
                    Toast.makeText(v.getContext(), "Both passwords need to match", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    Toast.makeText(v.getContext(), "New Password submitted successfully", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Input validation method for the newly entered password
     * @return String message with result - Valid, Empty, No Match, Invalid
     */
    private String newPasswordValidation()
    {
        InputValidatorHelper helper = new InputValidatorHelper();
        EditText newPassword = findViewById(R.id.new_password_inp);
        EditText newPasswordConfirm = findViewById(R.id.new_password_inp_confirm);

        if(newPassword.getText().toString().trim().equals("") || newPasswordConfirm.getText().toString().trim().equals(""))
        {
            return "Empty";
        }

        if(helper.validPassword(newPassword.getText().toString()) && helper.validPassword(newPasswordConfirm.getText().toString()))
        {
            if(newPassword.getText().toString().trim().equals(newPasswordConfirm.getText().toString().trim()))
            {
                return "Valid";
            }
            else return "No Match";
        }
        else return "Invalid";
    }
}