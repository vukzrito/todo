package com.rito.todo.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rito.todo.R;
import com.rito.todo.data.TodoDbHelper;
import com.rito.todo.model.TodoItem;

import java.util.List;

import static com.rito.todo.data.TodoContract.TodoEntry;

/**
 * Created by RVukela on 2016/10/25.
 */

public class TodoItemsListAdapter extends ArrayAdapter<TodoItem> {

    List<TodoItem> todoItems;
    Context context;


    @Override
    public int getCount() {
        return todoItems.size();
    }

    public TodoItemsListAdapter(Context context, List<TodoItem> items) {
        super(context, R.layout.item_row);

        this.context = context;
        this.todoItems = items;
    }

    @Nullable
    @Override
    public TodoItem getItem(int position) {
        return todoItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_row,parent,false);
        convertView.setOnClickListener(null);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.todo_item_title);
        TextView descTextView = (TextView) convertView.findViewById(R.id.todo_item_description);
        CheckBox itemCheckBox = (CheckBox) convertView.findViewById(R.id.todo_item_check_box);
        itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int isComplete=0;
                if(isChecked){
                    isComplete=1;
                }

                TodoDbHelper dbHelper= new TodoDbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long itemId = getItem(position).getId();
                ContentValues args = new ContentValues();
                args.put( TodoEntry.COLUMN_NAME_IS_COMPLETE, isComplete);
                db.update(TodoEntry.TABLE_NAME, args, TodoEntry._ID + "=" + itemId, null);
                db.close();

             // TodoItemsListAdapter.this.notifyDataSetChanged();

            }
        });

        titleTextView.setText(todoItems.get(position).getTitle());
                descTextView.setText(todoItems.get(position).getDescription());
        if(todoItems.get(position).isComplete()==1){
            itemCheckBox.setChecked(true);
        }else{
            itemCheckBox.setChecked(false);
        }

        return convertView;
    }


}


