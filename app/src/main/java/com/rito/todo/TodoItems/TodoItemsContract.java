package com.rito.todo.TodoItems;

import com.rito.todo.model.TodoItem;

import java.util.List;

/**
 * Created by RVukela on 2017/04/19.
 */

public interface TodoItemsContract {
    interface View{
        void showTodoItems(List<TodoItem> todoItemList);

    }

    interface UserActionsListener{
        void markItemComplete(long itemId);
        void markItemIncomplete(long itemId);
        void addNewItem(TodoItem item);
        void deleteTodoItem(long itemId);
    }
}
