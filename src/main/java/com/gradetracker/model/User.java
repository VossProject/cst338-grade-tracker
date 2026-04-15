package com.gradetracker.model;

/**
 * Represents a user in the Grade Tracker system.
 * A user has a username, password, and role (Admin, Teacher, or Student).
 * This model is used for authentication and role-based navigation.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class User {

  private int userId;
  private String username;
  private String password;
  private String roleName;

  public User(int userId, String username, String password, String roleName) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.roleName = roleName;
  }

  public int getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getRoleName() {
    return roleName;
  }
}
