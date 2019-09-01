package com.webapp.controller;

import com.webapp.model.User;
import com.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class DefaultController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "forms/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "forms/register";
        }
        if (userService.isUserPresent(user.getEmail())) {
            model.addAttribute("exist", true);
            return "forms/register";
        }
        userService.createUser(user);
        return "success";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "forms/login";
    }
}
