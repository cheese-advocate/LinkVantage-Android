package com.example.compulinkapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.compulinkapp.classes.Conect;
import com.example.compulinkapp.classes.ContentGenerator;
import com.example.compulinkapp.classes.GoogleMapsHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class JobDetailFragment extends Fragment{

    String postVar = null;
    String id = null;
    /**
     * These are the methods and functions that execute when the view is being created
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((DashActivity) getActivity()).setActionBarTitle("Job Details");
        return inflater.inflate(R.layout.fragment_job_details, container, false);
    }
    /**
     * These methods get executed after the view has been created
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ContentGenerator cg = new ContentGenerator(getContext(), view);
        final GoogleMapsHelper helper = new GoogleMapsHelper(getContext());
        //Get the text passed to this fragment
        id = getArguments().getString("jobID");
        TextView priority = view.findViewById(R.id.priorityText);
        TextView deadline = view.findViewById(R.id.dueDateText);
        TextView status = view.findViewById(R.id.statusText);
        TextView category = view.findViewById(R.id.categoryText);
        TextView client = view.findViewById(R.id.clientText);
        final TextView location = view.findViewById(R.id.locationText);
        TextView description = view.findViewById(R.id.descText);
        try
        {
            JSONObject obj = getJobDetails(id);
            //Set text color of priority
            if(obj.getString("priority").equals("Urgent"))
            {
                priority.setTextColor(Color.parseColor("#FF0000"));
            }
            else if(obj.getString("priority").equals("High"))
            {
                priority.setTextColor(Color.parseColor("#FFA500"));
            }
            else if(obj.getString("priority").equals("Low"))
            {
                priority.setTextColor(Color.parseColor("#03AAFB"));
            }
            //Set text color of status
            if(obj.getString("jobStatus").equalsIgnoreCase("In Progress"))
            {
                status.setTextColor(Color.parseColor("#32CD32"));
            }
            else if(obj.getString("jobStatus").equalsIgnoreCase("Not Started"))
            {
                status.setTextColor(Color.parseColor("#FF0000"));
            }
            else if(obj.getString("jobStatus").equalsIgnoreCase("Waiting on client"))
            {
                status.setTextColor(Color.parseColor("#FFA500"));
            }
            else
            {
                status.setTextColor(Color.parseColor("#03AAFB"));
            }

            location.setTextColor(Color.parseColor("#03AAFB"));

            if(obj.getString("jobDescription").equalsIgnoreCase("null"))
            {
                description.setText("No Description");
            }
            else description.setText(obj.getString("jobDescription"));
            //Set the actual text in the text views
            priority.setText(obj.getString("priority"));
            deadline.setText(obj.getString("deadline"));
            status.setText(obj.getString("jobStatus"));
            category.setText(obj.getString("typeName"));
            client.setText(obj.getString("clientName"));
            location.setText(obj.getString("location"));


            //On click of the location the option to navigate or view will again be there
            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String loc = location.getText().toString().trim();

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch(which)
                            {
                                case DialogInterface.BUTTON_POSITIVE:
                                    helper.navigateToLocation(loc);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    helper.openLocation(loc);
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.dialog_theme);
                    builder.setMessage("What would you like to do?").setPositiveButton("Navigate", dialogClickListener).setNegativeButton("View", dialogClickListener).show();
                }
            });

            getAllTasks(id, cg);
        }
        catch (ExecutionException | InterruptedException | JSONException e)
        {
            e.printStackTrace();
        }

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.jobsDetails_fragment_refreshSwipe);
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
                ft.detach(JobDetailFragment.this).attach(JobDetailFragment.this).commit();
                swipeRefreshLayout.setRefreshing(false); //sets refreshing to stop if this method has completed all the code
            }
        });

        Button add = view.findViewById(R.id.addTaskBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText task = view.findViewById(R.id.task_text);
                String text = task.getText().toString().trim();
                Conect connection = new Conect();
                postVar = "ADD_NEW_TASK";
                JSONObject data = new JSONObject();

                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                try
                {
                    data.put("date", date);
                    data.put("task", text);
                    data.put("jobID", id);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                postVar = postVar + "-" + "data"+ "=" + data.toString();

                try
                {
                    String response = connection.execute(postVar).get().toString().trim();

                    if(response.equalsIgnoreCase("True"))
                    {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(JobDetailFragment.this).attach(JobDetailFragment.this).commit();
                        Toast.makeText(getContext(), "Task Added", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();

                    connection.cancel(true);
                }
                catch (ExecutionException | InterruptedException e)
                {
                    e.printStackTrace();
                }
                task.setText("");//Clears the input after addition of new task
            }
        });
    }

    public JSONObject getJobDetails(String jobID) throws ExecutionException, InterruptedException, JSONException {
        Conect connection = new Conect();
        postVar = "GET_JOB_DETAILS";
        JSONObject data = new JSONObject();
        try
        {
            data.put("id", jobID);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        postVar = postVar + "-" + data.toString();
        String response = (String) connection.execute(postVar).get();

        JSONArray detailsArray = new JSONArray(response);
        JSONObject obj = detailsArray.getJSONObject(0);//There will always only be one object in the array
        connection.cancel(true);
        return obj;
    }

    public void getAllTasks(String jobID, ContentGenerator cg) throws ExecutionException, InterruptedException, JSONException {
        Conect connection = new Conect();
        postVar = "GET_ALL_TASKS";
        JSONObject data = new JSONObject();

        try
        {
            data.put("id", jobID);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        postVar = postVar + "-" + data.toString();

        String response = (String) connection.execute(postVar).get();
        String id, task, taskEnd;

        JSONArray array = new JSONArray(response);
        JSONObject obj;
        LinearLayout parent = getView().findViewById(R.id.task_container);
        for(int i = 0; i < array.length(); i++)
        {
            obj = array.getJSONObject(i);

            id = obj.getString("taskID");
            task = obj.getString("taskDescription");
            taskEnd = obj.getString("taskEnd");

            cg.createTaskCard(parent, task, id, taskEnd);
        }

        connection.cancel(true);
    }
}
