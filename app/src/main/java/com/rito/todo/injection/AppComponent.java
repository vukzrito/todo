package com.rito.todo.injection;

import com.rito.todo.TodoItemsList.TodoItemsActivity;
import com.rito.todo.TodoItemsList.TodoItemsPresenter;
import com.rito.todo.TodoItemsList.TodoItemsRepositoryImpl;
import com.rito.todo.data.TodoDatabaseImpl;
import com.rito.todo.data.TodoSQLiteDbHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class, PersistenceModule.class})
public interface AppComponent {
    void inject(TodoItemsActivity target);

    void inject(TodoItemsPresenter target);

    void inject(TodoItemsRepositoryImpl target);

    void inject(TodoDatabaseImpl target);

    void inject(TodoSQLiteDbHelper target);
}
