
package io.github.iidxTe.ohtu.model;


public abstract class Bookmark {

    private String title;
    private String type;

    public Bookmark(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return title + " " + type;
    }        

}
