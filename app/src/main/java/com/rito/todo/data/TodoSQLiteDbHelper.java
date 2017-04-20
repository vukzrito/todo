package com.rito.todo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rito.todo.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import static com.rito.todo.data.TodoContract.TodoEntry;
import static com.rito.todo.data.TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE;
import static com.rito.todo.data.TodoContract.TodoEntry.TABLE_NAME;

/**
 * Created by RVukela on 2016/10/26.
 */

public class TodoSQLiteDbHelper extends SQLiteOpenHelper {
    public static final String TEXT_TYPE= " TEXT";
    public static final String INT_TYPE= " INTEGER";
    public  static final String COMMA_SEP= ",";
    private static final String SQL_CREATE_ENTRIES ="CREATE TABLE " + TABLE_NAME + " (" +
                                            TodoEntry._ID + " INTEGER PRIMARY KEY," +
                                            TodoEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                                            TodoEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP+
                                            TodoEntry.COLUMN_NAME_IS_COMPLETE + INT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "todo.db";


    public TodoSQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public int getCompletedCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME+" WHERE "+COLUMN_NAME_IS_COMPLETE+"= 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=0;
        try{
             count = cursor.getCount();


        }finally {
            cursor.close();
            return count;

        }

    }

    public TodoItem insertTodoItem(TodoItem item){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.getTitle());
        values.put(TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION, item.getDescription());
        values.put(TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE, item.isComplete());

        long newRowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);
        item.setId(newRowId);
        return  item;
    }

    public TodoItem updateCompletionStatus(long itemId, int status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put( TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE, status);
        db.update(TodoContract.TodoEntry.TABLE_NAME, args, TodoContract.TodoEntry._ID + "=" + itemId, null);

        db.close();
        return null;
    }

    public TodoItem getTodoItem(long itemId){
        return null;
    }

    public List<TodoItem>  getAllTodoItems(){
       List<TodoItem> todoItems;

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {TodoContract.TodoEntry._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TITLE,
                TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION,
                TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE,

        };


        String sortOrder = TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION + " DESC";

        Cursor resultsCursor = db.query(TodoContract.TodoEntry.TABLE_NAME,projection,
                                 null,null,null,null,sortOrder);
        todoItems = toTodoItemsList(resultsCursor);
        return todoItems;
    }

    private List<TodoItem> toTodoItemsList(Cursor resultsCursor){
        List todoItems = new ArrayList();
        todoItems.add(new TodoItem());
        try {
            while (resultsCursor.moveToNext()) {
                long itemId = resultsCursor.getLong(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID));
                String itemTitle =resultsCursor.getString(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE));
                String itemDescription =resultsCursor.getString(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION));
                int isComplete = resultsCursor.getInt(resultsCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE));
                TodoItem item = new TodoItem(itemId,itemTitle,itemDescription,isComplete);
                Log.v("Item", item.toString());
                todoItems.add(item);
            }
        } finally {
            resultsCursor.close();
        }

        return  todoItems;
    }
}
