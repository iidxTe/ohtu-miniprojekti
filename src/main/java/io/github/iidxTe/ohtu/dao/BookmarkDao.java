
package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

import java.util.List;


public interface BookmarkDao {

    /**
     * Gets bookmarks owned by given user. They may additionally see bookmarks
     * of their group members, which can be gotten with
     * {@link #getVisibleBookmarks(User)}.
     * @param user User.
     * @return Own bookmarks.
     */
    List<Bookmark> getOwnedBookmarks(User user);

    /**
     * Gets all bookmarks visible to given user.
     * @param user User.
     * @return All their bookmarks, and bookmarks of their reader group.
     */
    List<Bookmark> getVisibleBookmarks(User user);

    void addBookmark(User user, Bookmark bookmark);

    void updateBookmark(int userId, Bookmark bookmark);

    Bookmark getBookmarkById(int id, int userId);

    void deleteBookmark(int bookId);

    /**
     * Checks if user with given id owns the bookmark with given id.
     * @param userId User id.
     * @param bookmarkId Bookmark id.
     * @return Whether the user owns the bookmark or not.
     */
    boolean isOwner(int userId, int bookmarkId);

    void updateHasread(int userId, Bookmark bookmark);

}
