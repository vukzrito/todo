package com.rito.todo.injection;

import com.rito.todo.TodoItemsList.TodoItemsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class})
public interface AppComponent {
    void inject(TodoItemsActivity activity);
}
