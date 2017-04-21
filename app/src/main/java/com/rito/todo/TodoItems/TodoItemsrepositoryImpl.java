package com.rito.todo.TodoItems;

import com.rito.todo.data.TodoDatabase;
import com.rito.todo.model.TodoItem;

import java.util.List;

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
        database.retrieveAllItems(new TodoDatabase.RetrieveItemsCallback() {
            @Override
            public void onItemsRetrieved(List<TodoItem> todoItems) {
                callback.onTodoItemsLoaded(todoItems);
            }

            @Override
            public void onError(String errorMessage) {
                //TODO Implement error handling
            }
        });
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
                //TODO Implement error handling
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

    @Override
    public void revertTodoItem(long itemId, final RevertTodoItemCallback callback) {
        database.revertItem(itemId, new TodoDatabase.ModifyItemStatusCallback() {
            @Override
            public void onItemCompleted(TodoItem item) {

            }

            @Override
            public void onItemReverted(TodoItem item) {
                callback.onTodoItemReverted(item);
            }

            @Override
            public void onError(String errorMessage) {
                
            }
        });
    }
}
