package com.scenario.todo.service;

import com.scenario.todo.entity.Todo;

import java.util.List;

public interface TodoService {
    Todo save(Todo todo);
    <T> T findByIdAndUserId(int id, int userId,Class<T> type);
    <T> List<T> findAllByUserId(int userId, Class<T> type);
    boolean existsByIdAndUserId(int id, int userId);
    void deleteById(int id);
}
