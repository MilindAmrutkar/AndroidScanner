package com.android.androidscanner.Utilities;

/**
 * Created by Admin on 08-Jun-17.
 */

public class SetURL {
    private static final String URL = "http://192.168.0.102:8080/ResearchPaper/";

    public static final String FetchStockIds = URL + "getIdFromStockForSKU.php";
    public static final String SendStockData = URL + "uploadStockDetails.php";
    public static final String FetchItemDetails = URL + "getItemDetails.php";
    public static final String FetchCurrentGoldRate = URL + "getCurrentRate.php";
}
