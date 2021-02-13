package com.scenario.todo.service;


import com.scenario.todo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User user);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}