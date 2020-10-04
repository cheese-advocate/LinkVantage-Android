package com.example.compulinkapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.compulinkapp.R;
import com.example.compulinkapp.fragments.FacebookFeedFragment;
import com.example.compulinkapp.fragments.ProfileFragment;
import com.example.compulinkapp.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The dash class sets up the whole dashboard with all menus and container for fragments
 * All methods in this class are used to set up the layout for the dashboard
 * Instead of having a new activity for every screen needed several fragments are used
 * These fragments are placed into a container on the dashboard when the specific
 * fragment needs to be displayed
 *
 * The fragments are fully modular thus they can be placed in many activities where
 * there is a container for them to be placed
 */
public class DashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        Toast.makeText(this, "Welcome " + pref.getString("username", null), Toast.LENGTH_SHORT).show();
        /**
         * Nav controller setup to control the navigation of the dashboard bottom navigation
         */
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
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
                SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
                pref.edit().clear().apply();//Clear the shared preferences
                startActivity(logout);
                finish();//Kills this activity to prevent back click login
                break;
        }

        return true;
    }
}