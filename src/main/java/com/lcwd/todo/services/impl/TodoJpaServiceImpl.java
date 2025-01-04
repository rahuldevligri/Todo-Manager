package com.lcwd.todo.services.impl;

import com.lcwd.todo.exceptions.ResourceNotFoundException;
import com.lcwd.todo.models.Todo;
import com.lcwd.todo.repository.TodoRepository;
import com.lcwd.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class TodoJpaServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    /**
     * save single todo at a time
     * @return todo
     */
    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    /**
     * get all todos
     * @return todos
     */
    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * get single todo by todoId
     * @param todoId
     * @return todo
     * @throws ParseException
     */
    @Override
    public Optional<Todo> getTodo(int todoId) throws ParseException {
        return Optional.ofNullable(todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("todo not found with given id", HttpStatus.NOT_FOUND)));
    }

    /**
     * update todo by id
     * @param todoId
     * @param todo
     * @return todo
     */
    @Override
    public Todo updateTodo(int todoId, Todo todo) {
       Todo todo1 = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("todo not found with given id", HttpStatus.NOT_FOUND));
       todo1.setTitle(todo.getTitle());
       todo1.setContent(todo.getContent());
       todo1.setStatus(todo.getStatus());
       todo1.setToDoDate(todo.getToDoDate());
       return todoRepository.save(todo1);
    }

    /**
     * delete todo by id
     * @param todoId
     */
    @Override
    public void deleteTodo(int todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("todo not found with given id", HttpStatus.NOT_FOUND));
        todoRepository.delete(todo);

    }
}
