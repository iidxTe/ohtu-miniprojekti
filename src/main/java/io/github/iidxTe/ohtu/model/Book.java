
package io.github.iidxTe.ohtu.model;


public class Book extends Bookmark {
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        super(title, "book");
        this.author = author;
        this.isbn = isbn;
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

}
