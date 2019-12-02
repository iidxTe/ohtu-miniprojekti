package io.github.iidxTe.ohtu.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.iidxTe.ohtu.dao.BookmarkDao;
import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

@Service
public class BookmarkService {

    @Autowired
    BookmarkDao dao;
    
    private List<String> availableBookmarks;

    public BookmarkService() {
        this.availableBookmarks = new ArrayList<>();
        availableBookmarks.add("kirja");
    }

    public List<String> getAvailableBookmarks() {
        return this.availableBookmarks;
    }
    
    public void setDao(BookmarkDao dao) {
        this.dao = dao;
    }
        
    public Book createBook(User owner, String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        dao.add(owner, book);
        return book;
    }
    
    public void updateBook(int id) {
        Bookmark book = dao.getById(id);
        dao.update(book);
    }

    public List<Bookmark> listAll(User user) {
        return dao.getAll(user);
    }

}
