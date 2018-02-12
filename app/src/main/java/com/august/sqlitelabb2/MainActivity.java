package com.august.sqlitelabb2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ArrayAdapter <String> adapter;
    private ListView listView;
    private List<Task> tasks;
    private Spinner spinner;
    private List<String> priorities, categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        spinner = (Spinner)findViewById(R.id.spinner);
        dbHelper = new DBHelper(this);
        tasks = dbHelper.getAllTasks();
        priorities = dbHelper.getPriorities();
        categories = dbHelper.getCategories();
        setupSpinner();
        updateListView();

        final Intent detailIntent = new Intent(this, DetailActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                detailIntent.putExtra("currentId", tasks.get(position).getId());
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(detailIntent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            /*    switch (position) {
                    case 0:
                        tasks = dbHelper.getAllTasks();
                        break;
                    case 1:
                        tasks = dbHelper.getPriorityTasks("'Low Priority'");
                        break;
                    case 2:
                        tasks = dbHelper.getPriorityTasks("'Medium Priority'");
                        break;
                    case 3:
                        tasks = dbHelper.getPriorityTasks("'High Priority'");
                        break;
                    case 4:
                        tasks = dbHelper.getCategoryTasks("'Work'");
                        break;
                    case 5:
                        tasks = dbHelper.getCategoryTasks("'School'");
                        break;
                    case 6:
                        tasks = dbHelper.getCategoryTasks("'Home'");
                        break;
                    default:
                        tasks = dbHelper.getAllTasks();
                }
                updateListView(); */

                if (position==0)
                    tasks = dbHelper.getAllTasks();
                else if (position >0 && position < priorities.size()+1)
                    tasks = dbHelper.getPriorityTasks("'" + priorities.get(position-1) + "'");
                else if (position > priorities.size()+1 && position < priorities.size() + categories.size()+1)
                    tasks = dbHelper.getCategoryTasks("'" + categories.get(position-1-priorities.size()) + "'");

                updateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                tasks = dbHelper.getAllTasks();
                updateListView();
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tasks = dbHelper.getAllTasks();
        updateListView();
        spinner.setSelection(0);
    }

    public void addTask(View v) {
        Intent addIntent = new Intent(this, AddActivity.class);
        addIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(addIntent);
    }

    private void setupSpinner() {
        List<String> choices = new ArrayList<>();

        choices.add("Show All Tasks");
        for (String prio : priorities)
            choices.add(prio);
        for (String categ : categories)
            choices.add(categ);


     /*   choices.add("Show Low Priority Tasks");
        choices.add("Show Medium Priority Tasks");
        choices.add("Show High Priority Tasks");
        choices.add("Show Work Tasks");
        choices.add("Show School Tasks");
        choices.add("Show Home Tasks"); */



        ArrayAdapter<String> choicesAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, choices);
        choicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(choicesAdapter);
        spinner.setSelection(0);
    }

    private List<String> getTaskTitles() {
        List<String> taskTitles = new ArrayList<>();
        for (Task task : tasks)
            taskTitles.add(task.getTitle());
        return taskTitles;
    }

    private void updateListView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getTaskTitles());
        listView.setAdapter(adapter);
    }

}
