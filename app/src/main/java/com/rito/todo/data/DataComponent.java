package com.rito.todo.data;

import com.rito.todo.AppModule;
import com.rito.todo.TodoItemsList.TodoItemsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, DataModule.class})
public interface DataComponent {
    void inject(TodoItemsActivity activity);
}
