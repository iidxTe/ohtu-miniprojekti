package io.github.iidxTe.ohtu.domain;

import io.github.iidxTe.ohtu.dao.BookmarkDao;
import io.github.iidxTe.ohtu.dao.ListDao;
import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import java.util.ArrayList;
import java.util.List;

public class BookmarkService {

    BookmarkDao dao;
    private List<String> availableBookmarks;

    public BookmarkService() {
        dao = new ListDao();
        this.availableBookmarks = new ArrayList<>();
        availableBookmarks.add("kirja");
    }

    public List<String> getAvailableBookmarks() {
        return this.availableBookmarks;
    }
    
    public void setDao(BookmarkDao dao) {
        this.dao = dao;
    }
        
    public Book createBook(String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        book.setId((int) (Math.random() * 1000000000));
        dao.add(book);
        return book;
    }
    
    public void updateBook(Book book) {
        dao.update(book);
    }

    public List<Bookmark> listAll() {
        return dao.getAll();
    }

}
