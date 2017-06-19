package com.rito.todo;


import android.app.Application;

import com.rito.todo.TodoItemsList.TodoItemsRepository;
import com.rito.todo.TodoItemsList.TodoItemsrepositoryImpl;
import com.rito.todo.data.TodoDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    TodoItemsRepository provideRepository(TodoDatabase todoDatabase) {
        return new TodoItemsrepositoryImpl(todoDatabase);
    }
}
