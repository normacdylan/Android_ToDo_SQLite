package com.august.sqlitelabb2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayAdapter <String> adapter;
    private List<String> taskTitles;
    private ListView listView;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        dbHelper = new DBHelper(this);
        taskTitles = new ArrayList<String>();
        taskTitles = dbHelper.getAllTaskTitles();
        tasks = new ArrayList<Task>();
        tasks = dbHelper.getAllTasks();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskTitles);
        listView.setAdapter(adapter);
        final Intent detailIntent = new Intent(this, DetailActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detailIntent.putExtra("currentId", tasks.get(position).getId());
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskTitles = dbHelper.getAllTaskTitles();
        tasks = dbHelper.getAllTasks();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskTitles);
        listView.setAdapter(adapter);
    }

    public void addTask(View v) {
        Intent addIntent = new Intent(this, AddActivity.class);
        addIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(addIntent);
    }



}
