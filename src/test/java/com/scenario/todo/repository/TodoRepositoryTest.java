package com.scenario.todo.repository;

import com.scenario.todo.entity.Todo;
import com.scenario.todo.entity.User;
import com.scenario.todo.projection.TodoWithoutUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_save_todo_with_already_saved_user(){
        User user = userRepository.getOne(10);
        Todo todo = new Todo();
        todo.setTitle("title deneme");
        todo.setDate(Date.from(Instant.now().plus(Period.ofDays(1))));
        todo.setUser(user);
        Todo createdTodo = todoRepository.save(todo);
        assertTrue(createdTodo.getId() > 0L);
        assertEquals(todo.getTitle(),createdTodo.getTitle());
        assertEquals(todo.getDate(),createdTodo.getDate());
    }

    @Test
    void should_find_by_id_and_user_id(){

        User user = new User();
        user.setId(10);

        Todo todo = new Todo();
        todo.setId(4);

        Todo searchedTodo = todoRepository.findByIdAndUserId(todo.getId(),user.getId(),Todo.class);
        assertNotNull(searchedTodo);
        assertEquals(user.getId(),searchedTodo.getUser().getId());
        assertEquals(todo.getId(),searchedTodo.getId());

        TodoWithoutUser todoWithoutUser = todoRepository.findByIdAndUserId(todo.getId(),user.getId(),TodoWithoutUser.class);
        assertNotNull(todoWithoutUser);
        assertEquals(todo.getId(),todoWithoutUser.getId());
    }

    @Test
    void should_not_find_by_id_and_user_id_and_return_null(){

        User user = new User();
        user.setId(5555);

        Todo todo = new Todo();
        todo.setId(1221212);

        Todo searchedTodo = todoRepository.findByIdAndUserId(todo.getId(),user.getId(),Todo.class);
        assertNull(searchedTodo);

        TodoWithoutUser todoWithoutUser = todoRepository.findByIdAndUserId(todo.getId(),user.getId(),TodoWithoutUser.class);
        assertNull(todoWithoutUser);
    }

    @Test
    void should_find_all_by_user_id_and_return_list(){

        User user = new User();
        user.setId(10);

        List<Todo> todoList = todoRepository.findAllByUserId(user.getId(),Todo.class);
        assertNotNull(todoList);
        assertTrue(todoList.size() > 0);
        todoList.forEach(todo -> assertEquals(user.getId(),todo.getUser().getId()));

        List<TodoWithoutUser> todoWithoutUserList = todoRepository.findAllByUserId(user.getId(),TodoWithoutUser.class);
        assertNotNull(todoWithoutUserList);
        assertTrue(todoWithoutUserList.size() > 0);
    }

    @Test
    void should_find_all_by_user_id_and_return_empty_list(){

        User user = new User();
        user.setId(21321312);

        List<Todo> todoList = todoRepository.findAllByUserId(user.getId(),Todo.class);
        assertEquals(todoList.size(), 0);

        List<TodoWithoutUser> todoWithoutUserList = todoRepository.findAllByUserId(user.getId(),TodoWithoutUser.class);
        assertEquals(todoWithoutUserList.size(), 0);
    }

    @Test
    void should_exists_by_id_and_user_id_return_true(){

        User user = new User();
        user.setId(10);

        Todo todo = new Todo();
        todo.setId(4);


        boolean exists = todoRepository.existsByIdAndUserId(todo.getId(),user.getId());
        assertTrue(exists);
    }

    @Test
    void should_exists_by_id_and_user_id_return_false(){
        User user = new User();
        user.setId(124124);

        Todo todo = new Todo();
        todo.setId(123123);

        boolean exists = todoRepository.existsByIdAndUserId(todo.getId(),user.getId());
        assertFalse(exists);
    }

    @Test
    void should_delete_by_id(){
        Todo todo = new Todo();
        todo.setId(4);
        Todo searchedTodo = todoRepository.findById(todo.getId()).orElse(null);
        assertNotNull(searchedTodo);
        assertEquals(todo.getId(),searchedTodo.getId());
        todoRepository.deleteById(searchedTodo.getId());
        searchedTodo = todoRepository.findById(searchedTodo.getId()).orElse(null);
        assertNull(searchedTodo);
    }

}