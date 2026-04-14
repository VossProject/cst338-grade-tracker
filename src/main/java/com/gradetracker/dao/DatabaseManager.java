package com.gradetracker.dao;

import java.io.File;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

/**
 * Manages SQLite database connection, the initial tables setup, and preseeds data.
 * This class follows the singleton pattern to ensure only one database controller exists during the lifecycle.
 * To use this in your DAO add:
 * {@code Connection connection = DatabaseManager.getInstance().getConnection();}
 *
 * @author Olga Bradford
 * @since 4/13/2026
 */
public class DatabaseManager {
  private static final String DB_NAME = "otterware_grade_tracker.db";
  private static final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.home") + File.separator + DB_NAME;
  private static DatabaseManager manager;

  /**
   * Private constructor that initializes the database by creating tables and
   * preseeding data if it does not already exist.
   */
  private DatabaseManager() {
    try (Connection connection = getConnection()) {
      if (connection != null) {
        createTables(connection);
        preseedData(connection);
      }
    } catch (SQLException e) {
      System.err.println("Could not initialize database: " + e.getMessage());
    }
  }

  /**
   * Provides global access to the single instance of the DatabaseManager.
   *
   * @return The singleton instance of DatabaseManager.
   */
  public static synchronized DatabaseManager getInstance() {
    if (manager == null) {
      manager = new DatabaseManager();
    }
    return manager;
  }

  /**
   * Opens and returns a new connection to the SQLite database.
   *
   * @return A Connection object to the database.
   * @throws SQLException In case of a database access error.
   */
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  /**
   * Creates all database tables, if they do not already exist
   *
   * @param connection Active database connection.
   * @throws SQLException In case of a table creation error.
   */
  private void createTables(Connection connection) throws SQLException {
    String tables = """
            CREATE TABLE IF NOT EXISTS roles (
                roleId INTEGER PRIMARY KEY AUTOINCREMENT,
                roleName TEXT NOT NULL UNIQUE
            );
            
            CREATE TABLE IF NOT EXISTS users (
                userId INTEGER PRIMARY KEY AUTOINCREMENT,
                userName TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                roleId INTEGER NOT NULL,
                FOREIGN KEY (roleId) REFERENCES roles(roleId) ON DELETE CASCADE
            );
            
            CREATE TABLE IF NOT EXISTS classes (
                classId INTEGER PRIMARY KEY AUTOINCREMENT,
                className TEXT NOT NULL UNIQUE,
                description TEXT NOT NULL,
                teacherId INTEGER,
                FOREIGN KEY (teacherId) REFERENCES users(userId) ON DELETE SET NULL
            );
            
            CREATE TABLE IF NOT EXISTS student_classes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                studentId INTEGER NOT NULL,
                classId INTEGER NOT NULL,
                FOREIGN KEY (studentId) REFERENCES users(userId) ON DELETE CASCADE,
                FOREIGN KEY (classId) REFERENCES classes(classId) ON DELETE CASCADE
            );
            
            CREATE TABLE IF NOT EXISTS assignments (
                assignmentId INTEGER PRIMARY KEY AUTOINCREMENT,
                assignmentTitle	TEXT NOT NULL,
                description	TEXT NOT NULL,
                dueDate	TEXT NOT NULL,
                maxGrade	REAL NOT NULL,
                classId INTEGER NOT NULL,
                FOREIGN KEY (classId) REFERENCES classes(classId) ON DELETE CASCADE
            );
            
            CREATE TABLE IF NOT EXISTS grades (
                gradeId INTEGER PRIMARY KEY AUTOINCREMENT,
                gradeValue	REAL NOT NULL,
                assignmentId INTEGER NOT NULL,
                studentId INTEGER NOT NULL,
                FOREIGN KEY (assignmentId) REFERENCES assignments(assignmentId) ON DELETE CASCADE,
                FOREIGN KEY (studentId) REFERENCES users(userId) ON DELETE CASCADE
            );
        """;

    try (Statement statement = connection.createStatement()) {
      for (String query : tables.split(";")) {
        if (!query.trim().isEmpty()) {
          statement.execute(query);
        }
      }
    }
  }

  /**
   * Preseeds databse with user roles and an initial Admin user.
   *
   * @param connection Active database connection.
   * @throws SQLException In case of a data insertion error.
   */
  private void preseedData(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      ResultSet userRoles = statement.executeQuery("SELECT COUNT(*) FROM roles");
      if (userRoles.next() && userRoles.getInt(1) == 0) {
        statement.execute("INSERT INTO roles (roleName) VALUES ('Admin'), ('Teacher'), ('Student')");
        System.out.println("Roles preseeded.");
      }

      ResultSet existingUsers = statement.executeQuery("SELECT COUNT(*) FROM users");
      if (existingUsers.next() && existingUsers.getInt(1) == 0) {
        statement.execute("INSERT INTO users (userName, password, roleId) VALUES ('Admin', 'Admin123', 1)");
        System.out.println("Default admin user preseeded.");
      }
    }
  }

  //This code triggers the database creation. This is for testing purposes only. DO NOT UNCOMMENT
  /*public static void main(String[] args) {
    DatabaseManager.getInstance();
    System.out.println("Test successful! Check your C:/Users/<username> folder");
  }*/


}
