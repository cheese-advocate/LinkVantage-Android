package com.example.compulinkapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
                //stops refreshing when this method has completed its code
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /*THE CODE BELOW IS FOR TESTING ONLY*/


        Button addClient = view.findViewById(R.id.test_add_btn);
        final LinearLayout parent = (LinearLayout) view.findViewById(R.id.client_container_main);

        addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentGenerator cg = new ContentGenerator(getContext(), view);
                //Call this method to create new client as many times as needed
                //The strings needed to display should just be passed into the method
                cg.createClientCard(parent, "Edrich Barnard", "Cerebruteq", "18 Williams Street Paarl");
            }
        });

        Button addPotentialClient = view.findViewById(R.id.test_add_btn2);
        final LinearLayout parent2 = (LinearLayout) view.findViewById(R.id.potential_clients_container);

        addPotentialClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentGenerator cg = new ContentGenerator(getContext(), view);
                //Call this method to create new client as many times as needed
                //The strings needed to display should just be passed into the method
                cg.createClientCard(parent2, "Edrich Barnard", "Shows interest in PC upgrade");
            }
        });
    }
}
