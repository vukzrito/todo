package com.rito.todo;


import android.app.Application;

import com.rito.todo.data.DaggerDataComponent;
import com.rito.todo.data.DataComponent;

public class TodoApp extends Application {
    private DataComponent dataComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        dataComponent = DaggerDataComponent.create();
    }

    public DataComponent getDataComponent(){
        return  dataComponent;
    }
}
