package com.gradetracker.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the Database creation
 *
 * @author Olga Bradford
 * @since 4/14/2026
 */
class DatabaseManagerTest {
  private static DatabaseManager manager;

  @BeforeEach
  void setUp() {
    manager = DatabaseManager.getInstance();
  }

  @Test
  void getInstance() {
    DatabaseManager instance1 = DatabaseManager.getInstance();
    DatabaseManager instance2 = DatabaseManager.getInstance();
    assertNotNull(instance1);
    assertSame(instance1, instance2, "Should be the same memory address");
  }

  @Test
  void getConnection() throws SQLException {
    try (Connection connection = manager.getConnection()) {
      assertNotNull(connection, "Connection should not be null");
      assertFalse(connection.isClosed(), "Connection should be open");
    }
  }

  @Test
  void testRolesPreseed() throws SQLException {
    String roles = "SELECT COUNT(*) FROM roles";
    try (Connection connection = manager.getConnection();
         Statement statement = connection.createStatement();
         ResultSet res = statement.executeQuery(roles)) {
          assertTrue(res.next());
          assertEquals(3, res.getInt(1), "There should be 3 roles (Admin, Teacher, and Student)");
    }
  }

  @Test
  void testAdminUserCreation() throws SQLException {
    String admins = "SELECT userId FROM users WHERE roleId = 1";
    try (Connection connection = manager.getConnection();
         Statement statement = connection.createStatement();
         ResultSet res = statement.executeQuery(admins)) {
          assertTrue(res.next(), "A user with roleId = 1 should exist");
          int userId = res.getInt("userId");
          assertTrue(userId > 0);
    }
  }
}