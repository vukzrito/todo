package com.rito.todo.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.rito.todo.data.TodoContract.TodoEntry;
import static com.rito.todo.data.TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE;
import static com.rito.todo.data.TodoContract.TodoEntry.TABLE_NAME;

/**
 * Created by RVukela on 2016/10/26.
 */

public class TodoDbHelper extends SQLiteOpenHelper {
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


    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "todo.db";


    public TodoDbHelper(Context context) {
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
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}
