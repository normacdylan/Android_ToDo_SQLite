package com.august.sqlitelabb2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrx on 2018-02-05.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "ToDo";
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    //autoincrement?


    // Priority table
    public static final String TABLE_PRIORITY = "priority";
    public static final String COLUMN_PRIORITY_ID = "priorityId";
    public static final String COLUMN_PRIORITY_NAME = "priorityName";
    private static final String TABLE_PRIORITY_CREATE =
            "CREATE TABLE " + TABLE_PRIORITY + " (" +
                    COLUMN_PRIORITY_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_PRIORITY_NAME + " TEXT, " +
                    ")";

    // Category table
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_CATEGORY_NAME = "categoryName";
    private static final String TABLE_CATEGORY_CREATE =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CATEGORY_NAME + " TEXT, " +
                    ")";

    // Task table
    public static final String TABLE_TASK = "task";
    public static final String COLUMN_TASK_ID = "taskId";
    public static final String COLUMN_TASK_TITLE = "taskTitle";
    public static final String COLUMN_TASK_DESCRIPTION = "taskDescription";
    public static final String COLUMN_TASK_DEADLINE = "taskDeadline";
    public static final String COLUMN_TASK_PRIORITY_ID = "taskPriorityId";
    public static final String COLUMN_TASK_CATEGORY_ID = "taskCategoryId";
    private static final String TABLE_TASK_CREATE =
            "CREATE TABLE " + TABLE_TASK + " (" +
                    COLUMN_TASK_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_TASK_TITLE + " TEXT, " +
                    COLUMN_TASK_DESCRIPTION + " TEXT, " +
                    COLUMN_TASK_DEADLINE + " TEXT, " +
                    COLUMN_TASK_PRIORITY_ID + " INT" +
                    ")";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_PRIORITY_CREATE);
        db.execSQL(TABLE_CATEGORY_CREATE);
        db.execSQL(TABLE_TASK_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    /*    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIORITY_CREATE); */
    }

    //get all tasks in database
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT * FROM task "+
                "JOIN category ON task.categoryId = category.Id "+
                "JOIN priority ON task.priorityId = priority.Id";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
                task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
                task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
                task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
                task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
                tasks.add(task);
            }
        }
        return tasks;
    }

    //Get all tasks belonging to specified priority group
    public List<Task> getPriorityTasks(String priority) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT * FROM task "+
                "JOIN category ON task.categoryId = category.Id "+
                "JOIN priority ON task.priorityId = priority.Id "+
                "WHERE priority.priorityName = " + priority +";";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
                task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
                task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
                task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
                task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
                tasks.add(task);
            }
        }
        return tasks;
    }

    //Get all tasks belonging to specified category
    public List<Task> getCategoryTasks(String category) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT * FROM task "+
                "JOIN category ON task.categoryId = category.Id "+
                "JOIN priority ON task.priorityId = priority.Id "+
                "WHERE category.categoryName = " + category +";";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
                task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
                task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
                task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
                task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
                tasks.add(task);
            }
        }
        return tasks;
    }

    //Get all tasks sorted according to specified column and order
    public List<Task> getAllTasksSorted(String column, String order) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT * FROM task "+
                "JOIN category ON task.categoryId = category.Id "+
                "JOIN priority ON task.priorityId = priority.Id "+
                "ORDER BY " + column +" "+ order +";";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
                task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
                task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
                task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
                task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
                tasks.add(task);
            }
        }
        return tasks;
    }

    //Get names of all categories
    public List getCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT categoryName FROM category;";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                String category = c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME));
                categories.add(category);
            }
        }
        return categories;
    }

    //Get names of all priorities
    public List getPriorities() {
        List<String> priorities = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT priorityName FROM priority;";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                String priority = c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME));
                priorities.add(priority);
            }
        }
        return priorities;
    }

    //Get categoryId from categoryName
    public int getCategoryId(String categoryName) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT categoryId FROM category WHERE categoryName = " +categoryName + ";";

        Cursor c = db.rawQuery(query, null);

        return c.getInt(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_ID));
    }

    //Get priorityId from priorityName
    public int getPriorityId(String priorityName) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT priorityId FROM priority WHERE priorityName = " + priorityName + ";";

        Cursor c = db.rawQuery(query, null);

        return c.getInt(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_ID));
    }

    public void addTask(Task task) {
        //Casta tasks variabler?
        SQLiteDatabase db = getWritableDatabase();

        int categoryId = getCategoryId(task.getCategory());
        int priorityId = getPriorityId(task.getPriority());

        String query = "INSERT INTO task (taskTitle, taskDescription, taskDeadline, taskPriorityId, taskCategoryId) "
                        +"VALUES (" + task.getTitle() + ", " + task.getDescription() + ", " + task.getDeadline() +
                        ", " + priorityId + ", " + categoryId +");";

        db.execSQL(query);
    }

    public void deleteTask(int taskId) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "DELETE FROM task WHERE taskId = " +taskId +";";
        db.execSQL(query);
    }

}
