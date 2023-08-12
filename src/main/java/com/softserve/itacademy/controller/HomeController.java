package com.softserve.itacademy.controller;

import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserService userService;

    public HomeController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHome(Model model) {
            model.addAttribute("users", userService.getAll());
        return "home";
    }

    @GetMapping("/")
    public String getBlank(Model model) {
        model.addAttribute("users", userService.getAll());

        return "home";
    }

}
