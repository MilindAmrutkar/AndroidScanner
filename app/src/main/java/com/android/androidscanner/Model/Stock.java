package com.android.androidscanner.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 6/8/2017.
 */

public class Stock {
    String sku, type, date, size, noOfStones, stoneWeight, billNo, grossWeight, billDate, status, description, netWeight;

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNoOfStones() {
        return noOfStones;
    }

    public void setNoOfStones(String noOfStones) {
        this.noOfStones = noOfStones;
    }

    public String getStoneWeight() {
        return stoneWeight;
    }

    public void setStoneWeight(String stoneWeight) {
        this.stoneWeight = stoneWeight;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQRJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("sku", sku);
        jsonObject.accumulate("type", type);
        jsonObject.accumulate("noOfStones", noOfStones);
        jsonObject.accumulate("stoneWeight", stoneWeight);
        jsonObject.accumulate("netWeight", netWeight);
        jsonObject.accumulate("gross_weight", grossWeight);
        jsonObject.accumulate("description", description);

        /*JSONObject rootObject = new JSONObject();
        rootObject.accumulate("item", jsonObject);*/
        return jsonObject.toString();
    }

    public String getJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("sku", sku);
        jsonObject.accumulate("gross_weight", grossWeight);
        jsonObject.accumulate("net_weight", netWeight);
        jsonObject.accumulate("date", date);
        jsonObject.accumulate("size", size);
        jsonObject.accumulate("noOfStones", noOfStones);
        jsonObject.accumulate("description", description);
        jsonObject.accumulate("itemType", type);
        jsonObject.accumulate("stoneWeight", stoneWeight);
        jsonObject.accumulate("billNo", billNo);

        JSONObject rootObject = new JSONObject();
        rootObject.accumulate("stock", jsonObject);
        return rootObject.toString();
    }
}
