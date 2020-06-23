package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        Button clientOptBut = (Button) findViewById(R.id.clientOptionBut);

        clientOptBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchOpt = new Intent(v.getContext(), RegisterAccount.class);
                startActivity(switchOpt);
            }
        });
    }
}