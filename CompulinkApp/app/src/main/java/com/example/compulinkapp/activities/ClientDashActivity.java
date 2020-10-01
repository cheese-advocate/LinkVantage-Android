package com.example.compulinkapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.compulinkapp.R;
import com.example.compulinkapp.fragments.ProfileFragment;
import com.example.compulinkapp.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientDashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dash);

        BottomNavigationView bottomNav = findViewById(R.id.client_bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.client_fragment_container);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    public void setActionBarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

    /**
     * Creates the option menu seen in the dashboard
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    /**
     * The method below is called when an item on the options menu is selected
     * and performs the necessary action based on the selected option
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.account_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack("Account").commit();
                break;
            case R.id.settings_opt:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).addToBackStack("Settings").commit();
                break;
            case R.id.logout_opt:
                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logout);
                finish();//Kills this activity to prevent back click login
                break;
        }

        return true;
    }
}