package io.github.iidxTe.ohtu.controllers;

import io.github.iidxTe.ohtu.dao.UserDao;
import io.github.iidxTe.ohtu.domain.BookmarkService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkService service;
    
    @Autowired
    private UserDao userDao;

    public BookmarkController() {
        this.service = new BookmarkService();
    }
        
        
    @GetMapping("/")
    public String home(Model model, Principal login) {
        model.addAttribute("books", this.service.listAll(userDao.getUser(login.getName())));
        model.addAttribute("types", this.service.getAvailableBookmarks());
        return "index";
    }

    @PostMapping("/")
    public String create(Principal login, @RequestParam String title, @RequestParam String author, @RequestParam String isbn, @RequestParam String type) {
        switch (type) {
            case "kirja":
                service.createBook(userDao.getUser(login.getName()), title, author, isbn);
                break;
            default:
        }
        return "redirect:/";
    }
    
    @GetMapping("/editBookmark/{id}")
    public String editBookmarkForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("editableBook", service.getBookById(id));
        return "editBookmark";
    }
    
    @PostMapping("/editBookmark/{id}") 
    public String editBookmark(@PathVariable("id") int id, @RequestParam(value="isRead", required=false) Boolean isRead) {
        if (isRead == null) {
            isRead = false;
        }
        service.updateBook(id, isRead);
        return "redirect:/";
    }

}
