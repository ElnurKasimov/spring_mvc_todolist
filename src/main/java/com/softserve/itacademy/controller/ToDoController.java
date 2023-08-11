package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.impl.TaskServiceImpl;
import com.softserve.itacademy.service.impl.ToDoServiceImpl;
import com.softserve.itacademy.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/todos")
public class ToDoController {
    @Autowired
    ToDoServiceImpl toDoService;
    @Autowired
    TaskServiceImpl taskService;
    @Autowired
    UserServiceImpl userService;


//    @GetMapping("/create/users/{owner_id}")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/create/users/{owner_id}")
//    public String create(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
    @GetMapping("/{id}/tasks")
    public String read(@PathVariable(name = "id") Integer id, Model model) {
        try {
            model.addAttribute("todo", toDoService.readById(id));
            model.addAttribute("taskList", taskService.getByTodoId(id).stream()
                    .sorted(Comparator.comparing(Task::getId))
                    .collect(Collectors.toList()));

            List<User> collaborators = userService.getCollaboratorByTodoId(id).stream()
                    .sorted(Comparator.comparing(User::getId))
                    .collect(Collectors.toList());
            model.addAttribute("users", userService.getAll().stream()
                    .filter(user -> !collaborators.contains(user))
                    .collect(Collectors.toList()));
            model.addAttribute("collaborators", collaborators);
            return "todo-tasks";
        } catch (NoSuchElementException e){
            model.addAttribute("errorMessage", "ToDo with id: " + id + " was not found");
            return "error-page";
        }
    }
//
//    @GetMapping("/{todo_id}/update/users/{owner_id}")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @PostMapping("/{todo_id}/update/users/{owner_id}")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{todo_id}/delete/users/{owner_id}")
//    public String delete(//add needed parameters) {
//                         // ToDo
//        return " ";
//    }
//
//    @GetMapping("/all/users/{user_id}")
//    public String getAll(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{id}/add")
//    public String addCollaborator(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{id}/remove")
//    public String removeCollaborator(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}
