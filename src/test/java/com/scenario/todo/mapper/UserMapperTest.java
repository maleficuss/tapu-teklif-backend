package com.scenario.todo.mapper;

import com.scenario.todo.controller.rest.request.PostUser;
import com.scenario.todo.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private UserMapperImpl userMapper = new UserMapperImpl();

    @Test
    void should_map_postUser_to_user(){
        PostUser postUser = new PostUser("test@test.com","testpassword");
        User user = userMapper.map(postUser);
        assertNotNull(user);
        assertEquals(postUser.getEmail(),user.getEmail());
        assertEquals(postUser.getPassword(),user.getPassword());
    }

}