package com.example.taskapp.service;

import com.example.taskapp.entity.Task;
import com.example.taskapp.exception.TaskNotFoundException;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Long id, Task updatedTask) {
        Task existingTask = findById(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setCompleted(updatedTask.isCompleted());
        return taskRepository.save(existingTask);
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    public Task toggleComplete(Long id) {
        Task task = findById(id);
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }
}