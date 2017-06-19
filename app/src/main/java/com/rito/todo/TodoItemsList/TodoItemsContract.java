package com.rito.todo.TodoItemsList;

import com.rito.todo.data.TodoItem;

import java.util.List;


public interface TodoItemsContract {
    interface View {
        void showTodoItems(List<TodoItem> todoItemList);

        void notifyTodoItemAdded();

        void notifyTodoItemDeleted();

        void notifyTodoItemUpdated();

    }

    interface UserActionsListener {
        void setView(View view);

        void markItemComplete(long itemId);

        void markItemIncomplete(long itemId);

        void addNewItem(TodoItem item);

        void loadTodoItems();

        void deleteTodoItem(long itemId);
    }
}
