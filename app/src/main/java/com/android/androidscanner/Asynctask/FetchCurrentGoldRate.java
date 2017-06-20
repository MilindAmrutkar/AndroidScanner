package com.android.androidscanner.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.androidscanner.Utilities.SetURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Admin on 20-Jun-17.
 */

public class FetchCurrentGoldRate extends AsyncTask<String, String, String> {
    private static String TAG = "FetchCurrentGoldRate";
    public AsyncResponse delegate = null;
    Context context;



    public FetchCurrentGoldRate(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {

        BufferedReader reader = null;
        String text = "";
        String data = "";
        try {
            String link = SetURL.FetchCurrentGoldRate;
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
                //sb.append(line);
            }
            text = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        delegate.processFinish(s);
        Log.i(TAG, "onPostExecute: s: " + s);
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

}
