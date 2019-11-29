
package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

import java.util.ArrayList;
import java.util.List;


public class ListDao implements BookmarkDao {

    private List<Bookmark> list;

    public ListDao() {
        this.list = new ArrayList<>();
    }   

    @Override
    public List<Bookmark> getAll(User user) {
        return list;
    }

    @Override
    public void add(User user, Bookmark bookmark) {
        list.add(bookmark);
    }

    @Override
    public void update(Bookmark bookmark) {        
        
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
