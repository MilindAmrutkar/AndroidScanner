package com.android.androidscanner.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.androidscanner.Asynctask.SendStockData;
import com.android.androidscanner.Model.Stock;
import com.android.androidscanner.R;
import com.android.androidscanner.Utilities.Utility;

import org.json.JSONException;

public class NewItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "NewItem";
    int spnrItemType = R.id.spnr_Type_EnterStockDetailsActivity;
    int spnrNoOfStone = R.id.spnr_NoOfStones_EnterStockDetailsActivity;
    String stockId;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        stockId = getIntent().getStringExtra("stockId");
        Log.i(TAG, "onCreate: stockId: " + stockId);
        setSpinnerData();
        setSku();
        setDate();
    }


    private void setSku() {
        ((TextView) findViewById(R.id.tv_SKU_EnterStockDetailsActivity)).setText(Utility.getSKU());
    }

    private void setDate() {
        ((TextView) findViewById(R.id.tv_Date_EnterStockDetailsActivity)).setText(Utility.getDate());
    }

    private void setSpinnerData() {
        setSpinnerAdapter(spnrItemType, R.array.item_type);
        setSpinnerAdapter(spnrNoOfStone, R.array.no_of_stones);
    }

    public void setSpinnerAdapter(int spinnerid, int arrayid) {
        spinner = (Spinner) findViewById(spinnerid);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayid, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spnrItemType) {
            String itemType = ((Spinner) findViewById(R.id.spnr_Type_EnterStockDetailsActivity)).getSelectedItem().toString();
            TextView tvSKU = ((TextView) findViewById(R.id.tv_SKU_EnterStockDetailsActivity));
            if (itemType.equalsIgnoreCase("Ring")) {
                tvSKU.setText("R" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Chain")) {
                tvSKU.setText("C" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Stud")) {
                tvSKU.setText("S" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Necklace")) {
                tvSKU.setText("N" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Earrings")) {
                tvSKU.setText("E" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Bracelet")) {
                tvSKU.setText("B" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Kalamani")) {
                tvSKU.setText("K" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Dollar")) {
                tvSKU.setText("D" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Haar")) {
                tvSKU.setText("H" + Utility.getSKU() + stockId);
            } else if (itemType.equalsIgnoreCase("Others")) {
                tvSKU.setText("O" + Utility.getSKU() + stockId);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onUpload(View view) throws JSONException {
        String grossWeight = ((EditText) findViewById(R.id.et_GrossWeight_EnterStockDetailsActivity)).getText().toString();
        String date = ((TextView) findViewById(R.id.tv_Date_EnterStockDetailsActivity)).getText().toString();
        String itemType = ((Spinner) findViewById(R.id.spnr_Type_EnterStockDetailsActivity)).getSelectedItem().toString();
        String stoneWeight = ((EditText) findViewById(R.id.et_StoneWeight_EnterStockDetailsActivity)).getText().toString();
        String SKU = ((TextView) findViewById(R.id.tv_SKU_EnterStockDetailsActivity)).getText().toString();
        String size = ((EditText) findViewById(R.id.et_Size_EnterStockDetailsActivity)).getText().toString();
        String noOfStones = ((Spinner) findViewById(R.id.spnr_NoOfStones_EnterStockDetailsActivity)).getSelectedItem().toString();
        String description = ((EditText) findViewById(R.id.et_Description_EnterStockDetailsActivity)).getText().toString();
        String netWeight = ((EditText) findViewById(R.id.et_NetWeight_EnterStockDetailsActivity)).getText().toString();

        Log.i(TAG, "onUpload: grossWeight: " + grossWeight + " date: " + date + " itemType: " + itemType +
                " stoneWeight: " + stoneWeight + " SKU: " + SKU + " size: " + size + " noOfStones: " + noOfStones +
                " description: " + description);

        Stock stock = new Stock();
        stock.setGrossWeight(grossWeight);
        stock.setDate(date);
        stock.setType(itemType);
        stock.setStoneWeight(stoneWeight);
        stock.setSku(SKU);
        stock.setSize(size);
        stock.setNoOfStones(noOfStones);
        stock.setDescription(description);
        stock.setNetWeight(netWeight);
        //String JSON = stock.getJSON();
        //Log.i(TAG, "onUpload: JSON: "+JSON);
        new SendStockData(this, SKU).execute(stock);
    }

}
