package com.rito.todo.data;

import android.content.Context;

import com.rito.todo.todoItems.TodoItemsRepository;
import com.rito.todo.todoItems.TodoItemsRepositoryImpl;


public class Injection {
    public static TodoSQLiteDbHelper provideDataBaseHelper(Context context) {
        return new TodoSQLiteDbHelper(context);
    }


    public static TodoItemsRepository provideRepository(Context ctx) {
        return new TodoItemsRepositoryImpl(new TodoDatabaseImpl(ctx));
    }
}
