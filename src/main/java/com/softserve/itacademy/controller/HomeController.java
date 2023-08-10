package com.softserve.itacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(Model model) {
        // Добавление данных в модель, если необходимо
        return "home"; // Возвращает имя шаблона (без расширения .html)
    }



}
