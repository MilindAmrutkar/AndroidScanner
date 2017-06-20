package com.android.androidscanner.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.androidscanner.Asynctask.DownloadImageTask;
import com.android.androidscanner.Asynctask.FetchItemDetails;
import com.android.androidscanner.Model.Stock;
import com.android.androidscanner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GenerateQR extends AppCompatActivity implements FetchItemDetails.AsyncResponse{
    EditText editText;
    private static final String TAG = "GenerateQR";
    Stock stock;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        editText = (EditText) findViewById(R.id.et_SKU);
        stock = new Stock();
        imageView = (ImageView) findViewById(R.id.iv_qrCode_generateQR);

    }

    public void fetchValues(View view) {
        String sku = editText.getText().toString();
        FetchItemDetails asyncTask = new FetchItemDetails(this);
        asyncTask.delegate = this;
        asyncTask.execute(sku);
    }

    @Override
    public void processFinish(String output) {
        String json="";
        String size;
        parseJSON(output);
        Log.i(TAG, "processFinish: sku: "+stock.getSku());
        Log.i(TAG, "processFinish: item: "+stock.getType());
        size = "100x100";
        try {
            json = stock.getQRJSON();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new DownloadImageTask(imageView, this).execute(json, "http://api.qrserver.com/v1/create-qr-code/", size);

    }

    private void parseJSON(String myJSON) {

        try {
            Log.i(TAG, "parseJSON: myJSON: " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if (!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String sku = jsonObject.get("sku").toString();
                        String type = jsonObject.get("type").toString();
                        String date = jsonObject.get("date").toString();
                        String size = jsonObject.get("size").toString();
                        String no_of_stones = jsonObject.get("no_of_stones").toString();
                        String stone_weight = jsonObject.get("stone_weight").toString();
                        String bill_no = jsonObject.get("bill_no").toString();
                        String net_weight = jsonObject.get("net_weight").toString();
                        String gross_weight = jsonObject.get("gross_weight").toString();
                        String description = jsonObject.get("description").toString();
                        stock.setSku(sku);
                        stock.setType(type);
                        stock.setDate(date);
                        stock.setSize(size);
                        stock.setNoOfStones(no_of_stones);
                        stock.setStoneWeight(stone_weight);
                        stock.setBillNo(bill_no);
                        stock.setNetWeight(net_weight);
                        stock.setGrossWeight(gross_weight);
                        stock.setDescription(description);
                    }
                    Log.i(TAG, "showList: message: " + jsonRoot.get("message"));

                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void shareImage(View view) {

    }
}
