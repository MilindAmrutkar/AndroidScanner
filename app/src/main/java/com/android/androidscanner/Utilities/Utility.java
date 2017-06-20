package com.android.androidscanner.Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 5/28/2017.
 */

public class Utility {
    private static String TAG = "Utility";





    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getSKU() {
        String todaysDate = getDate();
        String dateArr[] = todaysDate.split("/");
        String sku = "";
        for (String value : dateArr) {
            sku += value;
        }
        return sku;
    }

    public static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }


}
