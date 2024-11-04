package com.aashik.todorest.controller;

import com.aashik.todorest.dtos.TodoDto;
import com.aashik.todorest.entity.Priority;
import com.aashik.todorest.entity.Todo;
import com.aashik.todorest.entity.User;
import com.aashik.todorest.repository.TodoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    private User getAuthenticateUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        return (User) authentication.getPrincipal();
    }


    // Sort using priority and completed
    @GetMapping("/todos")
    public List<Todo> getTodos(@RequestParam Optional<Boolean> completed,
                               @RequestParam Optional<Priority> priority) {
        User user = getAuthenticateUser();
        if( completed.isPresent() && priority.isPresent()){
            return todoRepository.findAllByUserAndCompletedAndPriority(user, completed.get(), priority.get());
        } else if (completed.isPresent()) {
            return todoRepository.findAllByUserAndCompleted(user, completed.get());
        } else if (priority.isPresent()) {
            return todoRepository.findAllByUserAndPriority(user, priority.get());
        } else {
            return todoRepository.findAllByUser(user);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id){
        User user = getAuthenticateUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return todoRepository.findById(id)
                .filter(todo -> todo.getUser().equals(user))
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todos")
    public void addTodo(@Valid @RequestBody TodoDto todoDto){
        User user = getAuthenticateUser();
        todoRepository.save(new Todo(todoDto.getTask(), false, todoDto.getPriority(), user));
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todo> updateTodoById(@PathVariable Long id, @Valid @RequestBody TodoDto todoDto) {
        User user = getAuthenticateUser();
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty() || !todo.get().getUser().equals(user)){
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
        User user = getAuthenticateUser();
        return todoRepository.findById(id)
                .filter(todo -> todo.getUser().equals(user))
                .map(todo -> {
                    todo.setCompleted(true);
                    todoRepository.save(todo);
                    return ResponseEntity.ok().body(todo);
        })
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/todos/{id}/mark_incomplete")
    public ResponseEntity<Object> markIncomplete(@PathVariable Long id){
        User user = getAuthenticateUser();
        return todoRepository.findById(id)
                .filter(todo -> todo.getUser().equals(user))
                .map(todo -> {
                    todo.setCompleted(false);
                    todoRepository.save(todo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteTodoById(@PathVariable Long id){
        User user = getAuthenticateUser();
        return todoRepository.findById(id)
                .filter(todo -> todo.getUser().equals(user))
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}


//@GetMapping("/todos")
//public Page<Todo> getTodos(@RequestParam Optional<Boolean> completed,
//                           @RequestParam Optional<Priority> priority,
//                           @RequestParam int page,
//                           @RequestParam int size){
//    Pageable pageable = PageRequest.of(page,size);
//    if( completed.isPresent() && priority.isPresent()){
//        return todoRepository.findAllByCompletedAndPriority(completed.get(), priority.get(), pageable);
//    } else if (completed.isPresent()) {
//        return todoRepository.findAllByCompleted(completed.get(), pageable);
//    } else if (priority.isPresent()) {
//        return todoRepository.findAllByPriority(priority.get(), pageable);
//    } else {
//        return todoRepository.findAll(pageable);
//    }
//}