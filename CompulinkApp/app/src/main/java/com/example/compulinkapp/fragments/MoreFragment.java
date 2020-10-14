package com.example.compulinkapp.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.compulinkapp.activities.ClientDashActivity;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.R;
import com.example.compulinkapp.classes.GoogleMapsHelper;

public class MoreFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Activity parent = getActivity();
        if(parent.equals(DashActivity.class))
        {
            ((DashActivity) getActivity()).setActionBarTitle("More");
        }
        else if(parent.equals(ClientDashActivity.class))
        {
            ((ClientDashActivity) getActivity()).setActionBarTitle("More");
        }
        return inflater.inflate(R.layout.fragment_more, container, false);
        
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        /**
         * On click of facebook link the link will open in the facebook app and take the user to
         * Compulink Technologies Home screen in facebook where all important details are shown
         * If facebook is not on the device or not set up on the device the user will be redirected
         * to compulink technologies' facebook page on their default browser
         */


        /**
         * Opens google maps app on the location of Compulink technologies
         */

    }
}
