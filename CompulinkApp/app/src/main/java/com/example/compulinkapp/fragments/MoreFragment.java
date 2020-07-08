package com.example.compulinkapp.fragments;

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

import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.R;

public class MoreFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("More");
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView profileManagement = view.findViewById(R.id.profile_management_card);
        CardView jobManagement = view.findViewById(R.id.job_management_card);
        CardView facebookLink = view.findViewById(R.id.facebookCard);
        //Other cards still required and onClickListeners also required for each

        profileManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).addToBackStack("Profile").commit();
            }
        });

        jobManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new JobFragment()).addToBackStack("Jobs").commit();
                //Job fragment to be replaced with job management when it has been added
            }
        });

        /**
         * On click of facebook link the link will open in the facebook app and take the user to
         * Compulink Technologies Home screen in facebook where all important details are shown
         * If facebook is not on the device or not set up on the device the user will be redirected
         * to compulink technologies' facebook page on their default browser
         */
        facebookLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/Compulinktechnologies/";
                try
                {
                    PackageManager pm = getContext().getPackageManager();
                    ApplicationInfo appInfo = pm.getApplicationInfo("com.facebook.katana", 0);
                    if(appInfo.enabled)
                    {
                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                }
                catch (ActivityNotFoundException | PackageManager.NameNotFoundException e)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });
    }
}
