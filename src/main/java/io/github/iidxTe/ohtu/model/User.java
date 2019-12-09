package io.github.iidxTe.ohtu.model;

/**
 * User in our application.
 *
 */
public class User {

    /**
     * Unique id of the user.
     */
    private int id;

    /**
     * Username set on registration. Cannot be changed.
     */
    private final String name;

    /**
     * Display name set by the user. Should be set to {@link #name} initially.
     */
    private String displayName;

    private String group;

    public User(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
