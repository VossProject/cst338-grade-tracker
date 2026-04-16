package com.gradetracker.dao;

import com.gradetracker.model.User;

/**
 * Data Access Object (DAO) interface for User objects.
 * Defines methods for retrieving and authenticating users.
 * This allows different implementations (e.g., in-memory, database).
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public interface UserDao {

  /**
   * Authenticates a user based on username and password.
   *
   * @param username the username entered by the user
   * @param password the password entered by the user
   * @return the matching User if credentials are valid, otherwise null
   */
  User authenticate(String username, String password);

  boolean usernameExists(String username);

  void save(User user);
}
