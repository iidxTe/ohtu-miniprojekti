package io.github.iidxTe.ohtu.model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public User(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getInt("id");
		this.name = resultSet.getString("name");
		this.displayName = resultSet.getString("displayName");
		this.group = resultSet.getString("readerGroup");
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
