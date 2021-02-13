package com.scenario.todo.mapper;

import com.scenario.todo.controller.rest.request.PostTodo;
import com.scenario.todo.controller.rest.request.PutTodo;
import com.scenario.todo.entity.Todo;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodoMapperTest {

    private TodoMapperImpl todoMapper = new TodoMapperImpl();

    @Test
    void should_map_postTodo_to_todo() {
        PostTodo postTodo = new PostTodo();
        postTodo.setTitle("Test Title");
        Todo todo = todoMapper.map(postTodo);
        assertEquals(postTodo.getTitle(),todo.getTitle());
    }

    @Test
    void should_map_and_replace_todo_from_putTodo() {
        PutTodo putTodo = new PutTodo();
        putTodo.setTitle("Test Title");

        Todo todo = new Todo();
        todo.setTitle("Replacing Title");
        todo.setDate(new Date());

        todoMapper.map(putTodo,todo);
        assertEquals(putTodo.getTitle(),todo.getTitle());
    }
}