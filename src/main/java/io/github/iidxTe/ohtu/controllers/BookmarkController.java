package io.github.iidxTe.ohtu.controllers;

import io.github.iidxTe.ohtu.domain.BookmarkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookmarkController {

    private BookmarkService service;

    public BookmarkController() {
        this.service = new BookmarkService();
    }
        
        
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", this.service.listAll());
        model.addAttribute("types", this.service.getAvailableBookmarks());
        return "index";
    }

    @PostMapping("/")
    public String create(@RequestParam String title, @RequestParam String author, @RequestParam String isbn, @RequestParam String type) {
        switch (type) {
            case "kirja":
                service.createBook(title, author, isbn);
                break;
            default:
        }
        return "redirect:/";
    }
    
    @PutMapping("/{id}")
    public String update(@RequestParam int id) {
                service.updateBook(id);
           
        return "redirect:/";
    }



}
