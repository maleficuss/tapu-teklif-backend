package com.scenario.todo.controller.rest;

import com.scenario.todo.service.TodoService;
import com.scenario.todo.token_manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.Period;

@RestController
public class TestController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private TodoService todoService;


    @GetMapping("test")
    public void test(){
        System.out.println(Instant.now().plus(Period.ofDays(1)));
    }
}
