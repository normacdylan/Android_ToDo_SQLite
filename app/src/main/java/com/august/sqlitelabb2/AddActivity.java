package com.august.sqlitelabb2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText titleEdit;
    private EditText descriptionEdit;
    private DatePicker datePicker;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper = new DBHelper(this);
        titleEdit = (EditText)findViewById(R.id.titleEdit);
        descriptionEdit = (EditText)findViewById(R.id.descriptionEdit);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        addButton = (Button)findViewById(R.id.addButton);
    }

    public void addTask() {
        Task task = new Task(
                titleEdit.getText().toString(),
                datePicker.toString(),
                descriptionEdit.getText().toString(),
                "Category",
                "Priority");

        dbHelper.addTask(task);

        Intent addedIntent = new Intent(this, MainActivity.class);
        startActivity(addedIntent);
    }

}
