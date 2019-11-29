
package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

import java.util.List;


public interface BookmarkDao {

    List<Bookmark> getAll(User user);

    void add(User user, Bookmark bookmark);
    
    void update(Bookmark bookmark);
        
    Bookmark getById(int id);

}
