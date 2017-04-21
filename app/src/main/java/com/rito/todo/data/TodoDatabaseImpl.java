package com.rito.todo.data;

/**
 * Created by RVukela on 2017/04/20.
 */

public class TodoDatabaseImpl implements TodoDatabase {
    private TodoSQLiteDbHelper dbHelper;

    public TodoDatabaseImpl(TodoSQLiteDbHelper dbHelper) {
        this.dbHelper = dbHelper;
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
