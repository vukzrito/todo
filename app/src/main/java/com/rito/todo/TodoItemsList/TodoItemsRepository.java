package com.rito.todo.TodoItemsList;

import com.rito.todo.data.TodoItem;

import java.util.List;


public interface TodoItemsRepository {
    void loadTodoItems(LoadTodoItemsCallback callback);

    void createTodoItem(TodoItem item, CreateTodoItemCallback callback);

    void deleteTodoItem(long itemId, DeleteTodoItemCallback callback);

    void completeTodoItem(long itemId, CompleteTodoItemCallback callback);

    void revertTodoItem(long itemId, RevertTodoItemCallback callback);

    interface LoadTodoItemsCallback {
        void onTodoItemsLoaded(List<TodoItem> itemList);
    }

    interface CreateTodoItemCallback {
        void onTodoItemCreated(TodoItem item);

    }

    interface DeleteTodoItemCallback {
        void onTodoItemDeleted(TodoItem item);

    }

    interface CompleteTodoItemCallback {
        void onTodoItemCompleted(TodoItem item);
    }

    interface RevertTodoItemCallback {
        void onTodoItemReverted(TodoItem item);
    }
}
