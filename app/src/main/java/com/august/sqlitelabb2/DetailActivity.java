package com.august.sqlitelabb2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private TextView infoText;
    private Button finishButton;
    private Task task;
    private int currentTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dbHelper = new DBHelper(this);
        infoText = (TextView)findViewById(R.id.infoText);
        finishButton = (Button)findViewById(R.id.finishButton);
        currentTaskId = getIntent().getIntExtra("currentId", 1);
        task = dbHelper.getTask(currentTaskId);
        setupViews();
    }

    private void setupViews() {

        //ta med avklarad i info?
        String info = task.getTitle() + "(Priority: " + task.getPriority() +")" + '\n'
                + task.getDescription() + '\n' + "Category: " +
                task.getCategory() +'\n' + "Deadline: " + task.getDeadline();

        infoText.setText(info);

        if (task.getDone())
            finishButton.setVisibility(View.INVISIBLE);
        else
            finishButton.setVisibility(View.VISIBLE);
    }

    public void finishTask(View v) {
        dbHelper.finishTask(currentTaskId);
        setupViews();
    }

}
