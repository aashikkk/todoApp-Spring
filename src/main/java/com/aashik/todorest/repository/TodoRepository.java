package com.aashik.todorest.repository;

import com.aashik.todorest.entity.Priority;
import com.aashik.todorest.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    public Page<Todo> findAllByCompleted(boolean completed, Pageable pageable);

    public Page<Todo> findAllByCompletedAndPriority(boolean completed, Priority priority, Pageable pageable);

    public Page<Todo> findAllByPriority(Priority priority, Pageable pageable);

}
