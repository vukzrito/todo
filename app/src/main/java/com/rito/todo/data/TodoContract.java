package com.rito.todo.data;

import android.provider.BaseColumns;



public final class TodoContract {
    private TodoContract(){

    }

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IS_COMPLETE= "is_complete";
    }

}
