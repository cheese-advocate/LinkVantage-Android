package com.example.compulinkapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment{

    /**
     * Creates the view when this fragment is called or displayed
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Dash) getActivity()).setActionBarTitle("Profile");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * When the view has been created the following code will be able to execute
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View resetPw = view.findViewById(R.id.resetPw_opt);

        /**
         * Navigates to reset/new password when clicked
         */
        resetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPwPage = new Intent(v.getContext(), NewPassword.class);
                startActivity(resetPwPage);
            }
        });

        View deleteAccount = view.findViewById(R.id.deleteAccount);

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent deleteAccount = new Intent(getContext(), LoginActivity.class);
                                startActivity(deleteAccount);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_theme);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();
            }
        });
    }
}
