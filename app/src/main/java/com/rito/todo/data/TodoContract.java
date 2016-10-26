package com.rito.todo.data;

import android.provider.BaseColumns;

/**
 * Created by RVukela on 2016/10/26.
 */

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
