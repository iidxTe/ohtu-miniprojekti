
package io.github.iidxTe.ohtu.model;

import java.util.ArrayList;
import java.util.List;


public abstract class Bookmark {

    private String title;
    private String type;
    private List<String> tags;
    private String comments;

    public Bookmark(String title, String type) {
        this.title = title;
        this.type = type;
        this.tags = new ArrayList<>();
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
