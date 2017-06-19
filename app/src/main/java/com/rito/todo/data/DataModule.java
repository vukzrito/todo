package com.rito.todo.data;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    TodoDatabase provideTodoDatabase(Application application) {
        return new TodoDatabaseImpl(application);
    }

    /*@Singleton
    @Provides
    TodoItemsContract.UserActionsListener provideTodoItemsPresenter(TodoDatabase database) {
        return new TodoItemsPresenter();
    }*/
}
