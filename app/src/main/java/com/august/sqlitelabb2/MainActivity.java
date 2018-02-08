package com.august.sqlitelabb2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayAdapter <String> adapter;
    private List taskTitles;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        taskTitles = dbHelper.getAllTaskTitles();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskTitles);
        listView.setAdapter(adapter);
    }



}
