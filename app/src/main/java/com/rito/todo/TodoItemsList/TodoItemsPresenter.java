package com.rito.todo.TodoItemsList;

import android.content.Context;

import com.rito.todo.data.TodoItem;
import com.rito.todo.injection.TodoApplication;

import java.util.List;

import javax.inject.Inject;


public class TodoItemsPresenter implements TodoItemsContract.UserActionsListener {
    private TodoItemsContract.View view;
    @Inject
    TodoItemsRepository repository;


    public TodoItemsPresenter(Context context) {
        ((TodoApplication)context).getAppComponent().inject(this);
    }

    @Override
    public void setView(TodoItemsContract.View view) {
        this.view = view;
    }

    @Override
    public void markItemComplete(long itemId) {
        repository.completeTodoItem(itemId, new TodoItemsRepository.CompleteTodoItemCallback() {
            @Override
            public void onTodoItemCompleted(TodoItem item) {
                loadTodoItems();
            }
        });
        view.notifyTodoItemUpdated();
    }

    @Override
    public void markItemIncomplete(long itemId) {
        repository.revertTodoItem(itemId, new TodoItemsRepository.RevertTodoItemCallback() {
            @Override
            public void onTodoItemReverted(TodoItem item) {
                loadTodoItems();
            }
        });
        view.notifyTodoItemUpdated();
    }

    @Override
    public void addNewItem(TodoItem item) {
        repository.createTodoItem(item, new TodoItemsRepository.CreateTodoItemCallback() {
            @Override
            public void onTodoItemCreated(TodoItem item) {
                loadTodoItems();
                view.notifyTodoItemAdded();
            }
        });
    }

    @Override
    public void loadTodoItems() {
        repository.loadTodoItems(new TodoItemsRepository.LoadTodoItemsCallback() {
            @Override
            public void onTodoItemsLoaded(List<TodoItem> itemList) {
                view.showTodoItems(itemList);
            }
        });
    }

    @Override
    public void deleteTodoItem(long itemId) {
        repository.deleteTodoItem(itemId, new TodoItemsRepository.DeleteTodoItemCallback() {
            @Override
            public void onTodoItemDeleted(TodoItem item) {
                loadTodoItems();
                view.notifyTodoItemDeleted();
            }
        });
    }
}
