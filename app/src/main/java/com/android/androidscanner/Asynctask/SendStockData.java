package com.android.androidscanner.Asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.androidscanner.Activity.GenerateQRCode;
import com.android.androidscanner.Activity.MainActivity;
import com.android.androidscanner.Model.Stock;
import com.android.androidscanner.Utilities.SetURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Admin on 6/8/2017.
 */

public class SendStockData extends AsyncTask<Stock, Integer, String> {
    private static String TAG = "SendStockData";
    String message, sku;
    private Context context;


    public SendStockData(Context context, String sku) {
        this.context = context;
        this.sku = sku;
    }

    @Override
    protected String doInBackground(Stock... params) {
        String line = "";
        String data = "";
        //String link = "http://192.168.0.102:8080/ImageTesting/uploadStockDetails.php";
        String link = SetURL.SendStockData;

        StringBuilder sb = null;
        URL url;
        HttpURLConnection conn;
        Stock stock = params[0];
        BufferedReader reader = null;

        try {
            url = new URL(link);
            sb = new StringBuilder();
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            data = "";
             data += "&" + URLEncoder.encode("json", "UTF-8")
                    + "=" + URLEncoder.encode(stock.getJSON(), "UTF-8");
            Log.i(TAG, "doInBackground: json: " + stock.getJSON());

            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Log.i(TAG, "doInBackground: sb: " + sb);

            JSONObject jsonObject = new JSONObject(sb.toString());
            message = jsonObject.getString("message");

            Log.i(TAG, "doInBackground: message: " + message);
            return message;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onPostExecute: s: " + s);
        if(s.equalsIgnoreCase("successfully uploaded")) {
            ((Activity) context).finish();
            Intent intent = new Intent(context, GenerateQRCode.class);
            intent.putExtra("sku", sku);
            context.startActivity(intent);

            //context.startActivity(new Intent(context, GenerateQRCode.class));
        } else {
            ((Activity) context).finish();
            context.startActivity(new Intent(context, MainActivity.class));
        }

    }
}
