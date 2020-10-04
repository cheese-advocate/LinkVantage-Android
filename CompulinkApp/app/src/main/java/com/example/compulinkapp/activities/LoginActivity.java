package com.example.compulinkapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    String accountType;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        //Get the shared preferences
        pref = getSharedPreferences("user_details", MODE_PRIVATE);

        if(autoLogin(pref))
        {
            if(accountType.equalsIgnoreCase("client"))
            {
                Intent intent = new Intent(getApplicationContext(), ClientDashActivity.class);
                startActivity(intent);
            }
            else if(accountType.equalsIgnoreCase("technician"))
            {
                Intent intent = new Intent(getApplicationContext(), DashActivity.class);
                startActivity(intent);
            }
        }

        final TextView forgotPw = (TextView) findViewById(R.id.forgortPwLink);

        forgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                forgotPw.startAnimation(anim);
                Intent forgotPasswordOpen = new Intent(v.getContext(), ForgotPasswordActivity.class);
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
                Intent registerPageOpen = new Intent(v.getContext(), RegisterAccountActivity.class);
                startActivity(registerPageOpen);
            }
        });

        final Button login = (Button) findViewById(R.id.loginBut);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                login.startAnimation(anim);
                Intent dash = new Intent(v.getContext(), DashActivity.class); //Activity to use when technician logs in
                Intent clientDash = new Intent(v.getContext(), ClientDashActivity.class); //Activity to use when client logs in
                if(loginValidation())
                {

                    try {

                        if (loginCredentialCheck()){
                            Log.d("Testing Response:" ,"We are indeed, returning true" );
                            if(accountType.equalsIgnoreCase("client"))
                            {
                                startActivity(clientDash);
                            }
                            else if(accountType.equalsIgnoreCase("technician"))
                            {
                                startActivity(dash);
                            }
                        } else if(!loginCredentialCheck()){
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
//loginCredentialCheck Method - START
    private boolean loginCredentialCheck() throws ExecutionException, InterruptedException, ExecutionException {

        SharedPreferences.Editor editor = pref.edit();
        //Initialising variables from user_input_fields - Start
        TextView usernameInp = findViewById(R.id.usernameInput);
        TextView passwordInp = findViewById(R.id.pwInp);
        //Initialising variables from user_input_fields - Start
        editor.putString("username", usernameInp.getText().toString().trim());
        editor.putString("password", passwordInp.getText().toString().trim());
        editor.apply();
        /*//Constructing Post Variable - Start
        String postVar = "HANDLE_LOGIN";
        postVar = postVar   + "-"     + "username="       +  usernameInp    .getText()  .toString();
        postVar = postVar   + "-"     + "password="       +  passwordInp    .getText()  .toString();
        //Constructing Post Variable - End

        Conect LoginConnection = new Conect();

        String loginResponse = (String) LoginConnection.execute(postVar).get();
        Log.d("Testing Response:" ,"Android Recieved response: " + loginResponse );
        LoginConnection.cancel(true); //ends the process after all code has completed*/

        String postVar = "NEW_HANDLE_LOGIN";
        JSONObject data = new JSONObject();
        try
        {
            data.put("username", usernameInp.getText().toString().trim());
            data.put("password", passwordInp.getText().toString().trim());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        postVar = postVar + "-" + data.toString();
        Conect connection = new Conect();

        String loginResponse = connection.execute(postVar).get().toString();

        Boolean response = false;
        try
        {
            JSONObject obj = new JSONObject(loginResponse.substring(loginResponse.indexOf("{"), loginResponse.lastIndexOf("}") + 1));
            response = obj.getBoolean("result");
            accountType = obj.getString("accountType");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return response;
    }
//loginCredentialCheck Method - END

    public boolean autoLogin(SharedPreferences pref)
    {
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);

        if(autoLoginCheck(username, password))
        {
            return true;
        }else return false;
    }

    public boolean autoLoginCheck(String username, String password)
    {
        if(username == null || password == null)
        {
            return false;
        }
        String postVar = "NEW_HANDLE_LOGIN";
        JSONObject data = new JSONObject();
        try
        {
            data.put("username", username);
            data.put("password", password);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        postVar = postVar + "-" + data.toString();
        Conect connection = new Conect();

        String loginResponse = null;
        try
        {
            loginResponse = connection.execute(postVar).get().toString();
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        Boolean response = false;
        try
        {
            JSONObject obj = new JSONObject(loginResponse.substring(loginResponse.indexOf("{"), loginResponse.lastIndexOf("}") + 1));
            response = obj.getBoolean("result");
            accountType = obj.getString("accountType");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return response;
    }
}
