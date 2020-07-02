package com.example.compulinkapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Dash) getActivity()).setActionBarTitle("About");
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button tcBtn = view.findViewById(R.id.t_c_btn);
        final Button privacyPolicyBtn = view.findViewById(R.id.privacyPolicyBtn);
        final Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.blink);

        privacyPolicyBtn.startAnimation(anim);

        tcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tcBtn.startAnimation(anim);
            }
        });

        privacyPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyPolicyBtn.startAnimation(anim);
            }
        });
    }
}
