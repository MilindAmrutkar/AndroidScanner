package com.android.androidscanner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.androidscanner.Asynctask.FetchStockIds;
import com.android.androidscanner.R;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements FetchStockIds.AsyncResponse{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void openScanQRCodeActivity(View view) {
        Intent intent = new Intent(this, ScanQRCode.class);
        startActivity(intent);
    }

    public void openNewItemActivity(View view) {
        FetchStockIds asyncTask = new FetchStockIds(this);
        asyncTask.delegate = this;
        asyncTask.execute();
    }

    @Override
    public void processFinish(String output) {
        String stockId = parseJSON(output);
        Log.i(TAG, "processFinish: stockId: " + output);
        Intent intent = new Intent(this, NewItem.class);
        intent.putExtra("stockId", stockId);
        startActivity(intent);
    }

    private String parseJSON(String myJSON) {
        String stockId;
        try {
            Log.i(TAG, "parseJSON: myJSON: " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if (!jsonRoot.getBoolean("status")) {
                    stockId = jsonRoot.getString("data");
                    Log.i(TAG, "showList: message: " + jsonRoot.get("message"));
                    return stockId;
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void openGenerateQRCode(View view) {
        Intent intent = new Intent(this, GenerateQR.class);
        startActivity(intent);
    }
}
