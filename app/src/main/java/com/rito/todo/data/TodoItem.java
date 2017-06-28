package com.rito.todo.data;


public class TodoItem {
    public static final int ITEM_COMPLETED = 1;
    public static final int ITEM_INCOMPLETE = 0;
    public long id;
    public String title;
    public String description;
    public int isComplete;

    public TodoItem() {

    }

    public TodoItem(String title, String description, int isComplete) {
        this.title = title;
        this.description = description;
        this.isComplete = isComplete;
    }

    public TodoItem(long id, String title, String description, int isComplete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isComplete = isComplete;
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

    @Override
    public String toString() {
        return "Title : " + title + "\n Description : " + description + "\n isComplete : " + isComplete();
    }

}
