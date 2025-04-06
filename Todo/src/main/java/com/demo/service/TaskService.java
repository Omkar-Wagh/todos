package com.demo.service;

import com.demo.model.Task;
import com.demo.model.User;
import com.demo.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepo repo;

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public void createTask(String title, User user) {
        Task task = new Task();
        task.setTitle(title);
        task.setUser(user);
        task.setCompleted(false);
        repo.save(task);
    }

//
//    public void deleteTask(int id,String username) {
//        repo.deleteById(id);
//    }
//
//    public void toggleTask(int id,String username) {
//        Task task = repo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id"));
//                task.setCompleted(!task.isCompleted());
//        repo.save(task);
//    }

    public void deleteTask(int taskId, String username) throws AccessDeniedException {
        Task task = repo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not allowed to delete this task!");
        }

        repo.delete(task);
    }

    public void toggleTask(int taskId, String username) throws AccessDeniedException {
        Task task = repo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not allowed to modify this task!");
        }

        task.setCompleted(!task.isCompleted());
        repo.save(task);
    }


    public List<Task> findByUser(User user) {
        return repo.findByUser(user);
    }
}
