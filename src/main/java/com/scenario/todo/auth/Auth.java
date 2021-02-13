package com.scenario.todo.auth;

import com.scenario.todo.entity.User;

public interface Auth {
    User getUser();
    void setUser(User user);
}
