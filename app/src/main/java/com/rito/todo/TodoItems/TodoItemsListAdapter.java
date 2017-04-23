package com.rito.todo.todoItems;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rito.todo.R;
import com.rito.todo.data.TodoItem;

import java.util.List;

import static com.rito.todo.todoItems.TodoItemsActivity.TodoItemCheckedListener;


public class TodoItemsListAdapter extends RecyclerView.Adapter<TodoItemsListAdapter.ViewHolder> {

    private  TodoItemCheckedListener todoItemCheckedListener;
    private List<TodoItem> todoItems;
    //Context context;
    private ProgressBar progressBar;
    private TextView textViewProgressval;




    public TodoItemsListAdapter( List<TodoItem> items, TodoItemCheckedListener
            todoItemCheckedListener) {

        this.todoItems = items;
        this.todoItemCheckedListener = todoItemCheckedListener;
    }

    @Nullable
    /**@Override **/
    public TodoItem getItem(int position) {
       /* if(position==0){
            return  null;
        }*/
        return todoItems.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        TodoItem item = todoItems.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descTextView.setText(item.getDescription());
        if(item.isComplete()==TodoItem.ITEM_COMPLETED)
            holder.itemCheckBox.setChecked(true);

        holder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                TodoItem todoItem = getItem(holder.getAdapterPosition());
                if(isChecked){

                    todoItemCheckedListener.onItemChecked(todoItem);
                }else{

                    todoItemCheckedListener.onItemUnChecked(todoItem);
                }


                //calculateProgress();

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }






    public void updateData(List<TodoItem> todoItems){
        this.todoItems = todoItems;
        notifyDataSetChanged();
        //calculateProgress();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView ;
        TextView descTextView;
        CheckBox itemCheckBox ;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.todo_item_title);
            descTextView = (TextView) itemView.findViewById(R.id.todo_item_description);
            itemCheckBox = (CheckBox) itemView.findViewById(R.id.todo_item_check_box);


        }
    }
}


