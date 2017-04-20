package com.rito.todo.TodoItems;

import com.rito.todo.data.TodoDatabase;
import com.rito.todo.model.TodoItem;

/**
 * Created by RVukela on 2017/04/19.
 */

public class TodoItemsrepositoryImpl implements TodoItemsRepository {
    private TodoDatabase database;
    public TodoItemsrepositoryImpl(TodoDatabase database) {
        this.database = database;
    }

    @Override
    public void loadTodoItems(final LoadTodoItemsCallback callback) {

    }

    @Override
    public void createTodoItem(TodoItem item, final CreateTodoItemCallback callback) {
        database.addItem(item, new TodoDatabase.AddItemToDbCallback() {
            @Override
            public void onItemAddedToDb(TodoItem item) {
                callback.onTodoItemCreated(item);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void deleteTodoItem(long itemId, DeleteTodoItemCallback callback) {
        //TODO Implement DELETE
    }

    @Override
    public void completeTodoItem(long itemId, final CompleteTodoItemCallback callback) {
        database.completeItem(itemId, new TodoDatabase.ModifyItemStatusCallback() {
            @Override
            public void onItemCompleted(TodoItem item) {
                callback.onTodoItemCompleted(item);
            }

            @Override
            public void onItemReverted(TodoItem item) {

            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
