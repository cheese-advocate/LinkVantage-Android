package com.example.compulinkapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.compulinkapp.R;

public class RegisterCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        final Button clientOptBut = (Button) findViewById(R.id.clientOptionBut);

        clientOptBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                clientOptBut.startAnimation(anim);
                Intent switchOpt = new Intent(v.getContext(), RegisterAccountActivity.class);
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