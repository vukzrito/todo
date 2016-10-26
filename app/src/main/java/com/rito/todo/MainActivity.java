package com.rito.todo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.rito.todo.adapter.TodoItemsListAdapter;
import com.rito.todo.data.TodoContract;
import com.rito.todo.data.TodoDbHelper;
import com.rito.todo.model.TodoItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView todoListView;
    TodoItemsListAdapter adapter;
    ArrayList todoItems;
    TodoDbHelper dbHelper;
    Button btnAddNewItem;
    EditText inputNewItemTitle;
    TextView textViewProgressval;
    EditText inputNewItemDesc;
    ProgressBar progressBar;

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
        drawer.setDrawerListener(toggle);
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
                String desc=inputNewItemDesc.getText().toString();;
                TodoItem todoItem= new TodoItem(title,desc,0 );
                insertItem(todoItem);
                todoItems.add(todoItem);
                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "New todo item added", Toast.LENGTH_SHORT).show();
                inputNewItemTitle.setText("");
                inputNewItemDesc.setText("");
            }
        });
        todoListView = (ListView) findViewById(R.id.tasks_list);


        dbHelper = new TodoDbHelper(this);
        todoItems = getTodoItems();

        adapter = new TodoItemsListAdapter(this,todoItems);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onInvalidated() {
                super.onInvalidated();
              calculateProgress();
            }


        });
        todoListView.setAdapter(adapter);
        calculateProgress();

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
        int id = item.getItemId();

        


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void insertItem(TodoItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, item.getTitle());
        values.put(TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION, item.getDescription());
        values.put(TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE, item.isComplete());

        long newRowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);
    }

    private ArrayList<TodoItem> getTodoItems(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {TodoContract.TodoEntry._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TITLE,
                TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION,
                TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE,

        };


        String sortOrder =
                TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION + " DESC";

        Cursor resultsCursor = db.query(
                TodoContract.TodoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        ArrayList<TodoItem> itemsList= new ArrayList<>();

        try {
            while (resultsCursor.moveToNext()) {
                long itemId = resultsCursor.getLong(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID));
                String itemTitle =resultsCursor.getString(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TITLE));
                String itemDescription =resultsCursor.getString(resultsCursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DESCRIPTION));
                int isComplete = resultsCursor.getInt(resultsCursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_IS_COMPLETE));
                TodoItem item = new TodoItem(itemId,itemTitle,itemDescription,isComplete);
                Log.v("Item", item.toString());
                itemsList.add(item);
            }
        } finally {
            resultsCursor.close();
        }


        return itemsList;
    }
    public void calculateProgress(){
      int nCompletedItems= dbHelper.getCompletedCount();
      int nItems = todoItems.size();
        double percentage = (double)nCompletedItems/ (double)nItems *100;
        textViewProgressval.setText((int)percentage + " %");
        progressBar.setProgress((int)percentage);

    }
    private void refreshAdapter(){
        adapter.clear();
        adapter.addAll(getTodoItems());
        adapter.notifyDataSetChanged();
    }
}
