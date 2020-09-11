package com.example.compulinkapp.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.compulinkapp.R;
import com.example.compulinkapp.activities.DashActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FacebookFeedFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("Facebook Feed");
        return inflater.inflate(R.layout.fragment_facebook_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Create the WebView to display the facebook feed
         */
        final WebView facebookView = view.findViewById(R.id.facebookView);
        facebookView.setWebViewClient(new WebViewClient());
        facebookView.getSettings().setLoadsImagesAutomatically(true);
        facebookView.getSettings().setJavaScriptEnabled(true);
        facebookView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        facebookView.getSettings().setBuiltInZoomControls(true);
        facebookView.getSettings().setSupportZoom(true);
        facebookView.getSettings().setLoadWithOverviewMode(true);
        facebookView.getSettings().setUseWideViewPort(true);
        facebookView.getSettings().setAllowContentAccess(true);
        facebookView.loadUrl("https://www.facebook.com/Compulinktechnologies/");

        /**
         * Ensure the facebook feed will allow the user to navigate back through the
         * feed if navigated to different pages
         */
        facebookView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && facebookView.canGoBack())
                {
                    //Goes back inside the webview and prevents the user from exiting the web view
                    facebookView.goBack();
                    return true;
                }
                return false;
            }
        });
    }
}
