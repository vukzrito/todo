package com.rito.todo.data;

import java.util.List;


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

    interface DeleteItemCallback{
        void onItemDeleted();
    }

    void retrieveCompletedItems(RetrieveItemsCallback callback);
    void retrieveAllItems(RetrieveItemsCallback callback);
    void addItem(TodoItem item, AddItemToDbCallback callback);
    void completeItem(long itemId, ModifyItemStatusCallback callback);
    void revertItem(long itemId, ModifyItemStatusCallback callback);
    void deleteItem(long itemId, DeleteItemCallback callback);
}
