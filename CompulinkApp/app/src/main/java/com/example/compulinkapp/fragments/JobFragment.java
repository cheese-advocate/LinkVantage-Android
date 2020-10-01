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
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.compulinkapp.classes.Conect;
import com.example.compulinkapp.classes.ContentGenerator;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class JobFragment extends Fragment{

    String postVar = null;
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
        final  ContentGenerator cg = new ContentGenerator(getContext(), view);
        final LinearLayout parent = view.findViewById(R.id.job_container);

        try
        {
            getJobs(parent, cg);
        }
        catch (ExecutionException | InterruptedException | JSONException e)
        {
            e.printStackTrace();
        }

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
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(JobFragment.this).attach(JobFragment.this).commit();
                //Set refresh to false when code is complete
                //This ensures the icon goes away when done refreshing
                swipe_refresh.setRefreshing(false);
            }
        });
    }

    public void getJobs(LinearLayout parent, ContentGenerator cg) throws ExecutionException, InterruptedException, JSONException
    {
        Conect connection = new Conect();
        postVar = "GET_JOBS";
        String response = (String) connection.execute(postVar).get();
        JSONArray data = new JSONArray(response);
        JSONObject obj;

        String id, desc, clientName, priority;

        for (int i = 0; i < data.length(); i++)
        {
            obj = data.getJSONObject(i);

            id = obj.getString("jobID");
            if(obj.isNull("jobDescription"))
            {
                desc = "No Description";
            }
            else desc = obj.getString("jobDescription");

            clientName = obj.getString("fullName");
            priority = obj.getString("priority");

            cg.createJobCard(parent, id, desc, clientName, priority);
        }

        connection.cancel(true);
    }
}
