package com.example.compulinkapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.compulinkapp.R;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.activities.MainActivity;
import com.example.compulinkapp.classes.Conect;
import com.example.compulinkapp.classes.ContentGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

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
                 * The fragment gets detached and attached again, thus all the fragment code
                 * executes again and in doing so UI gets updated
                 */
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(SalesFragment.this).attach(SalesFragment.this).commit();
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
                final Conect connection = new Conect();
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

                //Get the add button on the popup
                Button add = popupDialog.findViewById(R.id.addPotentialClient);
                //Code to run when add button clicked
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String success = "FALSE";
                        EditText fName = popupDialog.findViewById(R.id.nameInp);
                        EditText lName = popupDialog.findViewById(R.id.lastNameInp);
                        EditText jobInterest = popupDialog.findViewById(R.id.jobInp);
                        EditText location = popupDialog.findViewById(R.id.locationInp);
                        EditText cell = popupDialog.findViewById(R.id.cellInp);
                        EditText email = popupDialog.findViewById(R.id.emailInpPopup);

                        postVar = "REGISTER_POTENTIAL_CLIENT";
                        JSONObject data = new JSONObject();
                        try
                        {
                            data.put("firstName", fName.getText().toString());
                            data.put("lastName", lName.getText().toString());
                            data.put("jobInterest", jobInterest.getText().toString());
                            data.put("location", location.getText().toString());
                            data.put("cell", cell.getText().toString());
                            data.put("email", email.getText().toString());
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        postVar =  postVar + "-" + data.toString();
                        try
                        {
                            success = connection.execute(postVar).get().toString().trim();
                        }
                        catch (ExecutionException e)
                        {
                            e.printStackTrace();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                        if(success.equals("TRUE"))
                        {
                            Toast.makeText(getContext(), "New sales lead created", Toast.LENGTH_SHORT).show();
                            popupDialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "An error occurred!", Toast.LENGTH_LONG).show();
                        }
                        connection.cancel(true);
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
            /**
             * Contacts the server and generates data based on what is received
             */
            getClients(clientParent, cg);
            getStats(statParent, cg);
            getFeedback(feedbackParent, cg);
            getPotentialClients(potentialClientParent, cg);
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

    /**
     * Queries the web server to retrieve the existing clients
     *
     * @param parent the parent layout to which clients should be added
     * @param cg content generator to generate content
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JSONException
     */
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

    /**
     * Queries the web server to retrieve all potential clients
     *
     * @param parent the parent layout to which content should be added
     * @param cg content generator to generate new content
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JSONException
     */
    public void getPotentialClients(LinearLayout parent, ContentGenerator cg) throws ExecutionException, InterruptedException, JSONException
    {
        Conect connection = new Conect();
        postVar = "GET_POTENTIAL_CLIENTS";

        String response = (String) connection.execute(postVar).get();
        JSONArray data = new JSONArray(response);
        JSONObject obj;

        String fullName;
        String interest;

        for (int i = 0; i < data.length(); i++)
        {
            obj = data.getJSONObject(i);

            fullName = obj.getString("FullName");
            interest = obj.getString("interest");

            cg.createClientCard(parent, fullName, interest);
        }
        connection.cancel(true);//Stops thread when code completes
    }

    /**
     * Queries the web server to get the stats from the database
     *
     * @param parent parent layout to which content should be added
     * @param cg content generator to generate new content
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JSONException
     */
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

    /**
     * Queries the web server to get all the feedback from the database
     *
     * @param parent the parent layout to which new content should be added
     * @param cg content generator to generate the content
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JSONException
     */
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
