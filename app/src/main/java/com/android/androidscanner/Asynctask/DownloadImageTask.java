package com.android.androidscanner.Asynctask;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.androidscanner.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Admin on 09-Jun-17.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private static String TAG = "DownloadImageTask";
    ImageView bmImage;
    Context context;

    public DownloadImageTask(ImageView bmImage, Context context) {
        //here we are getting the id of the bitmap
        this.bmImage = bmImage;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {

        String json = urls[0];
        String link = urls[1];
        String size = urls[2];
        Log.i(TAG, "doInBackground: json: "+json+" link: "+link+" size: "+size);
        URL url = null;
        URLConnection conn =null;
        String data = "";
        Bitmap image = null;
        try {
            url = new URL(link);
            conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            data += URLEncoder.encode("data","UTF-8")
                    + "=" + URLEncoder.encode(json, "UTF-8");
            data += "&" + URLEncoder.encode("size","UTF-8")
                    + "=" + URLEncoder.encode(size, "UTF-8");
            Log.i(TAG, "doInBackground: data: "+data);

            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            image = BitmapFactory.decodeStream(conn.getInputStream());

            //String filePath = saveToInternalStorage(image);
            //Log.i(TAG, "doInBackground: filepath: "+filePath);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Bitmap result) {
        Log.i(TAG, "onPostExecute: result"+result);
        if(result!=null)
            bmImage.setImageBitmap(result);
        else
            bmImage.setImageResource(R.drawable.error);
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

}
