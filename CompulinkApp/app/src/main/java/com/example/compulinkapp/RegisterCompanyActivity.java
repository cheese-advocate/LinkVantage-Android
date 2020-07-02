package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class RegisterCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        //Toast message to be shown when this activity starts
        Toast.makeText(getApplicationContext(), "Only one contact and address can be created during registration.\nAdditional Contacts can be added after registration", Toast.LENGTH_LONG).show();

        final Button clientOptBut = (Button) findViewById(R.id.clientOptionBut);

        clientOptBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                clientOptBut.startAnimation(anim);
                Intent switchOpt = new Intent(v.getContext(), RegisterAccount.class);
                startActivity(switchOpt);
            }
        });

        final Button regBut = findViewById(R.id.registerBut);

        regBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                regBut.startAnimation(anim);
            }
        });
    }
}