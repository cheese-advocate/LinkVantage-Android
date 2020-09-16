package com.example.compulinkapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.compulinkapp.R;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.classes.Conect;
import com.example.compulinkapp.classes.ContentGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * TODO
 * Retrieve necessary data from SQL for the sales lead management screen
 * Format the data and display it in the necessary locations
 * Enable technicians to add new sales leads to the database from sales lead management screen
 */

public class SalesFragment extends Fragment{
    //Post variable to be used in server communication
    String postVar = null;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("Sales Management");
        return inflater.inflate(R.layout.fragment_sales_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Dialog popupDialog = new Dialog(getContext());//Dialog used to create and display popup dialog
        final ContentGenerator cg = new ContentGenerator(getContext(), view);
        /**
         * Initialisation of all the parent layouts to which the content can be added
         * */
        final LinearLayout clientParent = view.findViewById(R.id.client_container_main);//Existing clients are added to this layout
        final LinearLayout potentialClientParent = view.findViewById(R.id.potential_clients_container);//Potential clients are added to this layout
        final LinearLayout statParent = view.findViewById(R.id.stats_container);//Stats are added to this layout
        final LinearLayout feedbackParent = view.findViewById(R.id.feedback_container);//Feedback cards are added here
        /**
         * Setting the swipe refresh layout
         * The code below sets the look and feel of the swipe refresh layout
         */
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.main_swipe_refresh);
        //Look and feel of loading icon on refresh
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        /**
         * When the user swipes down the screen will be refreshed and the method below will be called
         * The code below will be used to see if any changes are detected in the database and
         * the screen will be updated accordingly
         */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * On refresh of the screen the following code will execute and query the database
                 * again. The screen will thus update if changes occur.
                 */
                try
                {
                    getClients(clientParent, cg);
                    getStats(statParent, cg);
                    getFeedback(feedbackParent, cg);
                }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false); //sets refreshing to stop if this method has completed all the code
            }
        });

        /**
         * Creates a popup window where the technician can add a new sales lead
         *
         * THIS CODE IS NOT FOR TESTING PURPOSES ONLY THE OTHER BUTTONS ARE
         */
        Button popup = view.findViewById(R.id.popupTest);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set the layout to be displayed in the popup
                popupDialog.setContentView(R.layout.sales_lead_popup);
                //Initialise the close button/image
                ImageView close = popupDialog.findViewById(R.id.close);
                //OnClickListener for close button/image
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Close the popup
                        popupDialog.dismiss();
                    }
                });
                //Prevent the popup from having a weird white border on the sides
                popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Show the popup
                popupDialog.show();
            }
        });

        try
        {
            getClients(clientParent, cg);
            getStats(statParent, cg);
            getFeedback(feedbackParent, cg);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void getClients(LinearLayout parent, ContentGenerator cg) throws ExecutionException, InterruptedException, JSONException
    {
        Conect connection = new Conect();
        postVar = "GET_CLIENTS";
        //Get the JSON response from PHP
        String response = (String) connection.execute(postVar).get();
        //Put the response in a JSON Array
        JSONArray data = new JSONArray(response);
        JSONObject obj;

        String fullName;
        String location;
        String company;

        for (int i = 0; i < data.length(); i++)
        {
            obj = data.getJSONObject(i);
            fullName = obj.getString("FullName");
            location = obj.getString("location");

            if(obj.isNull("companyName"))
            {
                company = "N/A";
            }
            else company = obj.getString("companyName");

            cg.createClientCard(parent, fullName, company, location);
            Log.d("Number of times run", "Number--" + i);
        }
        connection.cancel(true);//Stops the thread when code completes
    }

    public void getPotentialClients()
    {
        //Still awaiting data in the database
    }

    public void getStats(LinearLayout parent, ContentGenerator cg) throws ExecutionException, InterruptedException, JSONException
    {
        Conect connection = new Conect();
        postVar = "GET_STATS";
        String response = (String) connection.execute(postVar).get();
        JSONObject data  = new JSONObject(response);

        cg.createStatCard(parent, "Registered Companies", data.getString("companies"));
        cg.createStatCard(parent, "Total Jobs", data.getString("jobs"));
        cg.createStatCard(parent, "Total Clients", data.getString("clients"));

        connection.cancel(true);//Stops the thread when code completes
    }

    public void getFeedback(LinearLayout parent, ContentGenerator cg) throws ExecutionException, InterruptedException, JSONException
    {
        Conect connection = new Conect();
        postVar = "GET_FEEDBACK";
        String response = (String) connection.execute(postVar).get();
        JSONArray data = new JSONArray(response);
        JSONObject obj;

        String fullName;
        String desc;
        String feedback;

        for (int i = 0; i < data.length(); i++)
        {
            obj = data.getJSONObject(i);
            fullName = obj.getString("clientName");

            if(obj.isNull("jobDescription"))
            {
                desc = "No Description";
            }
            else desc = obj.getString("jobDescription");

            feedback = obj.getString("content");

            cg.createFeedbackCard(parent, fullName, desc, feedback);
        }

        connection.cancel(true);//Stops the thread when code completes
    }
}
