package com.aashik.todorest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    private String task;

    @Column
    private boolean completed;

    @Column
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Todo(String task, boolean completed, Priority priority, User user) {
        this.task = task;
        this.completed = completed;
        this.priority = priority;
        this.user = user;
    }
}
