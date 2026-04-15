package com.gradetracker.dao;

import com.gradetracker.model.User;

public interface UserDao {

  User authenticate(String username, String password);

  boolean usernameExists(String username);

  void save(User user);
}
