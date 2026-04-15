package com.gradetracker.dao;

import com.gradetracker.model.User;
import java.util.ArrayList;
import java.util.List;

public class InMemoryUserDao implements UserDao {

  private final List<User> users = new ArrayList<>();

  public InMemoryUserDao() {
    users.add(new User(1, "admin", "admin123", "Admin"));
    users.add(new User(2, "teacher1", "teach123", "Teacher"));
    users.add(new User(3, "student1", "stud1234", "Student"));
  }

  @Override
  public User authenticate(String username, String password) {
    for (User user : users) {
      if (user.getUsername().equalsIgnoreCase(username)
          && user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

  @Override
  public boolean usernameExists(String username) {
    for (User user : users) {
      if (user.getUsername().equalsIgnoreCase(username)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void save(User user) {
    users.add(user);
  }
}
