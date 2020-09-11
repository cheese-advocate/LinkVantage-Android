package com.example.compulinkapp.classes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GoogleMapsHelper {
    Context context;

    public GoogleMapsHelper(Context ctx)
    {
        context = ctx;
    }

    /**
     * Opens a specified location in the google maps app
     * Any location can be entered and will be opened directly in maps
     *
     * @param address address to be opened in the app
     */
    public void openLocation(String address)
    {
        //The uri is used by google maps to determine what to do - Open only a location/Navigate to that location etc.
        Uri uri = Uri.parse("geo:0, 0?q=" + Uri.encode(address));//Creates the string passed to google maps
        Intent maps = new Intent(Intent.ACTION_VIEW, uri);//Creates intent that starts google maps
        maps.setPackage("com.google.android.apps.maps");//Specify to the device to use google maps
        context.startActivity(maps);//Start google maps intent
    }
}
