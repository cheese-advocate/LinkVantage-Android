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
     *Any location can be entered and will be opened directly in maps
     *
     * @param address address to be opened in the app
     */
    public void openLocation(String address)
    {
        Uri uri = Uri.parse("geo:0, 0?q=" + Uri.encode(address));

        Intent maps = new Intent(Intent.ACTION_VIEW, uri);
        maps.setPackage("com.google.android.apps.maps");
        context.startActivity(maps);
    }
}
