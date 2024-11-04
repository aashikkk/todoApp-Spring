package com.aashik.todorest.controller;

import com.aashik.todorest.dtos.TodoDto;
import com.aashik.todorest.entity.Priority;
import com.aashik.todorest.entity.Todo;
import com.aashik.todorest.entity.User;
import com.aashik.todorest.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoControllerTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private PagedResourcesAssembler<Todo> pagedResourcesAssembler;

    @InjectMocks
    private TodoController todoController;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("aaron@gmail.com");
        user.setPassword("123");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetTodos() {
        Page<Todo> todos = new PageImpl<>(Collections.singletonList(new Todo()));
        when(todoRepository.findAllByUser(any(User.class), any(PageRequest.class))).thenReturn(todos);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn((PagedModel) PagedModel.of(Collections.singletonList(EntityModel.of(new Todo()))));

        PagedModel<EntityModel<Todo>> result = todoController.getTodos(Optional.empty(), Optional.empty(), 0, 10);

        assertEquals(1, result.getContent().size());
        verify(todoRepository, times(1)).findAllByUser(any(User.class), any(PageRequest.class));
    }

    @Test
    void testSearchTodos() {
        Page<Todo> todos = new PageImpl<>(Collections.singletonList(new Todo()));
        when(todoRepository.searchTodos(any(User.class), anyString(), any(Priority.class), any(PageRequest.class))).thenReturn(todos);
        when(pagedResourcesAssembler.toModel(any(Page.class))).thenReturn((PagedModel) PagedModel.of(Collections.singletonList(EntityModel.of(new Todo()))));

        PagedModel<EntityModel<Todo>> result = todoController.searchTodos("keyword", Optional.empty(), 0, 10);

        assertEquals(1, result.getContent().size());
        verify(todoRepository, times(1)).searchTodos(any(User.class), anyString(), any(Priority.class), any(PageRequest.class));
    }

    @Test
    void testGetTodoById() {
        Todo todo = new Todo();
        todo.setUser(user);
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));

        ResponseEntity<Todo> response = todoController.getTodoById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(todoRepository, times(1)).findById(anyLong());
    }

    @Test
    void testAddTodo() {
        TodoDto todoDto = new TodoDto();
        todoDto.setTask("Test Task");
        todoDto.setPriority(Priority.HIGH);

        todoController.addTodo(todoDto);

        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testUpdateTodoById() {
        Todo todo = new Todo();
        todo.setUser(user);
        when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo));

        TodoDto todoDto = new TodoDto();
        todoDto.setTask("Updated Task");
        todoDto.setPriority(Priority.LOW);

        ResponseEntity<Todo> response = todoController.updateTodoById(1L, todoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todoDto.getTask(), response.getBody().getTask());
        assertEquals(todoDto.getPriority(), response.getBody().getPriority());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }
}