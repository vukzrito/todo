package com.rito.todo.data;

import android.content.Context;

import com.rito.todo.TodoItemsList.TodoItemsRepository;
import com.rito.todo.TodoItemsList.TodoItemsrepositoryImpl;


public class Injection {
    public static TodoSQLiteDbHelper provideDataBaseHelper(Context context) {
        return new TodoSQLiteDbHelper(context);
    }


    public static TodoItemsRepository provideRepository(Context ctx) {
        return new TodoItemsrepositoryImpl(new TodoDatabaseImpl(ctx));
    }
}
