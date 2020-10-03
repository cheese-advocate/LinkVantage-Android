package com.example.compulinkapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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

        Button addJobPopup = view.findViewById(R.id.add_job_popup);
        final Dialog jobPopup = new Dialog(getContext());
        addJobPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobPopup.setContentView(R.layout.add_job_popup);

                //Initialise the close button/image
                ImageView close = jobPopup.findViewById(R.id.close);
                //OnClickListener for close button/image
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Close the popup
                        jobPopup.dismiss();
                    }
                });

                final Spinner jobTypeSpinner = jobPopup.findViewById(R.id.spin_jobType);
                final Spinner technicianSpinner = jobPopup.findViewById(R.id.spin_technician);
                final Spinner clientsSpinner = jobPopup.findViewById(R.id.spin_client);
                final Spinner siteSpinner = jobPopup.findViewById(R.id.spin_site);

                try
                {
                    //Gets all information required from the database and stores it in array lists of hash maps
                    final ArrayList<HashMap<String, String>> clients = buildList("GET_ALL_CONTACTS");
                    final ArrayList<HashMap<String, String>> technicians = buildList("GET_ALL_TECHNICIANS");
                    final ArrayList<HashMap<String, String>> type = buildList("GET_ALL_JOB_TYPES");
                    final ArrayList<HashMap<String, String>> site = buildList("GET_ALL_SITES");
                    //Build Array lists to be used in the spinners
                    ArrayList<String> clientList = toList(clients);
                    ArrayList<String> technicianList = toList(technicians);
                    ArrayList<String> typeList = toList(type);
                    ArrayList<String> siteList = toList(site);
                    //Create adapters for each spinner
                    ArrayAdapter<String> jobTypeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeList);
                    ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, clientList);
                    ArrayAdapter<String> siteListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, siteList);
                    ArrayAdapter<String> technicianListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, technicianList);
                    //Set the spinners adapters
                    jobTypeSpinner.setAdapter(jobTypeAdapter);
                    technicianSpinner.setAdapter(technicianListAdapter);
                    clientsSpinner.setAdapter(clientAdapter);
                    siteSpinner.setAdapter(siteListAdapter);

                    final EditText editTextDeadline = jobPopup.findViewById(R.id.txt_deadline);
                    final EditText editTextDesc = jobPopup.findViewById(R.id.txt_jobDesc);
                    final Spinner spinnerPriority = jobPopup.findViewById(R.id.spin_priority);
                    final Spinner spinnerJobStatus = jobPopup.findViewById(R.id.spin_jobStatus);

                    Button addJob = jobPopup.findViewById(R.id.btn_createJob);
                    addJob.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Conect connection = new Conect();
                            postVar = "ADD_JOB";
                            //Gets the current date - The date the job was added
                            String startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            String description = editTextDesc.getText().toString().trim();
                            String deadline = editTextDeadline.getText().toString().trim();
                            String priority = spinnerPriority.getSelectedItem().toString();
                            String jobStatus = spinnerJobStatus.getSelectedItem().toString();
                            String clientID = getKeyFromValue(clients, clientsSpinner.getSelectedItem().toString());
                            String tecID = getKeyFromValue(technicians, technicianSpinner.getSelectedItem().toString());
                            String typeID = getKeyFromValue(type, jobTypeSpinner.getSelectedItem().toString());
                            String siteID = getKeyFromValue(site, siteSpinner.getSelectedItem().toString());

                            JSONObject data = new JSONObject();

                            try
                            {
                                data.put("startDate", startDate);
                                data.put("deadline", deadline);
                                data.put("description", description);
                                data.put("priority", priority);
                                data.put("jobStatus", jobStatus);
                                data.put("clientID", clientID);
                                data.put("tecID", tecID);
                                data.put("typeID", typeID);
                                data.put("siteID", siteID);
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            postVar = postVar + "-" + "data" + "=" + data.toString();

                            try
                            {
                                String response = connection.execute(postVar).get().toString().trim();

                                if(response.equalsIgnoreCase("true"))
                                {
                                    Toast.makeText(getContext(), "New Job Created", Toast.LENGTH_SHORT).show();
                                    jobPopup.dismiss();
                                }
                                else if(response.equalsIgnoreCase("false"))
                                {
                                    Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (ExecutionException | InterruptedException e)
                            {
                                e.printStackTrace();
                            }

                            connection.cancel(true);//Closes the connection
                        }
                    });
                }
                catch (JSONException | ExecutionException | InterruptedException e)
                {
                    e.printStackTrace();
                }

                //Prevent the popup from having a weird white border on the sides
                jobPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Show the popup
                jobPopup.show();
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

    /**
     * Builds an array list of hash maps
     * The information stored in the hash map and array list depends on the postVar
     * @param serverReq the request to be made to the server
     * @return returns an array list containing hash maps
     */
    public ArrayList<HashMap<String, String>> buildList(String serverReq) throws JSONException, ExecutionException, InterruptedException
    {
        //List containing multiple hash maps of clients and their id
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        //Hash map for single client
        HashMap<String, String> listMap;//not set to new as a new one will be created for each client

        Conect connection = new Conect();
        postVar = serverReq;

        String response = connection.execute(postVar).get().toString().trim();//JSON response from server

        JSONArray data = new JSONArray(response);
        JSONObject obj;

        for (int i = 0; i < data.length(); i++)
        {
            obj = data.getJSONObject(i);

            listMap = new HashMap<String, String>();//Creates the new hash map for each entry
            listMap.put(obj.getString("id"), obj.getString("val"));//Adds id and value strings to hash map

            list.add(listMap);//Adds the hash map to the list
        }

        connection.cancel(true);//Closes the connection
        return list;
    }

    /**
     * Converts the hash map array list to an array list
     *
     * @param hashMapArrayList the list to be changed
     * @return returns an Array list
     */
    public ArrayList<String> toList(ArrayList<HashMap<String, String>> hashMapArrayList)
    {
        //List to be populated
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < hashMapArrayList.size(); i++)//Iterates through the array list and accesses each hash map
        {
            //Gets the hash map at the current index
            HashMap<String, String> map = hashMapArrayList.get(i);
            //Loops through the map and adds all the values to the list
            for (Map.Entry<String, String> entry : map.entrySet())//Iterates through the map to get the value
            {
                list.add(i, entry.getValue());
            }
        }
        //Returns the list
        return list;
    }

    /**
     * Returns the key of the value given
     *
     * @param hashMapArrayList the list containing the key to be searched for
     * @param value the value who's key needs to be searched for
     * @return returns the string key
     */
    public String getKeyFromValue(ArrayList<HashMap<String, String>> hashMapArrayList, String value)
    {
        for (int i = 0; i < hashMapArrayList.size(); i++)
        {
            //Gets the hash map at the current index
            HashMap<String, String> map = hashMapArrayList.get(i);
            //Searches through the current pointed at hash map
            for (String o : map.keySet())
            {
                if (map.get(o).equals(value))//Checks if the object equals the specified value
                {
                    //Returns the object/key if found
                    return o;
                }
            }
        }
        //Returns null if not found
        return null;
    }
}
