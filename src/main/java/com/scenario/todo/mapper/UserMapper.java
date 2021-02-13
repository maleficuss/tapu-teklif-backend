package com.scenario.todo.mapper;

import com.scenario.todo.controller.rest.request.PostUser;
import com.scenario.todo.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User map(PostUser postUser);
}
