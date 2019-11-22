
package io.github.iidxTe.ohtu.dao;

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

}
