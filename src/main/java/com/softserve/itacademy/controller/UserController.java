package com.softserve.itacademy.controller;


import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private  final UserService userService;
    private final RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/create")
    public String getCreateUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        return "user/create-user";}

    @PostMapping("/create")
    public String postCreateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAll());
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
        return "redirect:/todos/create/users/" + user.getId();
    }


    @GetMapping("/")
    public String getEmpty(Model model) {
        model.addAttribute("users", userService.getAll());
        model.addAttribute("roles", roleService.getAll());
        return "user/users-list";
    }

    @GetMapping("/{id}/update")
    public String getUpdate(@PathVariable(name="id") Long id, Model model) {
        User user = new User();
        try{
            user = userService.readById(id);
        } catch (ConstraintViolationException constraintViolationException) {
        constraintViolationException.printStackTrace();
    } catch (JDBCException jdbcException) {
        jdbcException.printStackTrace();
    } catch (Exception exception) {
        exception.printStackTrace();
    }
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAll());
        return "user/update-user";
    }

    @PostMapping("/{id}/update")
    public String postUpdate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        logger.info("Model content: {}", model.asMap());
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAll());
            return "user/update-user";
        }
       try {
            userService.update(user);
        } catch (DataIntegrityViolationException duplicateException) {
            duplicateException.printStackTrace();
            String duplicateEmail = "User with this email exist already. Email must be unique";
            model.addAttribute("duplicateEmail", duplicateEmail);
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAll());
            return "user/update-user";
        } catch (ConstraintViolationException constraintViolationException) {
            constraintViolationException.printStackTrace();
        } catch (JDBCException jdbcException) {
            jdbcException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("users", userService.getAll());
        return "home";
    }

    @GetMapping("/{id}/delete")
    public String getDelete(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        try{
            userService.delete(user.getId());
        } catch (ConstraintViolationException constraintViolationException) {
            constraintViolationException.printStackTrace();
        } catch (JDBCException jdbcException) {
            jdbcException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        model.addAttribute("users", userService.getAll());
        return "home";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user/users-list";
    }
}
