package com.aashik.todorest.dtos;

import com.aashik.todorest.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TodoDto {

    @NotBlank
    private String task;

    private Priority priority;
}
