package com.demo.controller;

import com.demo.model.Task;
import com.demo.model.User;
import com.demo.model.UserPrincipal;
import com.demo.service.TaskService;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getTodos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        List<Task> tasks = taskService.findByUser(user);
        model.addAttribute("username", username);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/create")
    public String createTask(@RequestParam String title, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        taskService.createTask(title, user);
        return "redirect:/tasks";
    }

//    @GetMapping("/{id}/delete")
//    public String deleteTask(@PathVariable int id) {
//        taskService.deleteTask(id);
//        return "redirect:/tasks";
//    }
//
//    @GetMapping("/{id}/toggle")
//    public String toggleTask(@PathVariable int id) {
//        taskService.toggleTask(id);
//        return "redirect:/tasks";
//    }
@GetMapping("/{id}/delete")
public String deleteTask(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
    taskService.deleteTask(id, userDetails.getUsername());
    return "redirect:/tasks";
}

    @GetMapping("/{id}/toggle")
    public String toggleTask(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        taskService.toggleTask(id, userDetails.getUsername());
        return "redirect:/tasks";
    }

}
