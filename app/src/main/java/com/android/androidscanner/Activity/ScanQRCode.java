package com.android.androidscanner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.androidscanner.Asynctask.FetchCurrentGoldRate;
import com.android.androidscanner.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQRCode extends AppCompatActivity implements FetchCurrentGoldRate.AsyncResponse {
    private TextView textViewSKU, textViewType, textViewNoOfStones, textViewStoneWeight, textViewNetWeight, textViewGrossWeight, textViewDescription, textViewItemRate;
    private String currentGoldRate;
    private IntentIntegrator qrScan;
    private static final String TAG = "ScanQRCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
        textViewSKU = (TextView) findViewById(R.id.textViewSku);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewNoOfStones = (TextView) findViewById(R.id.textViewNoOfStones);
        textViewStoneWeight = (TextView) findViewById(R.id.textViewStoneWeight);
        textViewNetWeight = (TextView) findViewById(R.id.textViewNetWeight);
        textViewGrossWeight = (TextView) findViewById(R.id.textViewGrossWeight);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewItemRate = (TextView) findViewById(R.id.textViewItemRate);
        fetchGoldRate();
        qrScan = new IntentIntegrator(this);
    }

    public void fetchGoldRate() {
        FetchCurrentGoldRate asyncTask = new FetchCurrentGoldRate(this);
        asyncTask.delegate = this;
        asyncTask.execute();
    }

    public void scanQRCode(View view) {
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result not found", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    textViewSKU.setText(obj.getString("sku"));
                    textViewType.setText(obj.getString("type"));
                    textViewNoOfStones.setText(obj.getString("noOfStones"));
                    textViewStoneWeight.setText(obj.getString("stoneWeight"));
                    textViewNetWeight.setText(obj.getString("netWeight"));
                    textViewGrossWeight.setText(obj.getString("gross_weight"));
                    textViewDescription.setText(obj.getString("description"));
                    long gross_weight = Long.parseLong(textViewGrossWeight.getText().toString());
                    long gold_rate = Long.parseLong(currentGoldRate);
                    long item_rate = gross_weight*gold_rate;
                    Log.i(TAG, "onActivityResult: itemRate: "+item_rate);

                    textViewItemRate.setText(item_rate+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void processFinish(String output) {
        currentGoldRate = parseJSON(output);
    }

    private String parseJSON(String myJSON) {
        String rate;
        try {
            Log.i(TAG, "parseJSON: myJSON: " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);

                rate = jsonRoot.getString("data");
                Log.i(TAG, "showList: message: " + jsonRoot.get("message"));
                return rate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

