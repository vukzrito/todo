package com.rito.todo.todoItems;

import com.rito.todo.data.TodoItem;

import java.util.List;



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
