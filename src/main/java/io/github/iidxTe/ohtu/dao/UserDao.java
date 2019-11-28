package io.github.iidxTe.ohtu.dao;

import io.github.iidxTe.ohtu.model.User;

public interface UserDao {

	/**
	 * Gets or creates an user with given name.
	 * @param name User name.
	 * @return An user.
	 */
	User getOrCreateUser(String name);
	
	/**
	 * Stores update to data of given user.
	 * @param user User that has been updated.
	 */
	void updateUser(User user);
}
