
package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import java.util.ArrayList;
import java.util.List;


public class ListDao implements BookmarkDao {

    private List<Bookmark> list;

    public ListDao() {
        this.list = new ArrayList<>();
    }   

    @Override
    public List<Bookmark> getAll() {
        return list;
    }

    @Override
    public void add(Bookmark bookmark) {
        list.add(bookmark);
    }

    @Override
    public void update(Bookmark bookmark) {        
        int i = list.indexOf(bookmark);
        Bookmark book = list.get(i);
        book.setIsRead(!book.isRead());      
        list.set(i, book);
    }

    @Override
    public Bookmark getById(int id) {
        Bookmark book = null;
        
        for (Bookmark bookmark : list) {
            if (bookmark.getId() == id) {
                book = bookmark;
            }
        }
        
        return book;
    }

}
