
package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ListBookmarkDao implements BookmarkDao {

    private List<Bookmark> list;

    public ListBookmarkDao() {
        this.list = new ArrayList<>();
    }   

    @Override
    public List<Bookmark> getOwnedBookmarks(User user) {
        return list;
    }

    @Override
    public void addBookmark(User user, Bookmark bookmark) {
        list.add(bookmark);
    }

    @Override
    public void updateBookmark(int userId, Bookmark bookmark) {        
        
    }

    @Override
    public Bookmark getBookmarkById(int id, int userId) {
        Bookmark book = null;
        
        for (Bookmark bookmark : list) {
            if (bookmark.getId() == id) {
                book = bookmark;
            }
        }
        
        return book;
    }

    @Override
    public List<Bookmark> getVisibleBookmarks(User user) {
        return list; // No multiuser support
    }

    @Override
    public void deleteBookmark(int bookId, boolean permanent) {
        Iterator<Bookmark> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == bookId) {
                it.remove();
                return;
            }
        }
    }

    @Override
    public boolean isOwner(int userId, int bookmarkId) {
        return true; // No multiuser support
    }

    @Override
    public void updateHasread(int userId, Bookmark bookmark) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
