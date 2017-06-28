package com.rito.todo.TodoItemsList;

import com.rito.todo.data.TodoDatabase;
import com.rito.todo.data.TodoItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


public class TodoItemsPresenterTest {

    private static final List<TodoItem> TODO_ITEMS = new ArrayList<>(1);
    @Mock
    public static TodoItem todoItem;
    @Mock
    private TodoItemsRepository itemsRepository;
    @Mock
    private TodoItemsContract.View itemsViews;
    @Mock
    TodoDatabase todoDatabase;
    private TodoItemsPresenter presenter;
    @Captor
    private ArgumentCaptor<TodoItemsRepository.LoadTodoItemsCallback> loadItemsArgumentCaptor;
    @Captor
    private ArgumentCaptor<TodoItemsRepository.CreateTodoItemCallback> createItemArgumentCaptor;
    @Captor
    private ArgumentCaptor<TodoItemsRepository.RevertTodoItemCallback> revertItemArgumentCaptor;
    @Captor
    private ArgumentCaptor<TodoItemsRepository.CompleteTodoItemCallback> completeItemArgumentCaptor;
    @Captor
    private ArgumentCaptor<TodoItemsRepository.DeleteTodoItemCallback> deleteItemArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new TodoItemsPresenter(itemsRepository);
        presenter.setView(itemsViews);
        TODO_ITEMS.add(new TodoItem(10, "Test", "Desc", 0));
        todoItem = new TodoItem(2, "Test", "Desc", 0);
    }

    @Test
    public void markItemComplete() {
        presenter.markItemComplete(todoItem.getId());
        verify(itemsRepository).completeTodoItem(any(long.class), completeItemArgumentCaptor.capture());
        completeItemArgumentCaptor.getValue().onTodoItemCompleted(todoItem);
    }

    @Test
    public void markItemIncomplete() {
        presenter.markItemIncomplete(todoItem.getId());
        verify(itemsRepository).revertTodoItem(any(long.class), revertItemArgumentCaptor.capture());
        revertItemArgumentCaptor.getValue().onTodoItemReverted(todoItem);
    }

    @Test
    public void addNewItem() {
        presenter.addNewItem(todoItem);
        verify(itemsRepository).createTodoItem(any(TodoItem.class), createItemArgumentCaptor.capture());
        createItemArgumentCaptor.getValue().onTodoItemCreated(todoItem);
        verify(itemsViews).notifyTodoItemAdded();
    }

    @Test
    public void loadTodoItems() {
        presenter.loadTodoItems();
        verify(itemsRepository).loadTodoItems(loadItemsArgumentCaptor.capture());
        loadItemsArgumentCaptor.getValue().onTodoItemsLoaded(TODO_ITEMS);
        verify(itemsViews).showTodoItems(TODO_ITEMS);
    }

    @Test
    public void deleteTodoItem() {
        presenter.deleteTodoItem(todoItem.getId());
        verify(itemsRepository).deleteTodoItem(any(long.class), deleteItemArgumentCaptor.capture());
        deleteItemArgumentCaptor.getValue().onTodoItemDeleted(todoItem);
    }

}