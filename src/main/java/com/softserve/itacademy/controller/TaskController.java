package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    TaskServiceImpl taskService;
    @Autowired
    ToDoServiceImpl toDoService;

    @GetMapping("/create/todos/{todo_id}")
    public String create(@PathVariable(name="todo_id") Integer id, Model model) {
        try {
            Task task = new Task();
            task.setTodo(toDoService.readById(id));
            model.addAttribute("todo_id", id);
            model.addAttribute("task", task);
            return "create-task";
        } catch (NoSuchElementException e){
            model.addAttribute("errorMessage", "ToDo with id: " + id + " was not found");
            return "error-page";
        }
    }

    @PostMapping("/create/todos/{todo_id}")
    public String create(@ModelAttribute(name="task") Task task,
                         @PathVariable(name = "todo_id") Integer todoId) {
        taskService.create(task);
        System.out.println(task);
        return "redirect:/todos/" + todoId + "/tasks";
    }
//
//    @GetMapping("/{task_id}/update/todos/{todo_id}")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/{task_id}/update/todos/{todo_id}")
//    public String update(//add needed parameters) {
//       //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{task_id}/delete/todos/{todo_id}")
//    public String delete(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}
