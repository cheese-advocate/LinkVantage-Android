package com.example.compulinkapp;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class ContentGenerator {
    Context context;

    /**
     * Constructor to set the context for the class
     * @param ctx
     */
    public ContentGenerator(Context ctx)
    {
        context = ctx;
    }

    /**
     * Receives a dp value and converts that value into pixels
     * Returns a pixel value
     * @param dp
     * @return
     */
    public int getPixels(int dp)
    {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * Creates a new card to be added to the linear layout acting as contianer for the cards
     *
     * Requires the layout the card needs to be added to and the text to display within the card
     * @param mainLayout
     * @param text
     */
    public void createJobCard(LinearLayout mainLayout, String text)
    {
        //New job card to be added
        CardView card = new CardView(context);

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
        //Add text view to card
        card.addView(tv);
        //Add new card to layout
        mainLayout.addView(card);
    }
}
