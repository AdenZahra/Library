package com.example.adenzahra.myapplication;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.adenzahra.myapplication.DataAdapter;
import com.example.adenzahra.myapplication.Database.DBHelper;
import com.example.adenzahra.myapplication.Models.Library;
import com.example.adenzahra.myapplication.R;

/**
 * Created by Aden Zahra on 1/26/2018.
 */

public class DataActivity extends AppCompatActivity {
    DataAdapter dataAdapter;
    RecyclerView recyclerView;
    DBHelper DB_Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
        DB_Helper = new DBHelper(this);


        //Code to change the title bar of any activity that extends AppCompatActivity
        ActionBar ab = getSupportActionBar();
        ab.setTitle(Html.fromHtml("<font color='green'>All</font> <font color='yellow'><b>Records</b>"));
        ab.setSubtitle("All records are listed below");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<Library> ui = DB_Helper.getAll();

        if(ui.size()<=0) {
            Toast.makeText(this,"No Data",Toast.LENGTH_LONG).show();

        }
        else {
            dataAdapter = new DataAdapter(this, ui);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);

            recyclerView.setAdapter(dataAdapter);

        }
    }
}
