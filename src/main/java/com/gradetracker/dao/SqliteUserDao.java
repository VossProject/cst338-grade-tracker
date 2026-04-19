package com.gradetracker.dao;

import com.gradetracker.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getRoleName());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to save user.", e);
    }
  }
}