
package io.github.iidxTe.ohtu.model;


public class Book extends Bookmark {

    private String author;

    public Book(String title, String type, String author) {
        super(title, "book");
        this.author = author;
    }

}
