package com.example.orchestrator.repository;

import com.example.orchestrator.task.Task;
import com.example.orchestrator.task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository  extends JpaRepository<Task, Long>{
    List<Task> findByStatus(TaskStatus status);
    Optional<Task> findFirstByStatus(TaskStatus taskStatus);
}
