package com.example.compulinkapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Dash) getActivity()).setActionBarTitle("Home");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout swipe_refresh = view.findViewById(R.id.home_fragment_refreshSwipe);
        //Set the look of the refresh icon while or when refreshing
        swipe_refresh.setColorSchemeResources(R.color.colorAccent);
        swipe_refresh.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        /**
         * Called every time the page gets refreshed
         *
         * Code to contact web server/database to be added here. This will update the jobs on each refresh
         */
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Code to execute on refresh required here
                /**
                 * Call database to see if new jobs were added
                 * Or if jobs were removed or changed
                 */
                //Set refresh to false when code is complete
                //This ensures the icon goes away when done refreshing
                swipe_refresh.setRefreshing(false);
            }
        });
    }
}
