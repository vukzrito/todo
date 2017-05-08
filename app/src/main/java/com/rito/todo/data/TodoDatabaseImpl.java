package com.rito.todo.data;


import android.content.Context;

public class TodoDatabaseImpl implements TodoDatabase {
    private TodoSQLiteDbHelper dbHelper;

    public TodoDatabaseImpl(Context context) {
        dbHelper = Injection.provideDataBaseHelper(context);
    }


    @Override
    public void retrieveCompletedItems(RetrieveItemsCallback callback) {

    }

    @Override
    public void retrieveAllItems(RetrieveItemsCallback callback) {
       callback.onItemsRetrieved( dbHelper.getAllTodoItems());
    }

    @Override
    public void addItem(TodoItem item, AddItemToDbCallback callback) {

        callback.onItemAddedToDb(dbHelper.insertTodoItem(item));
    }

    @Override
    public void completeItem(long itemId, ModifyItemStatusCallback callback) {
        callback.onItemCompleted(dbHelper.updateCompletionStatus(itemId,TodoItem.ITEM_COMPLETED ));
    }

    @Override
    public void revertItem(long itemId, ModifyItemStatusCallback callback) {
        callback.onItemReverted(dbHelper.updateCompletionStatus(itemId,TodoItem.ITEM_INCOMPLETE ));
    }
}
