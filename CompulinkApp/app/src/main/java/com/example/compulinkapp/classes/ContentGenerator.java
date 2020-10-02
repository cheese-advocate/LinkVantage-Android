package com.example.compulinkapp.classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.compulinkapp.R;
import com.example.compulinkapp.activities.DashActivity;
import com.example.compulinkapp.activities.LoginActivity;
import com.example.compulinkapp.fragments.JobDetailFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class ContentGenerator {
    Context context;
    View view;
    GoogleMapsHelper helper;
    String postVar = null;
    /**
     * Constructor to set the context and view for the class
     * @param ctx
     */
    public ContentGenerator(Context ctx, View view_sent)
    {
        context = ctx;
        view = view_sent;
        helper = new GoogleMapsHelper(ctx);
    }

    /**
     * Receives a dp value and converts that value into pixels
     * When setting content programmatically the sizes are set in pixels
     * Thus a dp value is provided and the method ensures a pixel value is added
     * to the element's size
     *
     * This ensures that generated content is the same size are manually created content
     *
     * Returns a pixel value
     * @param dp
     * @return
     */
    public int getPixels(int dp)
    {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * Creates a new job card
     *
     * Requires the layout the card needs to be added to and the text to display within the card
     * @param parent
     * @param jobID
     * @param description
     * @param client
     * @param priority
     */
    public void createJobCard(LinearLayout parent, String jobID, String description, String client, String priority)
    {
        //Get the required font
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat);
        //Layout to be placed inside the jobCard
        final LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout.setLayoutParams(layoutParams);

        final CardView card = new CardView(context);
        //Set layout params of cardView
        CardView.LayoutParams cardParams = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        //Design of the card
        cardParams.bottomMargin = getPixels(2);
        cardParams.height = getPixels(40);
        card.setLayoutParams(cardParams);
        card.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        card.setCardBackgroundColor(Color.parseColor("#CC373741"));
        //Layout params for hidden text
        LinearLayout.LayoutParams goneText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        //This text view is invisible but still accessible for other functionality
        final TextView goneID = new TextView(context);
        goneID.setLayoutParams(goneText);
        goneID.setVisibility(View.GONE);
        goneID.setText(jobID);

        //Layout params for text
        LinearLayout.LayoutParams normalText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        normalText.width = getPixels(145);
        normalText.gravity = Gravity.CENTER|Gravity.START;
        normalText.leftMargin = getPixels(2);
        //Two new text views
        TextView desc = new TextView(context);
        TextView clientName = new TextView(context);
        //Set their layout params
        desc.setLayoutParams(normalText);
        clientName.setLayoutParams(normalText);
        //Design the text views
        clientName.setText(client);
        clientName.setTextColor(Color.parseColor("#F3F3F3"));
        clientName.setTextSize(getPixels(7));
        clientName.setTypeface(font);

        desc.setText(description);
        desc.setTextColor(Color.parseColor("#F3F3F3"));
        desc.setTextSize(getPixels(7));
        desc.setTypeface(font);

        LinearLayout.LayoutParams endText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        endText.gravity = Gravity.CENTER|Gravity.START;
        endText.width = getPixels(90);
        //New text view
        TextView priorityText = new TextView(context);
        //Set layout params
        priorityText.setLayoutParams(endText);
        //Design
        priorityText.setText(priority);
        if(priority.equals("Urgent"))
        {
            priorityText.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(priority.equals("High"))
        {
            priorityText.setTextColor(Color.parseColor("#FFA500"));
        }
        else if(priority.equals("Low"))
        {
            priorityText.setTextColor(Color.parseColor("#03AAFB"));
        }
        else
        {
            priorityText.setTextColor(Color.parseColor("#F3F3F3"));
        }
        priorityText.setTextSize(getPixels(7));
        priorityText.setTypeface(font);
        //Add text views to layout
        layout.addView(goneID);
        layout.addView(desc);
        layout.addView(clientName);
        layout.addView(priorityText);
        //Add layout to the card
        card.addView(layout);
        //Add the card to its parent layout
        parent.addView(card);

        /**
         * On click of the job a new fragment will open and the jobID gets passed to it
         * The job id will then be used to retrieve all relevant job info
         */
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On click of job this code executes
                String passingID = goneID.getText().toString().trim();
                //Sets the bundle to be passed to new fragment
                Bundle bundle = new Bundle();
                bundle.putString("jobID", passingID);

                AppCompatActivity act = (AppCompatActivity) context;
                JobDetailFragment fr = new JobDetailFragment();
                fr.setArguments(bundle);
                act.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fr).addToBackStack("Jobs").commit();
            }
        });
    }

    /**
     * Creates a new client card to display all the existing clients
     *
     * @param parent the parent of the card to be created
     * @param client the name of the client
     * @param company the name of the company
     * @param locationString the location of the client
     */
    public void createClientCard(LinearLayout parent, String client, String company, String locationString)
    {
        //Get the font needed
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat);
        //Layout to be placed in card
        final LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout.setLayoutParams(layoutParams);

        final CardView card = new CardView(context);
        //Set the layout params of the new card
        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        //Design of the new card
        params.bottomMargin = getPixels(2);
        params.height = getPixels(40);
        card.setLayoutParams(params);
        card.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        card.setCardBackgroundColor(Color.parseColor("#CC373741"));

        //Custom parameters for location due to longer string length
        LinearLayout.LayoutParams paramsLocation = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        //Given more weight/space due to longer string
        paramsLocation.width = getPixels(150);
        paramsLocation.gravity = Gravity.CENTER;
        //Layout parameters for middle text
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsText.width = getPixels(100);
        paramsText.gravity = Gravity.CENTER;
        //Layout parameters for start text due to margin needed
        LinearLayout.LayoutParams paramsTextStart = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStart.setMarginStart(getPixels(8));
        paramsTextStart.width = getPixels(100);
        paramsTextStart.gravity = Gravity.CENTER;

        //Design the text view
        TextView clientName = new TextView(context);
        clientName.setLayoutParams(paramsTextStart);
        clientName.setText(client);
        clientName.setTextColor(Color.parseColor("#F3F3F3"));
        clientName.setTextSize(getPixels(7));
        clientName.setTypeface(font);
        //Design the text view
        TextView companyName = new TextView(context);
        companyName.setLayoutParams(paramsText);
        companyName.setText(company);
        companyName.setTextColor(Color.parseColor("#F3F3F3"));
        companyName.setTextSize(getPixels(7));
        companyName.setTypeface(font);
        //Design the text view
        final TextView location = new TextView(context);
        location.setLayoutParams(paramsLocation);
        location.setText(locationString);
        location.setTextColor(Color.parseColor("#03AAFB"));
        location.setTextSize(getPixels(7));
        location.setTypeface(font);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = location.getText().toString();

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                helper.navigateToLocation(text);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                helper.openLocation(text);
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog_theme);
                builder.setMessage("What would you like to do?").setPositiveButton("Navigate", dialogClickListener).setNegativeButton("View", dialogClickListener).show();
            }
        });

        //Add text to layout
        layout.addView(clientName);
        layout.addView(companyName);
        layout.addView(location);
        //Add text view to card
        card.addView(layout);
        //Add new card to layout
        parent.addView(card);
    }

    /**
     * Adds a potential client to the sales lead management page
     * Has the same name but receives different parameters
     *
     * @param parent the parent of the card to be added
     * @param client the name of the potential client
     * @param status the status or reason why the are potentially a client
     */
    public void createClientCard(LinearLayout parent, String client, String status)
    {
        //Get the font needed
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat);
        //Layout to be placed in card
        final LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout.setLayoutParams(layoutParams);

        final CardView card = new CardView(context);
        //Set the layout params of the new card
        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        //Design of the new card
        params.bottomMargin = getPixels(2);
        params.height = getPixels(40);
        card.setLayoutParams(params);
        card.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        card.setCardBackgroundColor(Color.parseColor("#CC373741"));

        //Layout parameters for start text due to margin needed
        LinearLayout.LayoutParams paramsTextStart = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextStart.setMarginStart(getPixels(8));
        paramsTextStart.weight = 1;
        paramsTextStart.width = getPixels(150);
        paramsTextStart.gravity = Gravity.CENTER;

        //Layout parameters for text
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsText.weight = 1;
        paramsText.width = getPixels(150);
        paramsText.gravity = Gravity.CENTER;

        TextView clientName = new TextView(context);
        clientName.setLayoutParams(paramsTextStart);
        clientName.setText(client);
        clientName.setTextColor(Color.parseColor("#F3F3F3"));
        clientName.setTextSize(getPixels(7));
        clientName.setTypeface(font);

        TextView statusTv = new TextView(context);
        statusTv.setLayoutParams(paramsText);
        statusTv.setText(status);
        statusTv.setTextColor(Color.parseColor("#F3F3F3"));
        statusTv.setTextSize(getPixels(7));
        statusTv.setTypeface(font);

        //Add text to layout
        layout.addView(clientName);
        layout.addView(statusTv);
        //Add text view to card
        card.addView(layout);
        //Add new card to layout
        parent.addView(card);
    }

    /**
     * Creates a stat card to display the stats on the sales lead management screen
     *
     * @param parent the parent layout of the card to be added
     * @param statDesc the description of the stat
     * @param stat the actual stat
     */
    public void createStatCard(LinearLayout parent, String statDesc, String stat)
    {
        //Get the font needed
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat);
        //Layout to be placed in card
        final LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);

        final CardView card = new CardView(context);
        //Set the layout params of the new card
        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        //Design of the new card
        params.rightMargin = getPixels(2);
        params.width = getPixels(100);
        params.height = getPixels(100);
        card.setLayoutParams(params);
        card.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        card.setCardBackgroundColor(Color.parseColor("#03AAFB"));

        //Layout parameters for text
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsText.weight = 1;

        TextView desc = new TextView(context);
        desc.setLayoutParams(paramsText);
        desc.setText(statDesc);
        desc.setTextColor(Color.parseColor("#F3F3F3"));
        desc.setTextSize(getPixels(7));
        desc.setTypeface(font);
        desc.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);

        TextView statInfo = new TextView(context);
        statInfo.setLayoutParams(paramsText);
        statInfo.setText(stat);
        statInfo.setTextColor(Color.parseColor("#F3F3F3"));
        statInfo.setTextSize(getPixels(12));
        statInfo.setTypeface(font);
        statInfo.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);

        //Add text to layout
        layout.addView(desc);
        layout.addView(statInfo);
        //Add text view to card
        card.addView(layout);
        //Add new card to layout
        parent.addView(card);
    }

    /**
     * Creates a feedback card on the sales lead management screen
     *
     * @param parent the parent of the card to be created
     * @param client the name of the client who made the review
     * @param job the name of the job being reviewed
     * @param feedback the feedback given
     */
    public void createFeedbackCard(LinearLayout parent, String client, String job, String feedback)
    {
        //Get the font needed
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat);
        //Layout to be placed in card
        final LinearLayout layout_outer = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout_outer.setOrientation(LinearLayout.HORIZONTAL);
        layout_outer.setLayoutParams(layoutParams);

        //Layout params for the textView
        LinearLayout.LayoutParams clientNameParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        clientNameParams.leftMargin = getPixels(8);
        clientNameParams.rightMargin = getPixels(8);
        clientNameParams.gravity = Gravity.CENTER;

        //Layout params for the textView
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.leftMargin = getPixels(8);
        textParams.rightMargin = getPixels(8);

        //Design the text view
        TextView clientName = new TextView(context);
        clientName.setLayoutParams(clientNameParams);
        clientName.setText(client);
        clientName.setTextColor(Color.parseColor("#03AAFB"));
        clientName.setTextSize(getPixels(7));
        clientName.setTypeface(font);
        clientName.setGravity(Gravity.CENTER);

        //Set inner layout layout params
        final LinearLayout layout_inner = new LinearLayout(context);
        LinearLayout.LayoutParams inner_layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout_inner.setOrientation(LinearLayout.VERTICAL);
        layout_inner.setBackgroundColor(Color.parseColor("#03AAFB"));
        layout_inner.setLayoutParams(inner_layoutparams);

        TextView jobDesc = new TextView(context);
        jobDesc.setLayoutParams(textParams);
        jobDesc.setText(job);
        jobDesc.setTextColor(Color.parseColor("#373741"));
        jobDesc.setTextSize(getPixels(7));
        jobDesc.setTypeface(font);
        jobDesc.setGravity(Gravity.CENTER|Gravity.START);

        TextView feedbackTv = new TextView(context);
        feedbackTv.setLayoutParams(textParams);
        feedbackTv.setText(feedback);
        feedbackTv.setTextColor(Color.parseColor("#F3F3F3"));
        feedbackTv.setTextSize(getPixels(7));
        feedbackTv.setTypeface(font);
        feedbackTv.setGravity(Gravity.CENTER|Gravity.START);

        final CardView card = new CardView(context);
        //Set the layout params of the new card
        CardView.LayoutParams params_card = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.MATCH_PARENT
        );
        params_card.width = getPixels(350);
        params_card.rightMargin = getPixels(5);
        card.setLayoutParams(params_card);
        card.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        card.setCardBackgroundColor(Color.parseColor("#CC373741"));

        layout_inner.addView(jobDesc);
        layout_inner.addView(feedbackTv);

        layout_outer.addView(clientName);
        layout_outer.addView(layout_inner);

        card.addView(layout_outer);
        parent.addView(card);
    }

    /**
     * Creates the tasks present in the database
     *
     * @param parent
     * @param task
     * @param id
     */
    public void createTaskCard(LinearLayout parent, String task, final String id, String taskEnd)
    {
        //Gets the font needed
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat);

        //Layout to be placed inside the jobCard
        final LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout.setLayoutParams(layoutParams);

        final CardView card = new CardView(context);
        //Set layout params of cardView
        CardView.LayoutParams cardParams = new CardView.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        //Design of the card
        cardParams.height = getPixels(40);
        card.setLayoutParams(cardParams);
        card.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        card.setCardBackgroundColor(Color.parseColor("#CC373741"));
        //Layout params for hidden text
        LinearLayout.LayoutParams goneText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        //This text view is invisible but still accessible for other functionality
        final TextView goneID = new TextView(context);
        goneID.setLayoutParams(goneText);
        goneID.setVisibility(View.GONE);
        goneID.setText(id);//Used to get id for task. This is needed to update or delete task in db

        //Layout params for text
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.width = getPixels(270);
        textParams.gravity = Gravity.CENTER|Gravity.START;
        textParams.leftMargin = getPixels(5);

        TextView taskTV = new TextView(context);
        taskTV.setLayoutParams(textParams);
        taskTV.setText(task);
        taskTV.setTextColor(Color.parseColor("#F3F3F3"));
        taskTV.setTextSize(getPixels(7));
        taskTV.setTypeface(font);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER|Gravity.START;

        CheckBox cbx = new CheckBox(context);
        cbx.setLayoutParams(params);
        CompoundButtonCompat.setButtonTintList(cbx, ColorStateList.valueOf(context.getResources().getColor(R.color.colorAccent)));

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        imageParams.width = getPixels(20);
        imageParams.height = getPixels(20);
        imageParams.gravity = Gravity.CENTER|Gravity.START;

        ImageView delete = new ImageView(context);
        delete.setLayoutParams(imageParams);
        delete.setImageResource(R.drawable.ic_trash);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Conect connection = new Conect();
                postVar = "DELETE_TASK";

                JSONObject data = new JSONObject();
                try
                {
                    data.put("id", goneID.getText().toString());//id of task to be deleted
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                postVar = postVar + "-" + data.toString();

                try
                {
                    String response = connection.execute(postVar).get().toString().trim();
                    if(response.equalsIgnoreCase("True"))
                    {
                        //Refreshes the fragment when a task is removed
                        AppCompatActivity act = (AppCompatActivity) context;
                        Fragment fr = act.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                        FragmentTransaction ft = fr.getFragmentManager().beginTransaction();
                        ft.detach(fr).attach(fr).commit();

                        Toast.makeText(context, "Task Removed", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                connection.cancel(true);
            }
        });

        if(!taskEnd.equalsIgnoreCase("null"))
        {
            //If the task has an end date it should be set as checked
            cbx.setChecked(true);
        }

        cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO
                String date;
                Conect connection = new Conect();
                if(isChecked)
                {
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                }
                else
                {
                    date = "NULL";
                }

                //Set end date in DB
                postVar = "CHANGE_TASK_STATE";
                JSONObject data = new JSONObject();

                try
                {
                    data.put("taskID", id);
                    data.put("endDate", date);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                postVar = postVar + "-" + "data" + "=" + data.toString();
                try
                {
                    connection.execute(postVar).get().toString().trim();
                }
                catch (ExecutionException e)
                {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                connection.cancel(true);
            }
        });

        layout.addView(goneID);
        layout.addView(cbx);
        layout.addView(taskTV);
        layout.addView(delete);

        card.addView(layout);

        parent.addView(card);
    }
}
