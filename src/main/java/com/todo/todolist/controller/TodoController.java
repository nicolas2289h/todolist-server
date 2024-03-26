package com.todo.todolist.controller;

import com.todo.todolist.dto.TodoDto;
import com.todo.todolist.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@Tag(name = "Todo List", description = "Operaciones relacionadas al manejo de tareas.")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Obtiene todas las tareas almacenadas", description = "Muestra el listado de los tareas registradas en la base de datos")
    @GetMapping("/todos")
    public ResponseEntity<Object> getAlltodos() {
        return todoService.getAlltodos();
    }

    // DELETE
    @Operation(summary = "Elimina una tarea", description = "Eliminación de una tarea que haya sido o no completada")
    @DeleteMapping("/delete-todo/{id}")
    public ResponseEntity<String> deletetodo(@PathVariable Long id) {
        boolean deleted = todoService.deletetodo(id);
        if (deleted) {
            return ResponseEntity.ok("Tarea eliminada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarea no encontrada.");
        }
    }

    @Operation(summary = "Elimina las tareas completadas", description = "Eliminación de todas las tareas que hayan sido completadas")
    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllTodos() {
        boolean completedTodoList = todoService.deleteAllTodos();
        if (completedTodoList) {
            return ResponseEntity.ok("Listado de tareas completadas eliminado correctamente.");
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/todo")
    @Operation(summary = "Crea una nueva tarea", description = "Creación de una nueva tarea con titulo y descripcion de la misma.")
    public ResponseEntity<String> createtodo(@RequestBody @Valid TodoDto todoDTO) {
        todoService.addNewtodo(todoDTO);
        return new ResponseEntity<>("Nueva tarea creada", HttpStatus.OK);
    }

    @Operation(summary = "Actualiza una tarea", description = "Actualización de una tarea mediante el id y los parámetros para actualizar")
    @PutMapping("/update-todo/{id}")
    public ResponseEntity<String> updatetodo(@RequestBody @Valid TodoDto todoDTO, @PathVariable Long id) {
        return todoService.updatetodo(todoDTO, id);
    }
}

