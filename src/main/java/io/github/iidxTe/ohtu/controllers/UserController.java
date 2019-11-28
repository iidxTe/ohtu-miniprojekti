package io.github.iidxTe.ohtu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.iidxTe.ohtu.dao.UserDao;

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
    
    // We're processing login attempts with tools provided by Spring
    
    // TODO user settings
}
