package com.rito.todo.todoItems;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rito.todo.R;
import com.rito.todo.data.TodoItem;

import java.util.List;



public class TodoItemsListAdapter extends ArrayAdapter<TodoItem> {

    private  TodoItemsActivity.TodoItemCheckedListener todoItemCheckedListener;
    List<TodoItem> todoItems;
    Context context;
    private ProgressBar progressBar;
    private TextView textViewProgressval;


    @Override
    public int getCount() {
        return todoItems.size();
    }

    public TodoItemsListAdapter(Context context, List<TodoItem> items,
                                TodoItemsActivity.TodoItemCheckedListener todoItemCheckedListener) {
        super(context, R.layout.item_row);
        this.context = context;
        this.todoItems = items;
        this.todoItemCheckedListener = todoItemCheckedListener;
    }

    @Nullable
    @Override
    public TodoItem getItem(int position) {
        if(position==0){
            return  null;
        }
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
        if(position==0){
           if(convertView==null){
               convertView = inflater.inflate(R.layout.todo_progress_layout,parent,false);

               textViewProgressval = (TextView) convertView.findViewById(R.id.todo_items_progress_value);
               progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
           }

        }else{
            convertView = inflater.inflate(R.layout.item_row,parent,false);
            convertView.setOnClickListener(null);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.todo_item_title);
            TextView descTextView = (TextView) convertView.findViewById(R.id.todo_item_description);
            CheckBox itemCheckBox = (CheckBox) convertView.findViewById(R.id.todo_item_check_box);
            itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                    TodoItem todoItem = getItem(position);
                    if(isChecked){

                        todoItemCheckedListener.onItemChecked(todoItem);
                    }else{

                        todoItemCheckedListener.onItemUnChecked(todoItem);
                    }


                    calculateProgress();

                }
            });


            titleTextView.setText(todoItems.get(position).getTitle());
            descTextView.setText(todoItems.get(position).getDescription());
            if(todoItems.get(position).isComplete()==1){
                itemCheckBox.setChecked(true);
            }else{
                itemCheckBox.setChecked(false);
            }
        }

        return convertView;
    }

    public void calculateProgress(){

        int nItems = todoItems.size()-1;
        double percentage = (double)calcCompletedItems()/ (double)nItems *100;
        if(textViewProgressval!=null){
            textViewProgressval.setText((int)percentage + " %");
            progressBar.setProgress((int)percentage);
        }


    }

    private int calcCompletedItems(){
        int nCompleted = 0;
        for(int i=1; i<todoItems.size(); i++){
            if(todoItems.get(i).isComplete() == TodoItem.ITEM_COMPLETED)
                nCompleted+=1;
        }

        return nCompleted;
    }

    public void updateData(List<TodoItem> todoItems){
        this.todoItems = todoItems;
        notifyDataSetChanged();
        calculateProgress();
    }


}


