package io.github.iidxTe.ohtu.dao;

import java.util.List;

import io.github.iidxTe.ohtu.model.User;

public interface UserDao {

	/**
	 * Gets or creates an user with given name.
	 * @param name User name.
	 * @return An user.
	 */
	User getUser(String name);
	
	/**
	 * Gets all users that are in given group.
	 * @param group Group name.
	 * @return List of users, possibly empty.
	 */
	List<User> getUsersByGroup(String group);
	
	/**
	 * Creates a new user.
	 * @param name User name.
	 * @param password Password.
	 * @return A newly created user.
	 */
	User createUser(String name, String password);
	
	/**
	 * Stores update to data of given user.
	 * @param user User that has been updated.
	 */
	void updateUser(User user);
}
