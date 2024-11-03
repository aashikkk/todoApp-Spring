package com.aashik.todorest.controller;

import com.aashik.todorest.dtos.TodoDto;
import com.aashik.todorest.entity.Priority;
import com.aashik.todorest.entity.Todo;
import com.aashik.todorest.repository.TodoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;


    @GetMapping("/todos")
    public List<Todo> getTodos(@RequestParam Optional<Boolean> completed, @RequestParam Optional<Priority> priority){
        if( completed.isPresent() && priority.isPresent()){
            return todoRepository.findAllByCompletedAndPriority(completed.get(), priority.get());
        } else if (completed.isPresent()) {
            return todoRepository.findAllByCompleted(completed.get());
        } else if (priority.isPresent()) {
            return todoRepository.findAllByPriority(priority.get());
        } else {
            return todoRepository.findAll();
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id){
        return todoRepository.findById(id)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos")
    public void addTodo(@Valid @RequestBody TodoDto todoDto){
        todoRepository.save(new Todo(todoDto.getTask(), false, todoDto.getPriority()));
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodoById(@PathVariable Long id, @Valid @RequestBody TodoDto todoDto) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Todo updatedTodo = todo.get();
        updatedTodo.setTask(todoDto.getTask());
        updatedTodo.setPriority(todoDto.getPriority());
        todoRepository.save(updatedTodo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/todos/{id}/mark_complete")
    public ResponseEntity<Todo> markCompleted(@PathVariable Long id){
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(true);
                    todoRepository.save(todo);
                    return ResponseEntity.ok().body(todo);
        })
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/todos/{id}/mark_incomplete")
    public ResponseEntity<Object> markIncomplete(@PathVariable Long id){
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setCompleted(false);
                    todoRepository.save(todo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteTodoById(@PathVariable Long id){
        return todoRepository.findById(id)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}