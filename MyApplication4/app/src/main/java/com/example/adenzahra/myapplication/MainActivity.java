package com.example.adenzahra.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.example.adenzahra.myapplication.DataActivity;
import com.example.adenzahra.myapplication.Database.DBHelper;
import com.example.adenzahra.myapplication.Globals;
import com.example.adenzahra.myapplication.R;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper DB_Helper;
    EditText et_name, et_isbn, et_author, et_cost;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB_Helper = new DBHelper(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_isbn = (EditText) findViewById(R.id.et_isbn);
        et_author = (EditText) findViewById(R.id.et_author);
        et_cost = (EditText) findViewById(R.id.et_cost);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(Html.fromHtml("<font color='white'>Library Management</font>"));
        ab.setSubtitle("Add Item");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(Globals.FIRST_START, true)) {
            prefs.edit().putBoolean(Globals.FIRST_START, false).commit();
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Globals.WRITE_EXTERNAL_RCODE);
            }
            if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, Globals.VIDEO_RCODE);
            }
        }

    }




    protected void show_notification(String[] rows, int count) {


        //NotificationManager is required for creating and displaying notifications
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder notify = new NotificationCompat.Builder(this)
                .setContentTitle("Records retrieved :: " + count)
                .setSmallIcon(android.R.drawable.ic_dialog_map)
                .setColor(Color.BLUE)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.gallery_thumb))
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{100, 200, 300, 200, 222, 233, 55, 499, 89, 254, 156, 213, 21, 321, 121, 300, 200, 222, 233, 55, 499, 89, 254, 156, 213, 21, 321, 121, 300, 200, 222, 233, 55, 499, 89, 254, 156, 213, 21, 321, 121, 254, 25, 400, 300, 200, 10})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL))
                .setLights(Color.RED, 300, 700)
                .setAutoCancel(true);

        //style for displaying data
        NotificationCompat.InboxStyle is = new NotificationCompat.InboxStyle()
                .setBigContentTitle("Total Data: " + count);
        for (int i = 0; i < rows.length; i++) {
            is.addLine(rows[i]);
        }
        is.setSummaryText("All the data is now displayed in this notification");
        notify.setStyle(is);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    Globals.NOTIFICATION_CHANNEL_ID,
                    Globals.NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(Globals.NOTIFICATION_CHANNEL_NAME);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.enableLights(true);
            notify.setChannel(Globals.NOTIFICATION_CHANNEL_ID);
            manager.createNotificationChannel(channel);
        }


        manager.notify(Globals.NOTIFICATION_ID, notify.build());
//        Log.d(Globals.LOG_TAG, "Notification sent");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                et_name.setText("");
                et_isbn.setText("");
                et_author.setText("");
                et_cost.setText("");
                break;
            case R.id.btn_save:
                long rid = DB_Helper.insert(et_name.getText().toString(), et_isbn.getText().toString(), et_author.getText().toString(), et_cost.getText().toString());
                Cursor c = DB_Helper.read(rid);
                String[] rows = new String[c.getCount()];
                while (c.moveToNext()) {
                    rows[c.getPosition()] = "ID: " + c.getInt(c.getColumnIndex(DBHelper.ID))
                            + "\n" + "NAME: " + c.getString(c.getColumnIndex(DBHelper.NAME));
                }

                show_notification(rows, c.getCount());
                c.close();
                break;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_all:
                startActivity(new Intent(this, DataActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Globals.WRITE_EXTERNAL_RCODE)
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, permissions[0] + ":: Permission Denied", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, permissions[0] + ":: Permission Granted", Toast.LENGTH_SHORT).show();
            }
        else if (requestCode == Globals.CAMERA_RCODE)
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, permissions[0] + " :: Permission Denied", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, permissions[0] + " :: Permission Granted", Toast.LENGTH_SHORT).show();
            }
        else if (requestCode == Globals.VIDEO_RCODE)
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    Toast.makeText(this, permissions[i] + " :: Permission Denied", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, permissions[i] + " :: Permission Granted", Toast.LENGTH_SHORT).show();
            }
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}

