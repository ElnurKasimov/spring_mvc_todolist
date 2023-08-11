package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.service.impl.StateServiceImpl;
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
    @Autowired
    StateServiceImpl stateService;

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
        task.setState(stateService.getByName("New"));
        taskService.create(task);
        System.out.println(task);
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable(name="task_id") Integer task_id,
                         @PathVariable(name="todo_id") Integer todo_id,
                         Model model) {
        try {
            Task taskToUpdate = taskService.readById(task_id);
            toDoService.readById(todo_id);
            List<State> stateList = stateService.getAll();
            if(stateList.isEmpty()) {
                State state = new State();
                state.setName("New");
                stateList.add(state);
            }
            model.addAttribute("taskToUpdate", taskToUpdate);
            model.addAttribute("todo_id", todo_id);
            model.addAttribute("states", stateList);
            return "update-task";
        } catch (NoSuchElementException e){
            model.addAttribute("errorMessage", "ToDo or Task with that id was not found");
            return "error-page";
        }
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update() {
       //ToDo
        return " ";
    }

    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete() {
        //ToDo
        return " ";
    }
}
