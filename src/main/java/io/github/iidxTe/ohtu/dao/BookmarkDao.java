
package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

import java.util.List;


public interface BookmarkDao {

    List<Bookmark> getAllBookmarksByUser(User user);

    void addBookmark(User user, Bookmark bookmark);
    
    void updateBookmark(Bookmark bookmark);
        
    Bookmark getBookmarkById(int id);
    
    void deleteBookmark(int bookId, boolean permanent);
    
    boolean validateOwner(int userId, int bookmarkId);

}
