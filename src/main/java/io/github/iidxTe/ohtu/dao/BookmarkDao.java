
package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.Bookmark;
import java.util.List;


public interface BookmarkDao {


    public List<Bookmark> getAll();

    public void add(Bookmark bookmark);
    
    public void update(Bookmark bookmark);
        
    public Bookmark getById(int id);


}
