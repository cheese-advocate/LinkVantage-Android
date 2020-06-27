package com.example.compulinkapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conect extends AsyncTask {

    @Override protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        String urlString = "http://10.0.0.21/urlct2ws/index.php";
        String data = "post=";
        data = data + objects[0];
        String response = "";

        try{

            Log.d("log" ,"Attemping to post" + data + "to" + urlString );

            URL url = new URL (urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setUseCaches(false);
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoOutput(true); //allows writing to the
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");

            urlConnection.connect();


            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            os.close();
            //Post Code - End

            //Receiving Post Response - Start
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response = response + inputLine +"\n";
            }
            in.close();
            //Receiving Post Response - End


        }catch (Exception e){

            Log.d("errror" ,"Error message" + e.getStackTrace());
        }

        Log.d("Response:" ,"Response from text file: " + response);
        return null;
    }

}