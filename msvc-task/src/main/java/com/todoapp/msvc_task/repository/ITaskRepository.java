package com.todoapp.msvc_task.repository;

import com.todoapp.msvc_task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {

}
