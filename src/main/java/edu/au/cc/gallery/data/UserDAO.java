package edu.au.cc.gallery.data;

import java.util.List;

public interface UserDAO {
	/**
	 * @return return the (possibly empty) list of users
	 */
	List<User> getUsers() throws Exception;

	/**
	 * @return user with specified username or null if no such user
	 */
	User getUserByUsername(String username) throws Exception;

	/**
	 * Add a user to the database.
	 */
	void addUser(User u) throws Exception;

	/**
	 * Delete a user from the database.
	 */
	void deleteUser(String username) throws Exception;

	/**
	 * Modify a user in the database.
	 */
	void modifyUser(User u) throws Exception;
}
