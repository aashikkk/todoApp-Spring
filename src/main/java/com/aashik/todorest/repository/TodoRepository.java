package com.aashik.todorest.repository;

import com.aashik.todorest.entity.Priority;
import com.aashik.todorest.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    public List<Todo> findAllByCompleted(boolean completed);

    public List<Todo> findAllByCompletedAndPriority(boolean completed, Priority priority);

    public List<Todo> findAllByPriority(Priority priority);

}
