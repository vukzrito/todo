package com.rito.todo.todoItems;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rito.todo.R;
import com.rito.todo.data.TodoDatabaseImpl;
import com.rito.todo.data.TodoItem;
import com.rito.todo.data.TodoSQLiteDbHelper;

import java.util.ArrayList;
import java.util.List;

public class TodoItemsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TodoItemsContract.View {

    ListView todoListView;
    TodoItemsListAdapter adapter;
    Button btnAddNewItem;
    EditText inputNewItemTitle;
    TextView textViewProgressval;
    EditText inputNewItemDesc;
    ProgressBar progressBar;
    private TodoItemsContract.UserActionsListener userActionsListener;
    private TodoItemCheckedListener todoItemCheckedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        textViewProgressval = (TextView) findViewById(R.id.todo_items_progress_value);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        inputNewItemTitle = (EditText) findViewById(R.id.text_input_new_item_title);
        inputNewItemTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.length()>0){
                        inputNewItemDesc.setVisibility(View.VISIBLE);
                    }else{
                        inputNewItemDesc.setVisibility(View.GONE);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNewItemDesc = (EditText) findViewById(R.id.text_input_new_item_desc);
        btnAddNewItem = (Button) findViewById(R.id.btn_add_new_item);
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= inputNewItemTitle.getText().toString();
                if(title.isEmpty()){
                    return;
                }
                String desc=inputNewItemDesc.getText().toString();
                TodoItem todoItem= new TodoItem(title,desc,0 );
                userActionsListener.addNewItem(todoItem);
                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), R.string.message_new_item_added, Toast.LENGTH_SHORT).show();
                inputNewItemTitle.setText("");
                inputNewItemDesc.setText("");
            }
        });
        todoItemCheckedListener = new TodoItemCheckedListener() {
            @Override
            public void onItemChecked(TodoItem item) {
                userActionsListener.markItemComplete(item.getId());
            }

            @Override
            public void onItemUnChecked(TodoItem item) {
                userActionsListener.markItemIncomplete(item.getId());
            }
        };
        todoListView = (ListView) findViewById(R.id.tasks_list);
        adapter = new TodoItemsListAdapter(this,new ArrayList<TodoItem>(1), todoItemCheckedListener);
        todoListView.setAdapter(adapter);
       userActionsListener = new TodoItemsPresenter(this, new TodoItemsRepositoryImpl(
               new TodoDatabaseImpl(new TodoSQLiteDbHelper(this))));


    }

    @Override
    protected void onStart() {
        super.onStart();
        userActionsListener.loadTodoItems();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void showTodoItems(List<TodoItem> todoItemList) {
        adapter.updateData(todoItemList);
    }

    @Override
    public void notifyTodoItemAdded() {
        Snackbar.make(this.todoListView.getRootView(), "New todo item added ",
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyTodoItemDeleted() {
        Snackbar.make(this.todoListView.getRootView(), R.string.message_item_deleted,
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyTodoItemUpdated() {
        Snackbar.make(this.todoListView.getRootView(), R.string.message_item_updated,
                Snackbar.LENGTH_LONG).show();
    }

    public interface TodoItemCheckedListener{
        void onItemChecked(TodoItem item);
        void onItemUnChecked(TodoItem item);
    }
}
