package com.rito.todo.data;

import java.util.List;

/**
 * Created by RVukela on 2017/04/20.
 */

public interface TodoDatabase {
    interface AddItemToDbCallback{
        void onItemAddedToDb(TodoItem item);
        void onError(String errorMessage);
    }

    interface RetrieveItemsCallback {
        void onItemsRetrieved(List<TodoItem> todoItems);
        void onError(String errorMessage);
    }

    interface ModifyItemStatusCallback{
        void onItemCompleted(TodoItem item);
        void onItemReverted(TodoItem item);
        void onError(String errorMessage);
    }

    void retrieveCompletedItems(RetrieveItemsCallback callback);
    void retrieveAllItems(RetrieveItemsCallback callback);
    void addItem(TodoItem item, AddItemToDbCallback callback);
    void completeItem(long itemId, ModifyItemStatusCallback callback);
    void revertItem(long itemId, ModifyItemStatusCallback callback);
}
