package com.gradetracker.dao;

import com.gradetracker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert Mozzetti
 * created: 4/18/2026
 * Explanation: SQLite implementation of UserDao
 */
public class SqliteUserDao implements UserDao {

  @Override
  public User authenticate(String username, String password) {
    String sql = """
        SELECT u.userId, u.userName, u.password, r.roleName
        FROM users u
        JOIN roles r ON r.roleId = u.roleId
        WHERE u.userName = ? AND u.password = ?
        """;

    try (Connection conn = DatabaseManager.getInstance().getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      stmt.setString(2, password);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new User(
              rs.getInt("userId"),
              rs.getString("userName"),
              rs.getString("password"),
              rs.getString("roleName")
          );
        }
      }
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to authenticate user.", e);
    }

    return null;
  }

  @Override
  public boolean usernameExists(String username) {
    String sql = "SELECT COUNT(*) FROM users WHERE userName = ? COLLATE NOCASE";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next() && rs.getInt(1) > 0;
      }
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to check username availability.", e);
    }
  }

  @Override
  public void save(User user) {
    String sql = """
        INSERT INTO users (userName, password, roleId)
        SELECT ?, ?, roleId FROM roles WHERE roleName = ?
        """;

    try (Connection conn = DatabaseManager.getInstance().getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, user.getUserName());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getRoleName());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to save user.", e);
    }
  }

  /**
   * Returns all users in the database.
   *
   * @return a list of all users
   */
  @Override
  public ObservableList<User> getAllUsers() {
    ObservableList<User> users = FXCollections.observableArrayList();

    String request = """
        SELECT u.userId, u.userName, r.roleName 
        FROM users u 
        JOIN roles r ON u.roleId = r.roleId
    """;

    try (Connection connection = DatabaseManager.getInstance().getConnection();
         Statement statement = connection.createStatement();
         ResultSet response = statement.executeQuery(request)) {

      while (response.next()) {
        users.add(new User(
            response.getInt("userId"),
            response.getString("userName"),
            response.getString("roleName")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }
}