
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
    public List<Bookmark> getAllBookmarksByUser(User user) {
        return list;
    }

    @Override
    public void addBookmark(User user, Bookmark bookmark) {
        list.add(bookmark);
    }

    @Override
    public void updateBookmark(Bookmark bookmark) {        
        
    }

    @Override
    public Bookmark getBookmarkById(int id) {
        Bookmark book = null;
        
        for (Bookmark bookmark : list) {
            if (bookmark.getId() == id) {
                book = bookmark;
            }
        }
        
        return book;
    }

    @Override
    public void deleteBookmark(int id, boolean permanent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isOwner(int userId, int bookmarkId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
