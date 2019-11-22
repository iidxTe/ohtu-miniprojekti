package io.github.iidxTe.ohtu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookmarkController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/")
    public String create(@RequestParam String name) {
        return "index";
    }

}
