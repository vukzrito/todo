package com.rito.todo.TodoItems;

import com.rito.todo.model.TodoItem;

/**
 * Created by RVukela on 2017/04/19.
 */

public class TodoItemsPresenter implements TodoItemsContract.UserActionsListener {
    private TodoItemsContract.View view;
    private TodoItemsRepository repository;

    public TodoItemsPresenter(TodoItemsContract.View view, TodoItemsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void markItemComplete(long itemId) {

    }

    @Override
    public void markItemIncomplete(long itemId) {

    }

    @Override
    public void addNewItem(TodoItem item) {

    }

    @Override
    public void deleteTodoItem(long itemId) {

    }
}
