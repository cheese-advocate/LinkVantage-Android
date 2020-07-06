package com.example.compulinkapp;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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
