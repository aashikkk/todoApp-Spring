package com.aashik.todorest.repository;

import com.aashik.todorest.entity.Priority;
import com.aashik.todorest.entity.Todo;
import com.aashik.todorest.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

     @Query("SELECT t FROM Todo t WHERE t.user = :user AND (t.task LIKE %:keyword% OR t.priority = :priority)")
     Page<Todo> searchTodos(@Param("user") User user, @Param("keyword") String keyword, @Param("priority") Priority priority, Pageable pageable);

     Page<Todo>  findAllByUser(User user, Pageable pageable);    public

     Page<Todo> findAllByUserAndCompleted(User user, boolean completed, Pageable pageable);

     Page<Todo> findAllByUserAndCompletedAndPriority(User user,boolean completed, Priority priority, Pageable pageable);

     Page<Todo> findAllByUserAndPriority(User user,Priority priority, Pageable pageable);

}
