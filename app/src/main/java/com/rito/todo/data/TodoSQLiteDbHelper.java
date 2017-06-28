package com.rito.todo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.rito.todo.data.TodoContract.TodoEntry;
import static com.rito.todo.data.TodoContract.TodoEntry.TABLE_NAME;


public class TodoSQLiteDbHelper extends SQLiteOpenHelper {
    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "todo.db";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
            TodoEntry._ID + " INTEGER PRIMARY KEY," +
            TodoEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            TodoEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            TodoEntry.COLUMN_NAME_IS_COMPLETE + INT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public TodoSQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //((TodoApplication)context).getAppComponent().inject(this);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.v("DB create query ", SQL_CREATE_ENTRIES);
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }


    public TodoItem insertTodoItem(TodoItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.getTitle());
        values.put(TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION, item.getDescription());
        values.put(TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE, item.isComplete());

        long newRowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);
        item.setId(newRowId);
        return item;
    }

    public TodoItem updateCompletionStatus(long itemId, int status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE, status);
        db.update(TodoContract.TodoEntry.TABLE_NAME, args, TodoContract.TodoEntry._ID + "=" + itemId, null);

        db.close();
        return null;
    }

    public void deleteItem(long itemId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + TodoEntry._ID + " = " + itemId);
        db.close();
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems;

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {TodoContract.TodoEntry._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TITLE,
                TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION,
                TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE,

        };


        String sortOrder = TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION + " DESC";

        Cursor resultsCursor = db.query(TodoContract.TodoEntry.TABLE_NAME, projection,
                null, null, null, null, sortOrder);
        todoItems = toTodoItemsList(resultsCursor);
        resultsCursor.close();
        return todoItems;
    }

    private List<TodoItem> toTodoItemsList(Cursor resultsCursor) {
        List todoItems = new ArrayList();
        try {
            while (resultsCursor.moveToNext()) {
                long itemId = resultsCursor.getLong(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID));
                String itemTitle = resultsCursor.getString(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE));
                String itemDescription = resultsCursor.getString(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION));
                int isComplete = resultsCursor.getInt(resultsCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE));
                TodoItem item = new TodoItem(itemId, itemTitle, itemDescription, isComplete);
                Log.v("Item", item.toString());
                todoItems.add(item);
            }
        } finally {
            resultsCursor.close();
        }

        return todoItems;
    }
}
