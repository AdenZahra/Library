package com.example.adenzahra.myapplication.Database;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
/**
 * Created by Aden Zahra on 1/26/2018.
 */

public class Download extends AsyncTask<String, Void, String> {

    public Download() {
    }

    protected String doInBackground(String... urls) {
        try {

            URL url = new URL(urls[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);
//            OutputStream output = new FileOutputStream(dst);
            String str = "";
            byte data[] = new byte[1024];
            long total = 0;
            int i = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                str = str + new String(data);
//                output.write(data, 0, count);
                i += 1;
            }
//            output.flush();
//            output.close();
            input.close();

            return str;
        } catch (Exception e) {
            return null;
        }
    }

    protected void onPostExecute(String str) {

    }
}

