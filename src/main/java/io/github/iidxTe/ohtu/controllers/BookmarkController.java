package io.github.iidxTe.ohtu.controllers;

import io.github.iidxTe.ohtu.domain.BookmarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookmarkController {

    private BookmarkService service;

    public BookmarkController() {
        this.service = new BookmarkService();
    }
        
        
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/")
    public String create(@RequestParam String name) {
        service.create(name);
        return "index";
    }

}
