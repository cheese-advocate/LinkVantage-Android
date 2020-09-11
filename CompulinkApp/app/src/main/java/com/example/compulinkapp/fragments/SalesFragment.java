package com.example.compulinkapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.compulinkapp.R;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.classes.ContentGenerator;

public class SalesFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("Sales Management");
        return inflater.inflate(R.layout.fragment_sales_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Dialog popupDialog = new Dialog(getContext());
        /**
         * Initialisation of all the parent layouts to which the content can be added
         */
        final LinearLayout clientParent = view.findViewById(R.id.client_container_main);
        final LinearLayout potentialClientParent = view.findViewById(R.id.potential_clients_container);
        final LinearLayout statParent = view.findViewById(R.id.stats_container);
        final LinearLayout feedbackParent = view.findViewById(R.id.feedback_container);

        /**
         * Setting the swipe refresh layout
         * The code below sets the look and feel of the swipe refresh layout
         */
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main_swipe_refresh);
        //Look and feel of loading icon on refresh
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        /**
         * When the user swipes down the screen will be refreshed and the method below will be called
         * The code below will be used to see if any changes are detected in the database and
         * the screen will be updated accordingly
         */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //On refresh code to come here
                swipeRefreshLayout.setRefreshing(false); //sets refreshing to stop if this method has completed all the code
            }
        });


        /*
        **********************************
        THE CODE BELOW IS FOR TESTING ONLY
        **********************************
        */

        Button addClient = view.findViewById(R.id.test_add_btn);

        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentGenerator cg = new ContentGenerator(getContext(), view);
                //Call this method to create new client as many times as needed
                //The strings needed to display should just be passed into the method
                cg.createClientCard(clientParent, "Edrich Barnard", "Cerebruteq", "18 Williams Street Paarl");
            }
        });

        Button addPotentialClient = view.findViewById(R.id.test_add_btn2);

        addPotentialClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentGenerator cg = new ContentGenerator(getContext(), view);
                //Call this method to create new client as many times as needed
                //The strings needed to display should just be passed into the method
                cg.createClientCard(potentialClientParent, "Edrich Barnard", "Shows interest in PC upgrade");
            }
        });

        Button addStat = view.findViewById(R.id.test_add_btn3);

        addStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentGenerator cg = new ContentGenerator(getContext(), view);
                //Call this method to create new client as many times as needed
                //The strings needed to display should just be passed into the method
                cg.createStatCard(statParent, "Total Sales", "45");
            }
        });

        Button addFeedback = view.findViewById(R.id.test_feedbackBtn);

        addFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentGenerator cg = new ContentGenerator(getContext(), view);
                //Call this method to create new client as many times as needed
                //The strings needed to display should just be passed into the method
                cg.createFeedbackCard(feedbackParent, "Edrich Barnard", "PC Repair", "Overall Good job. Great service");
            }
        });

        /**
         * Creates a popup window where the technician can add a new sales lead
         */
        Button popup = view.findViewById(R.id.popupTest);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set the layout to be displayed in the popup
                popupDialog.setContentView(R.layout.sales_lead_popup);
                //Initialise the close button/image
                ImageView close = popupDialog.findViewById(R.id.close);
                //OnClickListener for close button/image
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Close the popup
                        popupDialog.dismiss();
                    }
                });
                //Prevent the popup from having a weird white border on the sides
                popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Show the popup
                popupDialog.show();
            }
        });
    }
}
