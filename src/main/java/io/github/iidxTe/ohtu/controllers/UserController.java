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
        if (dao.getUser(username) != null) {
            model.addAttribute("error", "käyttäjätunnus on jo olemassa");
            return "redirect:register?error";
        }
        if (username.length() < 4 || password.length() < 4) {
            model.addAttribute("error", "liian lyhyt käyttäjänimi tai salasana");
            return "redirect:register?error2";
        }
        dao.createUser(username, password);
        return "redirect:login"; // Allow newly created user to login
    }

    // We're processing login attempts with tools provided by Spring
    

    @GetMapping("/settings")
    public String settingsForm(Model model) {
        return "settings";
    }

    /* Tämä vielä ihan kesken:
    @PostMapping("/settings")
    public String settings(Model model, @RequestParam String username, @RequestParam String password) {

        dao.updateUser(User user);
        return "redirect:index";
    } */
}
