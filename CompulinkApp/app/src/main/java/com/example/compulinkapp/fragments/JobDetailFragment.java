package com.example.compulinkapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.compulinkapp.R;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.classes.Conect;
import com.example.compulinkapp.classes.ContentGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class JobDetailFragment extends Fragment{
    /**
     * These are the methods and functions that execute when the view is being created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("Job Details");
        return inflater.inflate(R.layout.fragment_job_details, container, false);
    }
    /**
     * These methods get executed after the view has been created
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Get the text passed to this fragment
        String text = getArguments().getString("jobID");
        TextView test = view.findViewById(R.id.testText);
        //Set the text for testing purposes
        test.setText(text);
    }
}
