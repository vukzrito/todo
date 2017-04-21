package com.rito.todo.TodoItems;

import com.rito.todo.model.TodoItem;

import java.util.List;

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
        repository.completeTodoItem(itemId, new TodoItemsRepository.CompleteTodoItemCallback() {
            @Override
            public void onTodoItemCompleted(TodoItem item) {

            }
        });
    }

    @Override
    public void markItemIncomplete(long itemId) {

    }

    @Override
    public void addNewItem(TodoItem item) {
        repository.createTodoItem(item, new TodoItemsRepository.CreateTodoItemCallback() {
            @Override
            public void onTodoItemCreated(TodoItem item) {

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

            }
        });
    }
}
