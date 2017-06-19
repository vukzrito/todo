package com.rito.todo.injection;

import android.content.Context;

import com.rito.todo.TodoItemsList.TodoItemsContract;
import com.rito.todo.TodoItemsList.TodoItemsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    TodoItemsContract.UserActionsListener provideTodoItemsPresenter(Context context){
        return new TodoItemsPresenter(context);
    }
}
