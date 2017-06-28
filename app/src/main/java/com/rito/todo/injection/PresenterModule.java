package com.rito.todo.injection;

import com.rito.todo.TodoItemsList.TodoItemsRepository;
import com.rito.todo.TodoItemsList.TodoItemsRepositoryImpl;
import com.rito.todo.data.TodoDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {
    @Singleton
    @Provides
    TodoItemsRepository provideTodoRepository(TodoDatabase todoDatabase) {
        return new TodoItemsRepositoryImpl(todoDatabase);
    }
}
