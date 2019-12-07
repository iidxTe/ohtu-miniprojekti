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
    
    public Book getBookById(int id) {
        return (Book) dao.getBookmarkById(id);
    }
        
    public Book createBook(User owner, String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        dao.addBookmark(owner, book);
        return book;
    }
    
    public void updateBook(int id, boolean isRead) {
        Bookmark book = dao.getBookmarkById(id);
        book.setIsRead(isRead);
        dao.updateBookmark(book);
    }

    public List<Bookmark> listAllByUser(User user) {
        return dao.getAllBookmarksByUser(user);
    }
    
    public void deleteBookmark(int id, User user) {
        if ((user.getGroup() == null || user.getGroup().isEmpty()) && dao.isOwner(user.getId(), id) ) {
            //if user doesn't belong to any group, bookmark will be deleted permanently
            dao.deleteBookmark(id, true);
        } else {
            //if user belongs to group, bookmark is not deleted but hidden 
            dao.deleteBookmark(id, false);
        }
    }

}
