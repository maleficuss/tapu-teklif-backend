package com.scenario.todo.repository;

import com.scenario.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
    <T> T findByIdAndUserId(int id, int userId,Class<T> type);
    <T> List<T> findAllByUserId(int userId, Class<T> type);
    boolean existsByIdAndUserId(int id, int userId);
    void deleteById(int id);
}
