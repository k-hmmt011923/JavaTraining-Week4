package com.example.taskapp.controller;

import com.example.taskapp.dto.TaskForm;
import com.example.taskapp.entity.Task;
import com.example.taskapp.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TaskViewController {

    private final TaskService taskService;

    public TaskViewController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("taskForm", new TaskForm());
        model.addAttribute("isEdit", false);
        return "tasks/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("taskForm") TaskForm taskForm,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "tasks/form";
        }

        Task task = new Task(taskForm.getTitle(), taskForm.isCompleted());
        taskService.create(task);

        redirectAttributes.addFlashAttribute("successMessage", "タスクを登録しました");
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);

        TaskForm taskForm = new TaskForm(task.getTitle(), task.isCompleted());
        model.addAttribute("taskForm", taskForm);
        model.addAttribute("taskId", id);
        model.addAttribute("isEdit", true);

        return "tasks/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("taskForm") TaskForm taskForm,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("taskId", id);
            model.addAttribute("isEdit", true);
            return "tasks/form";
        }

        Task updatedTask = new Task(taskForm.getTitle(), taskForm.isCompleted());
        taskService.update(id, updatedTask);

        redirectAttributes.addFlashAttribute("successMessage", "タスクを更新しました");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "タスクを削除しました");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.toggleComplete(id);
        redirectAttributes.addFlashAttribute("successMessage", "完了状態を切り替えました");
        return "redirect:/tasks";
    }
}