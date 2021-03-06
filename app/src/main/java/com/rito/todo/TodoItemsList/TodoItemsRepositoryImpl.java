package com.rito.todo.TodoItemsList;

import com.rito.todo.data.TodoDatabase;
import com.rito.todo.data.TodoItem;

import java.util.List;

import javax.inject.Inject;


public class TodoItemsRepositoryImpl implements TodoItemsRepository {

    @Inject
    TodoDatabase database;

    public TodoItemsRepositoryImpl(TodoDatabase todoDatabase) {
        this.database = todoDatabase;
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
    public void deleteTodoItem(long itemId, final DeleteTodoItemCallback callback) {
        //TODO Implement DELETE
        database.deleteItem(itemId, new TodoDatabase.DeleteItemCallback() {
            @Override
            public void onItemDeleted() {
                callback.onTodoItemDeleted(null);
            }
        });
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
