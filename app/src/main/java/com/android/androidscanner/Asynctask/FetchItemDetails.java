package com.android.androidscanner.Asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.androidscanner.Utilities.SetURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Admin on 20-Jun-17.
 */

public class FetchItemDetails extends AsyncTask<String, String, String> {
    private static String TAG = "FetchItemDetails";
    public AsyncResponse delegate = null;
    Context context;


    public FetchItemDetails(Context context) {
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
        String sku = params[0];
        Log.i(TAG, "doInBackground: sku: " + sku);
        try {
            String link = SetURL.FetchItemDetails;
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            data = "";
            data += "&" + URLEncoder.encode("sku", "UTF-8")
                    + "=" + URLEncoder.encode(sku, "UTF-8");
            Log.i(TAG, "doInBackground: json: " + sku);

            outputStreamWriter.write(data);
            outputStreamWriter.flush();

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
            Log.i(TAG, "doInBackground: text: " + text);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i(TAG, "onPostExecute: response from background s: " + s);
        super.onPostExecute(s);
        //delegate is the reference of interface
        delegate.processFinish(s);
        Log.i(TAG, "onPostExecute: s: " + s);


    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

}
