
package io.github.iidxTe.ohtu.domain;

import io.github.iidxTe.ohtu.dao.BookmarkDao;
import io.github.iidxTe.ohtu.dao.ListDao;
import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import java.util.List;


public class BookmarkService {
    
    BookmarkDao dao;

    public BookmarkService() {
        dao = new ListDao();
    }
           
    public void create(String title) {
        Bookmark book = new Book(title, "matti");
        dao.add(book);        
    }
    
    public List<Bookmark> listAll() {
        return dao.getAll();
    }
    
}
