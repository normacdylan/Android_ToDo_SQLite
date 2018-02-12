package com.august.sqlitelabb2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private TextView infoText;
    private Button finishButton;
    private Button deleteButton;
    private Task task;
    private int currentTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dbHelper = new DBHelper(this);
        infoText = (TextView)findViewById(R.id.infoText);
        finishButton = (Button)findViewById(R.id.finishButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        currentTaskId = getIntent().getIntExtra("currentId", 0);
        task = dbHelper.getTask(currentTaskId);
        setupViews();
    }

    private void setupViews() {
        String done = task.getDone()? "Already finished" : "Not yet finished";

        String info = task.getTitle() + " ("+task.getPriority() +")" + '\n'
                + task.getDescription() + '\n' + "Category: " +
                task.getCategory() +'\n' + "Deadline: " + task.getDeadline() +
                '\n' + done;

        infoText.setText(info);

        if (task.getDone())
            finishButton.setVisibility(View.INVISIBLE);
        else
            finishButton.setVisibility(View.VISIBLE);
    }

    public void finishTask(View v) {
        dbHelper.finishTask(currentTaskId);
        task = dbHelper.getTask(currentTaskId);
        setupViews();
    }

    public void deleteTask(View v) {
        dbHelper.deleteTask(currentTaskId);
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

}
