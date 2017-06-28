package com.rito.todo.injection;

import android.content.Context;

import com.rito.todo.TodoItemsList.TodoItemsContract;
import com.rito.todo.TodoItemsList.TodoItemsPresenter;
import com.rito.todo.TodoItemsList.TodoItemsRepository;
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
    TodoSQLiteDbHelper provideTodoSQLiteHelper(Context context) {
        return new TodoSQLiteDbHelper(context);
    }


    @Singleton
    @Provides
    TodoDatabase provideTodoDatabase(TodoSQLiteDbHelper todoSQLiteDbHelper) {
        return new TodoDatabaseImpl(todoSQLiteDbHelper);
    }


    @Singleton
    @Provides
    TodoItemsContract.UserActionsListener providePresenter(TodoItemsRepository todoItemsRepository) {
        return new TodoItemsPresenter(todoItemsRepository);
    }
}
