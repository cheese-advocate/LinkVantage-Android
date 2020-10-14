package com.example.compulinkapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compulinkapp.R;
import com.example.compulinkapp.classes.Conect;
import com.example.compulinkapp.fragments.ProfileFragment;
import com.example.compulinkapp.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientDashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dash);

        SharedPreferences pref = getSharedPreferences("user_details", MODE_PRIVATE);
        Toast.makeText(this, "Welcome " + pref.getString("username", null), Toast.LENGTH_SHORT).show();

        BottomNavigationView bottomNav = findViewById(R.id.client_bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.client_fragment_container);
        NavigationUI.setupWithNavController(bottomNav, navController);


        final TextView details =   (TextView) findViewById(R.id.detailsPanel);
        final TextView textView1 =   (TextView) findViewById(R.id.textView1);
        final TextView textView2 =   (TextView) findViewById(R.id.textView2);
        final TextView textView3 =   (TextView) findViewById(R.id.textView3);
        final TextView textView4 =   (TextView) findViewById(R.id.textView4);
        final TextView textView5 =   (TextView) findViewById(R.id.textView5);
        final TextView textView6 =   (TextView) findViewById(R.id.textView6);
        final TextView textView7 =   (TextView) findViewById(R.id.textView7);
        final TextView textView8 =   (TextView) findViewById(R.id.textView8);
        final TextView textView9 =   (TextView) findViewById(R.id.textView9);
        final TextView textView10 =   (TextView) findViewById(R.id.textView10);
        final TextView textView11 =   (TextView) findViewById(R.id.textView11);
        final TextView textView12 =   (TextView) findViewById(R.id.textView12);
        final TextView textView13 =   (TextView) findViewById(R.id.textView13);
        final TextView textView14 =   (TextView) findViewById(R.id.textView14);
        //Constructing Post Variable - Start
        String postVar = "MAIN_CLIENT_PORTAL";
        //Constructing Post Variable - End
        //Post Process - Start
        Conect conect = new Conect();
        conect.execute(postVar);
        //Post Process - End

        details.setText("Details" + "\n \n" +
                        "Reference number:" + "\n" + "#239067" + "\n \n" +
                "Description:" + "\n" +"Supply new pc's and redo networking" + "\n \n" +
                   "Location:"  + "\n" + "Kaapzicht Building, 9 Rogers Street, Tyger Valley, 7530" + "\n \n" +
                "Status:" +"\n" + "In progress" + "\n \n" +
                "Due date:" +"\n" + "22 July 2020" + "\n \n" +
                "Technician:" + "\n \n" +"Jan Nienaber"
                        );
        //DROPDOWN BOX - START
        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[]{"Job 1408"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        //DROPDOWN BOX - END
        NestedScrollView updateScroll = (NestedScrollView) findViewById(R.id.UpdateScroll);
        updateScroll.setBackgroundColor(0xFFFFFFFF);
        textView1.setText("Active Job Updates:");
        textView1.setHeight(75);
        textView1.setTextSize(25);

        textView2.setHeight(10);
        textView2.setText("");

        textView3.setHeight(75);
        textView3.setText("July 18 - 10:35\n" +
                "Completed milestone 'Stock ordered'");

        textView4.setHeight(50);
        textView4.setText("This is an example of client facing feedback");

        textView5.setHeight(10);
        textView5.setText("");

        textView6.setHeight(75);
        textView6.setText("July 18 - 10:35\n" +
                "Completed milestone 'Stock ordered'");

        textView7.setHeight(50);
        textView7.setText("This is an example of client facing feedback");

        textView8.setHeight(10);
        textView8.setText("");

        textView9.setHeight(75);
        textView9.setText("July 18 - 10:35\n" +
                "Completed milestone 'Stock ordered'");

        textView10.setHeight(50);
        textView10.setText("This is an example of client facing feedback");

        textView11.setHeight(10);
        textView11.setText("");

        textView12.setHeight(75);
        textView12.setText("July 18 - 10:35\n" +
                "Completed milestone 'Stock ordered'");

        textView13.setHeight(50);
        textView13.setText("This is an example of client facing feedback");

        textView14.setHeight(10);
        textView14.setText("");




    }//onCreate - END



//Validity True - End


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