package com.rito.todo.injection;

import com.rito.todo.TodoItemsList.TodoItemsContract;
import com.rito.todo.TodoItemsList.TodoItemsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    TodoItemsContract.UserActionsListener provideTodoItemsPresenter(){
        return new TodoItemsPresenter();
    }
}
