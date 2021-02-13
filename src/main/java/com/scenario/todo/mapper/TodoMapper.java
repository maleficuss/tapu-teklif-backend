package com.scenario.todo.mapper;

import com.scenario.todo.controller.rest.request.PostTodo;
import com.scenario.todo.controller.rest.request.PutTodo;
import com.scenario.todo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TodoMapper {
    Todo map(PostTodo postTodo);
    void map(PutTodo putTodo, @MappingTarget Todo todo);
}
