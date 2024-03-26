package com.todo.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    @NotBlank(message = "El titulo es obligatorio.") // NOTBLANK PARA STRINGS
    private String titulo;
    @NotBlank(message = "La descripcion es obligatorio.")
    private String descripcion;
    private boolean estado;
}
