package com.example.compulinkapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compulinkapp.R;
import com.example.compulinkapp.classes.Conect;

import java.util.concurrent.ExecutionException;

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
                Intent forgotPasswordOpen = new Intent(v.getContext(), ForgotPasswordActivity.class);
                startActivity(forgotPasswordOpen);
            }
        });

        Button registerBut = (Button) findViewById(R.id.registerBut);

        registerBut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent registerPageOpen = new Intent(v.getContext(), RegisterAccountActivity.class);
                startActivity(registerPageOpen);
            }
        });

        Button login = (Button) findViewById(R.id.loginBut);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(v.getContext(), DashActivity.class);
                if(loginValidation())
                {

                    try {

                        if (loginCredentialCheck().contains("1")){
                            Log.d("Testing Response:" ,"We are indeed, returning true" );
                            startActivity(dash);
                        } else if(loginCredentialCheck().contains("0")){
                            Log.d("Testing Response:" ,"We are indeed, returning false" );
                        } else{
                            Toast.makeText(getApplicationContext(), "Invalid Login Credentials", Toast.LENGTH_LONG).show();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                else Toast.makeText(getApplicationContext(), "Invalid Login Criteria", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Method to perform basic input validation on login
     * @return true or false based on if validation passes
     */ //loginValidation Method - Start
    private boolean loginValidation() {

        boolean valid;
        //Initialising variables from user_input_fields - Start
        TextView usernameInp = findViewById(R.id.usernameInput);
        TextView passwordInp = findViewById(R.id.pwInp);
        //Initialising variables from user_input_fields - Start

        //Validation - Start
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
        //Validation - End
    };
    //loginValidation Method - End
//loginCredentialCheck Method - Start
    private String loginCredentialCheck() throws ExecutionException, InterruptedException, ExecutionException {

        //Initialising variables from user_input_fields - Start
        TextView usernameInp = findViewById(R.id.usernameInput);
        TextView passwordInp = findViewById(R.id.pwInp);
        //Initialising variables from user_input_fields - Start

        //Constructing Post Variable - Start
        String postVar = "HANDLE_LOGIN";
        postVar = postVar   + "-"     + "username="       +  usernameInp    .getText()  .toString();
        postVar = postVar   + "-"     + "password="       +  passwordInp    .getText()  .toString();
        //Constructing Post Variable - End

        Conect LoginConnection = new Conect();

        String testing = (String) LoginConnection.execute(postVar).get();
        Log.d("Testing Response:" ,"Android Recieved response: " + testing + "and some more" + testing + testing + testing + testing);


        return testing;
    };

}
