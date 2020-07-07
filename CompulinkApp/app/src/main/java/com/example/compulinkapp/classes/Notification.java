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
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent todo = new Intent(context, DashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, todo, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notifications")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Linkvantage")
                .setContentText("You have items on your TODO list")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Log.d("T", "TEST TEST TEST 000000000000000");
        if(FileHelper.hasItemsCheck(context))
        {
            Log.d("T", "TEST TEST TEST");
            notificationManager.notify(1, builder.build());
        }
    }
}
