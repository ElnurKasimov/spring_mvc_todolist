package com.softserve.itacademy.controller;


import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {
    private  UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/create")
    public String getCreateUser(Model model) {
        model.addAttribute("user", new User());
        return "user/create-user";}

    @PostMapping("/create")
    public String postCreateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/create-user";
        }
        try{
            userService.create(user);
        } catch (ConstraintViolationException constraintViolationException) {
            constraintViolationException.printStackTrace();
        } catch (JDBCException jdbcException) {
            jdbcException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "todo-lists";
    }

//
//    @GetMapping("
//    ")
//    public String read(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/{id}/update")
//    public String update(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//
//    @GetMapping("/{id}/delete")
//    public String delete(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
//    @GetMapping("/all")
//    public String getAll(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
}
