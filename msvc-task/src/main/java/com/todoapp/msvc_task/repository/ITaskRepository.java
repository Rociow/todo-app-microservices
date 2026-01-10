package com.todoapp.msvc_task.repository;

import com.todoapp.msvc_task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);
}
