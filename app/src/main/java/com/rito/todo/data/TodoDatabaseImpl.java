package com.rito.todo.data;


import javax.inject.Inject;

public class TodoDatabaseImpl implements TodoDatabase {
    @Inject
    TodoSQLiteDbHelper dbHelper;

    @Inject
    public TodoDatabaseImpl(TodoSQLiteDbHelper todoSQLiteDbHelper) {
        // dbHelper = Injection.provideDataBaseHelper();
        this.dbHelper = todoSQLiteDbHelper;
    }


    @Override
    public void retrieveCompletedItems(RetrieveItemsCallback callback) {

    }

    @Override
    public void retrieveAllItems(RetrieveItemsCallback callback) {
        callback.onItemsRetrieved(dbHelper.getAllTodoItems());
    }

    @Override
    public void addItem(TodoItem item, AddItemToDbCallback callback) {

        callback.onItemAddedToDb(dbHelper.insertTodoItem(item));
    }

    @Override
    public void completeItem(long itemId, ModifyItemStatusCallback callback) {
        callback.onItemCompleted(dbHelper.updateCompletionStatus(itemId, TodoItem.ITEM_COMPLETED));
    }

    @Override
    public void revertItem(long itemId, ModifyItemStatusCallback callback) {
        callback.onItemReverted(dbHelper.updateCompletionStatus(itemId, TodoItem.ITEM_INCOMPLETE));
    }

    @Override
    public void deleteItem(long itemId, DeleteItemCallback callback) {
        dbHelper.deleteItem(itemId);
        callback.onItemDeleted();
    }
}
