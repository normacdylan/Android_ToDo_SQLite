package com.august.sqlitelabb2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrx on 2018-02-05.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "ToDo";
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 11;

    // Priority table
    public static final String TABLE_PRIORITY = "priority";
    public static final String COLUMN_PRIORITY_ID = "priorityId";
    public static final String COLUMN_PRIORITY_NAME = "priorityName";
    private static final String TABLE_PRIORITY_CREATE =
            "CREATE TABLE " + TABLE_PRIORITY + " (" +
                    COLUMN_PRIORITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRIORITY_NAME + " TEXT" +
                    ");";

    // Category table
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_CATEGORY_NAME = "categoryName";
    private static final String TABLE_CATEGORY_CREATE =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_NAME + " TEXT" +
                    ");";

    // Task table
    public static final String TABLE_TASK = "task";
    public static final String COLUMN_TASK_ID = "taskId";
    public static final String COLUMN_TASK_TITLE = "taskTitle";
    public static final String COLUMN_TASK_DESCRIPTION = "taskDescription";
    public static final String COLUMN_TASK_DEADLINE = "taskDeadline";
    public static final String COLUMN_TASK_DONE = "taskDone";
    public static final String COLUMN_TASK_PRIORITY_ID = "taskPriorityId";
    public static final String COLUMN_TASK_CATEGORY_ID = "taskCategoryId";
    private static final String TABLE_TASK_CREATE =
            "CREATE TABLE " + TABLE_TASK + " (" +
                    COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK_TITLE + " TEXT, " +
                    COLUMN_TASK_DESCRIPTION + " TEXT, " +
                    COLUMN_TASK_DEADLINE + " TEXT, " +
                    COLUMN_TASK_DONE + " INTEGER," +
                    COLUMN_TASK_PRIORITY_ID + " INTEGER," +
                    COLUMN_TASK_CATEGORY_ID + " INTEGER" +
                    ");";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_PRIORITY_CREATE);
        db.execSQL(TABLE_CATEGORY_CREATE);
        db.execSQL(TABLE_TASK_CREATE);

        Log.i(LOGTAG, "Tables created");

        String addCategories = "INSERT INTO category (categoryName) VALUES ('Work'),('School'),('Home'),('Other');";
        String addPriorities = "INSERT INTO priority (priorityName) VALUES ('Low Priority'),('Medium Priority'),('High Priority');";

        db.execSQL(addCategories);
        db.execSQL(addPriorities);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS ";
        db.execSQL(drop + TABLE_TASK + ";");
        db.execSQL(drop + TABLE_CATEGORY + ";");
        db.execSQL(drop + TABLE_PRIORITY + ";");

        Log.i(LOGTAG, "Tables dropped");

        onCreate(db);
    }

    //Get all tasks in database
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT taskId, taskTitle, taskDescription, taskDeadline," +
                " taskDone, category.categoryName, priority.priorityName FROM task "+
                "INNER JOIN category ON category.categoryId = task.taskCategoryId "+
                "INNER JOIN priority ON priority.priorityId = task.taskPriorityId;";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
                task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
                task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
                task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
                task.setDone(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_DONE))==1);
                task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
                tasks.add(task);
            }
        }
        Log.i(LOGTAG, "All tasks fetched");
        return tasks;
    }

    //Get one task
    public Task getTask(int taskId) {
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT taskId, taskTitle, taskDescription, taskDeadline," +
                " taskDone, category.categoryName, priority.priorityName FROM task "+
                "INNER JOIN category ON category.categoryId = task.taskCategoryId "+
                "INNER JOIN priority ON priority.priorityId = task.taskPriorityId " +
                "WHERE taskId = " + taskId+";";

        Cursor c = db.rawQuery(query, null);

        Task task = new Task();

        while(c.moveToNext()){
            task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
            task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
            task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
            task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
            task.setDone(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_DONE))==1);
            task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
            task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
        }
        Log.i(LOGTAG, "Task with id "+taskId+" fetched");
        return task;
    }

    //Get all tasks belonging to specified priority group
    public List<Task> getPriorityTasks(String priority) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT taskId, taskTitle, taskDescription, taskDeadline," +
                " taskDone, category.categoryName, priority.priorityName FROM task "+
                "JOIN category ON task.taskCategoryId = category.categoryId "+
                "JOIN priority ON task.taskPriorityId = priority.priorityId "+
                "WHERE priority.priorityName = " + priority +";";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
                task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
                task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
                task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
                task.setDone(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_DONE))==1);
                task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
                tasks.add(task);
            }
        }
        Log.i(LOGTAG, "All tasks with priority " + priority + "fetched");
        return tasks;
    }

    //Get all tasks belonging to specified category
    public List<Task> getCategoryTasks(String category) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String  query = "SELECT taskId, taskTitle, taskDescription, taskDeadline," +
                " taskDone, category.categoryName, priority.priorityName FROM task "+
                "JOIN category ON task.taskCategoryId = category.categoryId "+
                "JOIN priority ON task.taskPriorityId = priority.priorityId "+
                "WHERE category.categoryName = " + category +";";

        Cursor c = db.rawQuery(query, null);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                Task task = new Task();
                task.setId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_ID)));
                task.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_TITLE)));
                task.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DESCRIPTION)));
                task.setDeadline(c.getString(c.getColumnIndex(DBHelper.COLUMN_TASK_DEADLINE)));
                task.setDone(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TASK_DONE))==1);
                task.setCategory(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
                task.setPriority(c.getString(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_NAME)));
                tasks.add(task);
            }
        }
        Log.i(LOGTAG, "All tasks with category " + category + "fetched");
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
        Log.i(LOGTAG, "All categories fetched");
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
        Log.i(LOGTAG, "All priorities fetched");
        return priorities;
    }

    //Get categoryId from categoryName
    public int getCategoryId(String categoryName) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT categoryId FROM category WHERE categoryName = '" +categoryName + "';";

        Cursor c = db.rawQuery(query, null);

        int categoryId = 0;

        while (c.moveToNext()) {
            categoryId = c.getInt(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_ID));
        }
        Log.i(LOGTAG, "CategoryId for  "+categoryName+"fetched");
        return categoryId;
    }

    //Get priorityId from priorityName
    public int getPriorityId(String priorityName) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT priorityId FROM priority WHERE priorityName = '" + priorityName + "';";

        Cursor c = db.rawQuery(query, null);

        int priorityId = 0;

        while (c.moveToNext()) {
            priorityId = c.getInt(c.getColumnIndex(DBHelper.COLUMN_PRIORITY_ID));
        }
        Log.i(LOGTAG, "PriorityId for  "+priorityName+"fetched");
        return priorityId;
    }

    //Add new task
    public void addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        int categoryId = getCategoryId(task.getCategory());
        int priorityId = getPriorityId(task.getPriority());

        String query = "INSERT INTO task (taskTitle, taskDescription, taskDeadline, taskDone, taskPriorityId, taskCategoryId) "
                        +"VALUES ('" + task.getTitle() + "', '" + task.getDescription() + "', '" + task.getDeadline() + "', 0" +
                        ", " + priorityId + ", " + categoryId +");";

        db.execSQL(query);
        Log.i(LOGTAG, "Task added");
    }

    //Delete task
    public void deleteTask(int taskId) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "DELETE FROM task WHERE taskId = " +taskId +";";
        db.execSQL(query);
        Log.i(LOGTAG, "Task deleted");

    }

    //Finish task
    public void finishTask(int taskId) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "UPDATE task SET taskDone = 1 WHERE taskId = " + taskId + ";";

        db.execSQL(query);
        Log.i(LOGTAG, "Task with id "+taskId+" set to done");

    }

}
