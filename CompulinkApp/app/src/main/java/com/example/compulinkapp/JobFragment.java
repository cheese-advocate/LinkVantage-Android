package com.example.compulinkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JobFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Dash) getActivity()).setActionBarTitle("Jobs");
        return inflater.inflate(R.layout.fragment_jobs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button testBtn = view.findViewById(R.id.testBtn);
        final ContentGenerator cg = new ContentGenerator(getContext());
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.jobs_todo);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cg.createJobCard(linearLayout , "JOB Test");
            }
        });
    }
}
