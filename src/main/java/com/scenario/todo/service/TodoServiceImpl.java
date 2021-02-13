package com.scenario.todo.service;

import com.scenario.todo.entity.Todo;
import com.scenario.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository repository;

    @Override
    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public <T> T findByIdAndUserId(int id, int userId, Class<T> type) {
        return repository.findByIdAndUserId(id,userId,type);
    }

    @Override
    public <T> List<T> findAllByUserId(int userId, Class<T> type) {
        return repository.findAllByUserId(userId,type);
    }

    @Override
    public boolean existsByIdAndUserId(int id, int userId) {
        return repository.existsByIdAndUserId(id,userId);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
