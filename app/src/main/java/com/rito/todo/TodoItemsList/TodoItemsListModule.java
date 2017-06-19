package com.rito.todo.TodoItemsList;

import com.rito.todo.util.CustomScope;

import dagger.Module;
import dagger.Provides;

@Module
public class TodoItemsListModule {
    private final TodoItemsContract.View view;

    public TodoItemsListModule(TodoItemsContract.View view) {
        this.view = view;
    }

    @Provides
    @CustomScope
    TodoItemsContract.View provideTodoItemsListView(){
        return view;
    }
}
