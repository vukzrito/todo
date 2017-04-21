package com.rito.todo.TodoItems;

import com.rito.todo.model.TodoItem;

import java.util.List;

/**
 * Created by RVukela on 2017/04/19.
 */

public interface TodoItemsRepository {
    interface LoadTodoItemsCallback {
        void onTodoItemsLoaded(List<TodoItem> itemList);
    }

    interface CreateTodoItemCallback {
        void onTodoItemCreated(TodoItem item);

    }

    interface DeleteTodoItemCallback {
        void onTodoItemDeleted(TodoItem item);

    }

    interface CompleteTodoItemCallback{
        void onTodoItemCompleted(TodoItem item);
    }

    interface  RevertTodoItemCallback{
        void onTodoItemReverted(TodoItem item);
    }

    void loadTodoItems(LoadTodoItemsCallback callback);
    void createTodoItem(TodoItem item,CreateTodoItemCallback callback);
    void deleteTodoItem(long itemId, DeleteTodoItemCallback callback);
    void completeTodoItem(long itemId, CompleteTodoItemCallback callback);
    void revertTodoItem(long itemId, RevertTodoItemCallback callback);
}
