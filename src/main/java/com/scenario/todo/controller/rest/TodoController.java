package com.scenario.todo.controller.rest;

import com.scenario.todo.auth.Auth;
import com.scenario.todo.controller.rest.request.PostTodo;
import com.scenario.todo.controller.rest.request.PutTodo;
import com.scenario.todo.entity.Todo;
import com.scenario.todo.exception.TodoNotFoundException;
import com.scenario.todo.mapper.TodoMapper;
import com.scenario.todo.projection.TodoWithoutUser;
import com.scenario.todo.rest_response.RestResponse;
import com.scenario.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TodoMapper todoMapper;
    private final Auth auth;

    @PostMapping(value = "todos", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Void> save(Authentication authentication, @Valid PostTodo postTodo){
        Todo todo = todoMapper.map(postTodo);
        todo.setUser(auth.getUser());
        Todo createdTodo = todoService.save(todo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "todos/{id}")
    public ResponseEntity<RestResponse<TodoWithoutUser>> findById(@PathVariable("id") int id){
        TodoWithoutUser todo = todoService.findByIdAndUserId(id,auth.getUser().getId(),TodoWithoutUser.class);
        if (todo == null) {
            throw new TodoNotFoundException("id : "+id);
        }

        RestResponse<TodoWithoutUser> restResponse = RestResponse.<TodoWithoutUser>builder()
                .status(HttpStatus.OK.value())
                .data(todo)
                .build();

        return ResponseEntity.ok(restResponse);
    }

    @PutMapping(value = "todos/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id, @Valid PutTodo putTodo){
        Todo todo = todoService.findByIdAndUserId(id,auth.getUser().getId(),Todo.class);
        if (todo == null) {
            throw new TodoNotFoundException("id : "+id);
        }

        todoMapper.map(putTodo, todo);

        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "todos")
    public ResponseEntity<RestResponse<List<TodoWithoutUser>>> findAll(){
        List<TodoWithoutUser> todoList = todoService.findAllByUserId(auth.getUser().getId(),TodoWithoutUser.class);

        RestResponse<List<TodoWithoutUser>> restResponse = RestResponse.<List<TodoWithoutUser>>builder()
                .status(HttpStatus.OK.value())
                .data(todoList)
                .build();

        return ResponseEntity.ok(restResponse);
    }

    @DeleteMapping(value = "todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        if (!todoService.existsByIdAndUserId(id,auth.getUser().getId())) {
            throw new TodoNotFoundException("id : "+id);
        }

        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
