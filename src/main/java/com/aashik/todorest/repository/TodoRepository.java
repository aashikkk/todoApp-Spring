package com.aashik.todorest.repository;

import com.aashik.todorest.entity.Priority;
import com.aashik.todorest.entity.Todo;
import com.aashik.todorest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

     List<Todo>  findAllByUser(User user);    public

     List<Todo> findAllByUserAndCompleted(User user, boolean completed);

     List<Todo> findAllByUserAndCompletedAndPriority(User user,boolean completed, Priority priority);

     List<Todo> findAllByUserAndPriority(User user,Priority priority);

}
