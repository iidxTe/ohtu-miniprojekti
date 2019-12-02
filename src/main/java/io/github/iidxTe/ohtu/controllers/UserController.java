package io.github.iidxTe.ohtu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.iidxTe.ohtu.dao.UserDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Registration, logging in/out and user settings.
 *
 */
@Controller
public class UserController {

    @Autowired
    private UserDao dao;

    @GetMapping("/register")
    public String registerForm(Model model) {
        return "register";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }

    @PostMapping("/register")
    public String register(Model model, @RequestParam String username, @RequestParam String password) {
        if (dao.getUser(username) == null) {
            dao.createUser(username, password);
        }
        return "redirect:login"; // Allow newly created user to login
    }

    // We're processing login attempts with tools provided by Spring
    // TODO user settings
}
