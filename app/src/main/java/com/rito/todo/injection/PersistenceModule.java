package com.rito.todo.injection;

import android.content.Context;

import com.rito.todo.TodoItemsList.TodoItemsRepository;
import com.rito.todo.TodoItemsList.TodoItemsRepositoryImpl;
import com.rito.todo.data.TodoDatabase;
import com.rito.todo.data.TodoDatabaseImpl;
import com.rito.todo.data.TodoSQLiteDbHelper;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {
    @Provides
    @Singleton
    TodoItemsRepository provideTodoItemsRepository(Context context){
        return new TodoItemsRepositoryImpl(context);
    }

    @Provides
    @Singleton
    TodoDatabase provideTodoDatabase(Context context){
        return new TodoDatabaseImpl(context);
    }
    @Provides
    @Singleton
    TodoSQLiteDbHelper provideTodoSQLiteHelper(Context context){
        return new TodoSQLiteDbHelper(context);
    }
}
