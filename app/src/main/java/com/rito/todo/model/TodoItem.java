package com.rito.todo.model;

/**
 * Created by RVukela on 2016/10/25.
 */

public class TodoItem {
    public long id;
    public String title;
    public String description;
    public int isComplete;

    public TodoItem() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int isComplete() {
        return isComplete;
    }

    public void setComplete(int complete) {
        isComplete = complete;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public TodoItem(String title, String description, int isComplete) {
        this.title = title;
        this.description = description;
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
      return "Title : " + title +"\n Description : "+description + "\n isComplete : "+ isComplete()  ;
    }

    public TodoItem(long id, String title, String description, int isComplete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isComplete = isComplete;
    }

}
