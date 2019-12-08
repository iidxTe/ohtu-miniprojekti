
package io.github.iidxTe.ohtu.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Bookmark {
    
    /**
     * Unique id of this bookmark.
     */
    private int id;

    private String title;
    
    private boolean isRead;
    
    private boolean onList;

    public Bookmark(String title) {
        this.title = title;
        this.isRead = false;
    }
    
    public Bookmark(ResultSet resultSet) throws SQLException {
    	this.id = resultSet.getInt("id");
    	this.title = resultSet.getString("title");
    	this.isRead = resultSet.getBoolean("hasRead");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public abstract String getType();
    
    public boolean isRead() {
        return isRead;
    }
    
    public void setIsRead(boolean read) {
        this.isRead = read;
    }
    
    public boolean onList() {
        return onList;
    }
    
    public void setOnList(boolean onlist) {
        this.onList = onlist;
    }

    @Override
    public String toString() {
        return title + " " + getType();
    }        

}
