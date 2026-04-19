package com.gradetracker.dao;

import com.gradetracker.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLite implementation of the UserDao interface.
 * Handles user authentication using the database.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class SqliteUserDao implements UserDao {

  /**
   * Authenticates a user using username and password.
   * Username is checked case-insensitively and password is checked exactly.
   *
   * @param username the username entered by the user
   * @param password the password entered by the user
   * @return the matching User if credentials are valid, otherwise null
   */
  @Override
  public User authenticate(String username, String password) {
    String sql = """
        SELECT u.userId, u.userName, u.password, u.roleId, r.roleName
        FROM users u
        JOIN roles r ON u.roleId = r.roleId
        WHERE LOWER(u.userName) = LOWER(?) AND u.password = ?
        """;

    try (Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username);
      statement.setString(2, password);

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return new User(
              rs.getInt("userId"),
              rs.getString("userName"),
              rs.getString("password"),
              rs.getInt("roleId"),
              rs.getString("roleName")
          );
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to authenticate user.", e);
    }

    return null;
  }

  @Override
  public boolean usernameExists(String username) {
    String sql = "SELECT COUNT(*) FROM users WHERE LOWER(userName) = LOWER(?)";

    try (Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username);

      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0;
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to check username.", e);
    }

    return false;
  }

  @Override
  public void save(User user) {
    String sql = "INSERT INTO users (userName, password, roleId) VALUES (?, ?, ?)";

    try (Connection connection = DatabaseManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, user.getUsername());
      statement.setString(2, user.getPassword());
      statement.setInt(3, user.getRoleId());

      statement.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to save user.", e);
    }
  }
}
