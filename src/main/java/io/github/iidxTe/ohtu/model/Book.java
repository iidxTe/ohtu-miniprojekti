package io.github.iidxTe.ohtu.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Book extends Bookmark {

    private String author;
    private String isbn;
    private String creator;

    public Book(String title, String author, String isbn, String creator) {
        super(title);
        this.author = author;
        this.isbn = isbn;
        this.creator = creator;
    }
    
    public Book(ResultSet resultSet) throws SQLException {
    	super(resultSet);
    	
    	this.author = resultSet.getString("author");
    	this.isbn = resultSet.getString("isbn");
    	this.creator = resultSet.getString("creator");
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }        

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return super.toString() + " " + author + " " + isbn;
    }

    @Override
    public String getType() {
        return "book";
    }

}
