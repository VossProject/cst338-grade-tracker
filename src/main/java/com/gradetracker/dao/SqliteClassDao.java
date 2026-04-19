package com.gradetracker.dao;

import com.gradetracker.model.ClassRecord;
import com.gradetracker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert Mozzetti
 * created: 4/17/2026
 * Explanation: SQLite implementation of the ClassDao.
 */
public class SqliteClassDao implements ClassDao {

  @Override
  public List<User> findAvailableTeachers() {
    String sql = """
        SELECT u.userId, u.userName, r.roleName
        FROM users u
        JOIN roles r ON r.roleId = u.roleId
        LEFT JOIN classes c ON c.teacherId = u.userId
        WHERE r.roleName = 'Teacher' AND c.classId IS NULL
        ORDER BY u.userName
        """;

    return findUsers(sql);
  }

  @Override
  public List<User> findStudents() {
    String sql = """
        SELECT u.userId, u.userName, r.roleName
        FROM users u
        JOIN roles r ON r.roleId = u.roleId
        WHERE r.roleName = 'Student'
        ORDER BY u.userName
        """;

    return findUsers(sql);
  }

  @Override
  public List<User> findEnrolledStudents(int classId) {
    String sql = """
        SELECT u.userId, u.userName, r.roleName
        FROM student_classes sc
        JOIN users u ON u.userId = sc.studentId
        JOIN roles r ON r.roleId = u.roleId
        WHERE sc.classId = ?
        ORDER BY u.userName
        """;

    List<User> results = new ArrayList<>();
    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, classId);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          results.add(new User(
              rs.getInt("userId"),
              rs.getString("userName"),
              "",
              rs.getString("roleName")
          ));
        }
      }
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to load enrolled students.", e);
    }

    return results;
  }

  @Override
  public int saveClass(ClassRecord classRecord) {
    String sql = "INSERT INTO classes (className, description, teacherId) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, classRecord.getClassName());
      stmt.setString(2, classRecord.getDescription());
      stmt.setInt(3, classRecord.getTeacherId());
      stmt.executeUpdate();

      try (ResultSet keys = stmt.getGeneratedKeys()) {
        if (keys.next()) {
          int classId = keys.getInt(1);
          classRecord.setClassId(classId);
          return classId;
        }
      }
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to save class.", e);
    }

    throw new IllegalStateException("Class was saved without a generated ID.");
  }

  @Override
  public void enrollStudents(int classId, List<Integer> studentIds) {
    String sql = "INSERT INTO student_classes (studentId, classId) VALUES (?, ?)";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      for (Integer studentId : studentIds) {
        stmt.setInt(1, studentId);
        stmt.setInt(2, classId);
        stmt.addBatch();
      }
      stmt.executeBatch();
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to enroll students.", e);
    }
  }

  @Override
  public void enrollStudent(int classId, int studentId) {
    String sql = "INSERT INTO student_classes (studentId, classId) VALUES (?, ?)";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, studentId);
      stmt.setInt(2, classId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to enroll student.", e);
    }
  }

  @Override
  public boolean isStudentEnrolled(int classId, int studentId) {
    String sql = """
        SELECT COUNT(*)
        FROM student_classes
        WHERE classId = ? AND studentId = ?
        """;

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, classId);
      stmt.setInt(2, studentId);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next() && rs.getInt(1) > 0;
      }
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to check student enrollment.", e);
    }
  }

  private List<User> findUsers(String sql) {
    List<User> results = new ArrayList<>();

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        results.add(new User(
            rs.getInt("userId"),
            rs.getString("userName"),
            "",
            rs.getString("roleName")
        ));
      }
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to load users for class form.", e);
    }

    return results;
  }

  /**
   * Returns all classes in the database.
   *
   * @return a list of all classes
   */
  @Override
  public ObservableList<ClassRecord> getAllClasses() {
    ObservableList<ClassRecord> classes = FXCollections.observableArrayList();

    String request = """
        SELECT c.classId, c.className, c.description, u.userName 
        FROM classes c 
        JOIN users u ON c.teacherId = u.userId
    """;

    try (Connection connection = DatabaseManager.getInstance().getConnection();
         Statement statement = connection.createStatement();
         ResultSet response = statement.executeQuery(request)) {

      while (response.next()) {
        classes.add(new ClassRecord(
            response.getInt("classId"),
            response.getString("className"),
            response.getString("description"),
            response.getString("userName")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return classes;
  }
}
