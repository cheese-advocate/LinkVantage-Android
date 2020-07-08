package com.example.compulinkapp.classes;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.compulinkapp.R;
import com.example.compulinkapp.activities.DashActivity;

public class Notification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /**
         * Get the default notification sound of the device the app is installed on
         * This will be the sound of the notification
         */
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        /**
         * Set the intent to open the activity associated with the notification
         * The notification will open the correct page when clicked on
         */
        Intent todo = new Intent(context, DashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, todo, PendingIntent.FLAG_UPDATE_CURRENT);

        /**
         * Creating the notification and setting the look and feel
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notifications")
                .setSmallIcon(R.drawable.ic_bell)   //notification icon
                .setContentTitle("Linkvantage")     //notification title
                .setContentText("You have items on your TODO list")     //The text displayed in the notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)       //Priority of the notification
                .setAutoCancel(true)    //Enable the user to remove the notification on swipe to side
                .setSound(notificationSound)    //Set the notification to have a sound
                .setContentIntent(pendingIntent);   //Set the intent earlier created to the notification

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        /**
         * Build and show the notification if the condition is met
         */
        if(FileHelper.hasItemsCheck(context))
        {
            notificationManager.notify(1, builder.build());
        }
    }
}
