
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
    
    /**
     * Checks if user with given id owns the bookmark with given id.
     * @param userId User id.
     * @param bookmarkId Bookmark id.
     * @return Whether the user owns the bookmark or not.
     */
    boolean isOwner(int userId, int bookmarkId);

}
