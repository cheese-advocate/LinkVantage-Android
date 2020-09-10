package com.example.compulinkapp.classes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.example.compulinkapp.R;

public class ContentGenerator {
    Context context;
    View view;
    
    /**
     * Constructor to set the context and view for the class
     * @param ctx
     */
    public ContentGenerator(Context ctx, View view_sent)
    {
        context = ctx;
        view = view_sent;
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
     * Creates a new card to be added to the linear layout that is acting as container for the cards
     *
     * The argument list still needs to be modified so that it accepts all content needed for a job
     * This is just a working template for testing purposes
     *
     * Requires the layout the card needs to be added to and the text to display within the card
     * @param mainLayout
     * @param text
     */
    public void createJobCard(LinearLayout mainLayout, String text)
    {
        //New job card to be added
        final CardView card = new CardView(context);

        //Set layout parameters for new card
        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.MATCH_PARENT
        );
        //Design of the new card
        params.setMarginStart(getPixels(10));
        params.width = getPixels(150);
        card.setLayoutParams(params);
        card.setCardBackgroundColor(Color.parseColor("#CC373741"));

        //Layout parameters for text
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        //Design the text view
        TextView tv = new TextView(context);
        tv.setLayoutParams(paramsText);
        tv.setText(text);
        tv.setTextColor(Color.parseColor("#F3F3F3"));
        tv.setTextSize(getPixels(8));
        tv.setTextAlignment(TextView.TEXT_ALIGNMENT_GRAVITY);
        tv.setGravity(Gravity.CENTER);

        /**
         * Add onClickListener to the newly created card
         * This ensures that newly added cards will also
         * be able to move between the different job categories
         */
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout parent = (LinearLayout) card.getParent();
                ContentGenerator cg = new ContentGenerator(context, view);
                cg.changeParent(parent, card);
            }
        });

        //Add text view to card
        card.addView(tv);
        //Add new card to layout
        mainLayout.addView(card);
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
        TextView location = new TextView(context);
        location.setLayoutParams(paramsLocation);
        location.setText(locationString);
        location.setTextColor(Color.parseColor("#F3F3F3"));
        location.setTextSize(getPixels(7));
        location.setTypeface(font);

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
        paramsTextStart.gravity = Gravity.CENTER;

        //Layout parameters for text
        LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsText.weight = 1;
        paramsText.gravity = Gravity.CENTER;

        TextView clientName = new TextView(context);
        clientName.setLayoutParams(paramsTextStart);
        clientName.setText(client);
        clientName.setTextColor(Color.parseColor("#F3F3F3"));
        clientName.setTextSize(getPixels(7));
        clientName.setTypeface(font);

        TextView statusTv = new TextView(context);
        statusTv.setLayoutParams(paramsTextStart);
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
     * Changes the specified card view's parent. If the card needs to be displayed in
     * different location after user performs specific action
     *
     * When card is in finished jobs - gets removed on click
     * When card is in pending jobs - gets moved to active jobs on click
     * When card is in active jobs - gets moved to finished jobs on click
     *
     * Requires the current of the view parent and the view to be moved
     * @param parent - in linear layout format
     * @param vw - in Card View format
     */
    public void changeParent(LinearLayout parent, CardView vw)
    {
        //Layout params need to be reset
        CardView.LayoutParams params = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.MATCH_PARENT
        );
        params.setMarginStart(getPixels(10));
        params.width = getPixels(150);
        //Set the layout params
        vw.setLayoutParams(params);
        //checks if the parent given is null
        if(parent != null)
        {
            //decides where to move the clicked view based on the view's parent
            if(parent.getId() == R.id.jobs_active)
            {
                parent.removeView(vw);
                LinearLayout newParent = (LinearLayout) view.findViewById(R.id.jobs_finished);
                newParent.addView(vw);
            }
            else if(parent.getId() == R.id.jobs_pending)
            {
                parent.removeView(vw);
                LinearLayout newParent = (LinearLayout) view.findViewById(R.id.jobs_active);
                newParent.addView(vw);
            }
            else if(parent.getId() == R.id.jobs_finished)
            {
                parent.removeView(vw);
            }
        }
    }
}
