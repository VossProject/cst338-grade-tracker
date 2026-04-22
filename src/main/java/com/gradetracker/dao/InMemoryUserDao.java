//package com.gradetracker.dao;
//
//import com.gradetracker.model.User;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * In-memory implementation of the UserDao interface.
// * Stores users in a temporary list for testing and development purposes.
// * This avoids the need for a database during early development.
// *
// * @author Harvey Duran
// * @since 04/24/26
// */
//public class InMemoryUserDao implements UserDao {
//
//  private final List<User> users = new ArrayList<>();
//
//  // Initializes the DAO with sample users for testing login functionality.
//  public InMemoryUserDao() {
//    users.add(new User(1, "admin", "admin123", "Admin"));
//    users.add(new User(2, "teacher1", "teach123", "Teacher"));
//    users.add(new User(3, "student1", "stud1234", "Student"));
//  }
//
//  /**
//   * Searches the in-memory user list for matching credentials.
//   *
//   * @param username the username to search for
//   * @param password the password to match
//   * @return the User if found, otherwise null
//   */
//  @Override
//  public User authenticate(String username, String password) {
//    for (User user : users) {
//      if (user.getUserName().equalsIgnoreCase(username)
//          && user.getPassword().equals(password)) {
//        return user;
//      }
//    }
//    return null;
//  }
//
//  @Override
//  public boolean usernameExists(String username) {
//    for (User user : users) {
//      if (user.getUserName().equalsIgnoreCase(username)) {
//        return true;
//      }
//    }
//    return false;
//  }
//
//  @Override
//  public void save(User user) {
//    users.add(user);
//  }
//
//  @Override
//  public ObservableList<User> getAllUsers() {
//    // Return a snapshot so callers cannot mutate internal state accidentally.
//    return FXCollections.observableArrayList(users);
//  }
//
//}
