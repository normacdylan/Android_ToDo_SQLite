package com.august.sqlitelabb2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText titleEdit;
    private EditText descriptionEdit;
    private DatePicker datePicker;
    private Button addButton;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper = new DBHelper(this);
        titleEdit = (EditText)findViewById(R.id.titleEdit);
        descriptionEdit = (EditText)findViewById(R.id.descriptionEdit);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        addButton = (Button)findViewById(R.id.addButton);
        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
    }

    public void addTask(View v) {
        Task task = new Task(
                titleEdit.getText().toString(),
                getDateFromPicker(),
                descriptionEdit.getText().toString(),
                "'Work'",
                "'Low Priority'");

        dbHelper.addTask(task);

        Intent addedIntent = new Intent(this, MainActivity.class);
        addedIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(addedIntent);
    }

    private String getDateFromPicker() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        String date = ""+year+"/"+month+"/"+day;
        return date;
    }

}
