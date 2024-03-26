package com.todo.todolist.service;

import com.todo.todolist.dto.TodoDto;
import com.todo.todolist.entity.Todo;
import com.todo.todolist.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public ResponseEntity<Object> getAlltodos() {
        List<Todo> listtodos = todoRepository.findAll();
        return new ResponseEntity<>(listtodos, HttpStatus.OK);
    }

    public boolean deletetodo(Long id) {
        Optional<Todo> todoFound = todoRepository.findById(id);
        if (todoFound.isPresent()) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteAllTodos(){
        List<Todo> listCompletedTodos = todoRepository.findByEstado(true);
        if(!listCompletedTodos.isEmpty()){
            todoRepository.deleteAll(listCompletedTodos);
            return true;
        }
        return false;
    }

    public ResponseEntity<String> addNewtodo(TodoDto todoDTO) {
        Todo todo = new Todo();
        todo.setTitulo(todoDTO.getTitulo());
        todo.setDescripcion(todoDTO.getDescripcion());
        todo.setEstado(false);

        todoRepository.save(todo);
        return new ResponseEntity<>("Nueva tarea agregada", HttpStatus.OK);
    }

    public ResponseEntity<String> updatetodo(TodoDto todoDTO, Long id) {
        Optional<Todo> optionaltodo = todoRepository.findById(id);

        if (optionaltodo.isPresent()) {
            Todo updatedtodo = optionaltodo.get();

            updatedtodo.setTitulo(todoDTO.getTitulo());
            updatedtodo.setDescripcion(todoDTO.getDescripcion());
            updatedtodo.setEstado(todoDTO.isEstado());

            todoRepository.save(updatedtodo);
            return new ResponseEntity<>("Tarea actualizada.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Tarea NO encontrado.", HttpStatus.BAD_REQUEST);
    }
}