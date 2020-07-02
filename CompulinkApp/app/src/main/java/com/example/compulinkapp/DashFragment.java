package com.example.compulinkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Dash) getActivity()).setActionBarTitle("Dashboard");
        return inflater.inflate(R.layout.fragment_dash, container, false);
    }
}
