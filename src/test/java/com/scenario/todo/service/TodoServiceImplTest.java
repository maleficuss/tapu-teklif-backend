package com.scenario.todo.service;

import com.scenario.todo.entity.Todo;
import com.scenario.todo.entity.User;
import com.scenario.todo.projection.TodoWithoutUser;
import com.scenario.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;

    private User user;

    private List<Todo> todoList = new ArrayList<>();

    @Mock
    private TodoWithoutUser todoWithoutUser;

    @Mock
    private List<TodoWithoutUser> todoWithoutUserList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setPassword("testpassword");


        todo = new Todo();
        todo.setUser(user);
        todo.setId(2);
        todo.setTitle("Todo Title 1");
        todo.setDate(new Date());

        Todo todo2 = new Todo();
        todo2.setUser(user);
        todo2.setId(2);
        todo2.setTitle("Todo Title 2");
        todo2.setDate(new Date());


        Todo todo3 = new Todo();
        todo3.setUser(user);
        todo3.setId(2);
        todo3.setTitle("Todo Title 3");
        todo3.setDate(new Date());

        todoList.add(todo);
        todoList.add(todo2);
        todoList.add(todo3);
    }

    @Test
    void when_save_it_should_return_todo() {
        when(todoRepository.save(todo)).thenReturn(todo);
        Todo createdTodo = todoService.save(todo);
        assertNotNull(createdTodo);
        assertEquals(todo.getId(),createdTodo.getId());
        assertEquals(todo.getTitle(),createdTodo.getTitle());
        assertEquals(todo.getDate(), createdTodo.getDate());
        assertEquals(user, createdTodo.getUser());
    }

    @Test
    void when_find_by_id_and_user_id_exits_return_user(){
        when(todoRepository.findByIdAndUserId(todo.getId(),todo.getUser().getId(),Todo.class)).thenReturn(todo);
        Todo searchedTodo = todoService.findByIdAndUserId(todo.getId(),todo.getUser().getId(),Todo.class);
        assertNotNull(searchedTodo);
        assertEquals(todo.getId(),searchedTodo.getId());
        assertEquals(todo.getUser().getId(),searchedTodo.getUser().getId());

        /* Testing The Spring Data Projection Returns From Service*/
        when(todoRepository.findByIdAndUserId(todo.getId(),todo.getUser().getId(), TodoWithoutUser.class)).thenReturn(todoWithoutUser);
        TodoWithoutUser searchedTodoWithoutUser = todoService.findByIdAndUserId(todo.getId(),todo.getUser().getId(),TodoWithoutUser.class);

        when(todoWithoutUser.getId()).thenReturn(todo.getId());
        when(todoWithoutUser.getTitle()).thenReturn(todo.getTitle());
        when(todoWithoutUser.getDate()).thenReturn(todo.getDate());

        assertEquals(todo.getId(),todoWithoutUser.getId());
        assertEquals(todo.getTitle(),todoWithoutUser.getTitle());
        assertEquals(todo.getDate(),todoWithoutUser.getDate());
    }


    @Test
    void when_find_by_id_and_user_id_exits_return_null(){
        when(todoRepository.findByIdAndUserId(todo.getId(),todo.getUser().getId(),Todo.class)).thenReturn(null);
        Todo searchedTodo = todoService.findByIdAndUserId(todo.getId(),todo.getUser().getId(),Todo.class);
        assertNull(searchedTodo);
        /* Testing The Spring Data Projection Returns From Service*/
        when(todoRepository.findByIdAndUserId(todo.getId(),todo.getUser().getId(), TodoWithoutUser.class)).thenReturn(null);
        TodoWithoutUser searchedTodoWithoutUser = todoService.findByIdAndUserId(todo.getId(),todo.getUser().getId(),TodoWithoutUser.class);
        assertNull(searchedTodoWithoutUser);
    }


    @Test
    void when_find_by_all_by_user_id_return_list(){
        when(todoRepository.findAllByUserId(todo.getUser().getId(),Todo.class)).thenReturn(todoList);
        List<Todo> searchedTodoList = todoService.findAllByUserId(todo.getUser().getId(),Todo.class);
        assertTrue(searchedTodoList.size() > 0);
        assertEquals(searchedTodoList,todoList);

        /* Testing The Spring Data Projection Returns From Service*/
        when(todoRepository.findAllByUserId(todo.getUser().getId(),TodoWithoutUser.class)).thenReturn(todoWithoutUserList);
        List<TodoWithoutUser> searchedTodoWithoutUserList = todoService.findAllByUserId(todo.getUser().getId(),TodoWithoutUser.class);
        when(searchedTodoWithoutUserList.size()).thenReturn(todoList.size());

        assertTrue(searchedTodoWithoutUserList.size() > 0);
        assertEquals(todoList.size(), searchedTodoWithoutUserList.size());
        assertEquals(searchedTodoWithoutUserList,todoWithoutUserList);
    }


    @Test
    void when_find_by_all_by_user_id_return_empty_list(){
        when(todoRepository.findAllByUserId(todo.getUser().getId(),Todo.class)).thenReturn(new ArrayList<>());
        List<Todo> searchedTodoList = todoService.findAllByUserId(todo.getUser().getId(),Todo.class);
        assertEquals(searchedTodoList.size(), 0);

        /* Testing The Spring Data Projection Returns From Service*/
        when(todoRepository.findAllByUserId(todo.getUser().getId(),TodoWithoutUser.class)).thenReturn(new ArrayList<>());
        List<TodoWithoutUser> searchedTodoWithoutUserList = todoService.findAllByUserId(todo.getUser().getId(),TodoWithoutUser.class);
        assertEquals(searchedTodoWithoutUserList.size(), 0);
    }

    @Test
    void when_find_by_id_and_user_id_exits_return_true(){
        when(todoRepository.existsByIdAndUserId(todo.getId(),todo.getUser().getId())).thenReturn(true);
        boolean exists = todoService.existsByIdAndUserId(todo.getId(),todo.getUser().getId());
        assertTrue(exists);
    }


    @Test
    void when_find_by_id_and_user_id_exits_return_false(){
        when(todoRepository.existsByIdAndUserId(todo.getId(),todo.getUser().getId())).thenReturn(false);
        boolean exists = todoService.existsByIdAndUserId(todo.getId(),todo.getUser().getId());
        assertFalse(exists);
    }

    @Test
    void when_delete_by_id_does_not_throw(){
        assertDoesNotThrow(() -> todoService.deleteById(todo.getId()));
    }

}