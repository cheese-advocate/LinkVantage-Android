package com.example.compulinkapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.compulinkapp.classes.ContentGenerator;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.R;

public class JobFragment extends Fragment{

    /**
     * These are the methods and functions that execute when the view is being created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("Jobs");
        return inflater.inflate(R.layout.fragment_jobs, container, false);
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

        //Button used to test content addition functionality
        Button testBtn = view.findViewById(R.id.testBtn);
        final ContentGenerator cg = new ContentGenerator(getContext(), view);
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.jobs_active);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cg.createJobCard(linearLayout , "JOB Test");
            }
        });

        //Test card view used to test functionality of new code
        final CardView testV = view.findViewById(R.id.TEST);

        testV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout parent = (LinearLayout) testV.getParent();
                cg.changeParent(parent, testV);
            }
        });

        final SwipeRefreshLayout swipe_refresh = view.findViewById(R.id.jobs_fragment_refreshSwipe);
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
