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


    public BookmarkService() {
    }


    public void setDao(BookmarkDao dao) {
        this.dao = dao;
    }

    public Book getBookById(int id, int userId) {
        return (Book) dao.getBookmarkById(id, userId);
    }

    public Book createBook(User owner, String title, String author, String isbn) {
        Book book = new Book(title, author, isbn, owner.getDisplayName());
        dao.addBookmark(owner, book);
        return book;
    }

    public void updateBook(int userId, int bookId, boolean isRead) {
        Bookmark book = dao.getBookmarkById(bookId, userId);
        book.setIsRead(isRead);
        if (dao.isOwner(userId, bookId)) {
            dao.updateBookmark(userId, book);
        } else {
            dao.updateHasread(userId, book);
        }
    }

    public List<Bookmark> listAllByUser(User user) {
        return dao.getVisibleBookmarks(user);
    }

    public void deleteBookmark(int id, User user) {
        dao.deleteBookmark(id);
    }
}
