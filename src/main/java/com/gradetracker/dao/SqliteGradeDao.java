package com.gradetracker.dao;

import com.gradetracker.model.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite implementation of GradeDao.
 *
 * @author Mikey Voss
 * @since 2026-04-16
 */
public class SqliteGradeDao implements GradeDao {

  @Override
  public void save(Grade grade) {
    String sql = "INSERT INTO grades (gradeValue, assignmentId, studentId) "
        + "VALUES (?, ?, ?)";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setDouble(1, grade.getGradeValue());
      stmt.setInt(2, grade.getAssignmentId());
      stmt.setInt(3, grade.getStudentId());
      stmt.executeUpdate();

      ResultSet keys = stmt.getGeneratedKeys();
      if (keys.next()) {
        grade.setId(keys.getInt(1));
      }
    } catch (SQLException e) {
      System.err.println("Failed to save grade: " + e.getMessage());
    }
  }

  @Override
  public List<Grade> findByAssignmentId(int assignmentId) {
    String sql = "SELECT gradeId, gradeValue, assignmentId, studentId "
        + "FROM grades WHERE assignmentId = ?";
    List<Grade> results = new ArrayList<>();

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, assignmentId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        results.add(mapRow(rs));
      }
    } catch (SQLException e) {
      System.err.println("Failed to find grades: " + e.getMessage());
    }

    return results;
  }

  @Override
  public List<Grade> findByClassId(int classId) {
    String sql = "SELECT grades.gradeId, grades.gradeValue, grades.assignmentId, "
        + "grades.studentId FROM grades "
        + "JOIN assignments ON grades.assignmentId = assignments.assignmentId "
        + "WHERE assignments.classId = ?";
    List<Grade> results = new ArrayList<>();

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, classId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        results.add(mapRow(rs));
      }
    } catch (SQLException e) {
      System.err.println("Failed to find grades: " + e.getMessage());
    }

    return results;
  }

  @Override
  public List<Grade> findByStudentId(int studentId) {
    String sql = "SELECT gradeId, gradeValue, assignmentId, studentId "
        + "FROM grades WHERE studentId = ?";
    List<Grade> results = new ArrayList<>();

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, studentId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        results.add(mapRow(rs));
      }
    } catch (SQLException e) {
      System.err.println("Failed to find grades: " + e.getMessage());
    }

    return results;
  }

  private Grade mapRow(ResultSet rs) throws SQLException {
    Grade g = new Grade(
        rs.getDouble("gradeValue"),
        rs.getInt("assignmentId"),
        rs.getInt("studentId")
    );
    g.setId(rs.getInt("gradeId"));
    return g;
  }
}
