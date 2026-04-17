package com.gradetracker.dao;

import com.gradetracker.model.Assignment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLite implementation of AssignmentDao.
 *
 * @author Mikey Voss
 * @since 2026-04-14
 */
public class SqliteAssignmentDao implements AssignmentDao {

  @Override
  public void save(Assignment assignment) {
    String sql = "INSERT INTO assignments "
        + "(assignmentTitle, description, dueDate, maxGrade, classId) "
        + "VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, assignment.getTitle());
      stmt.setString(2, assignment.getDescription());
      stmt.setString(3, assignment.getDueDate().toString());
      stmt.setDouble(4, assignment.getMaxGrade());
      stmt.setInt(5, assignment.getClassId());
      stmt.executeUpdate();

      ResultSet keys = stmt.getGeneratedKeys();
      if (keys.next()) {
        assignment.setId(keys.getInt(1));
      }
    } catch (SQLException e) {
      System.err.println("Failed to save assignment: " + e.getMessage());
    }
  }

  @Override
  public List<Assignment> findByClassId(int classId) {
    String sql = "SELECT assignmentId, assignmentTitle, description, "
        + "dueDate, maxGrade, classId FROM assignments WHERE classId = ?";
    List<Assignment> results = new ArrayList<>();

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, classId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Assignment a = new Assignment(
            rs.getString("assignmentTitle"),
            rs.getString("description"),
            LocalDate.parse(rs.getString("dueDate")),
            rs.getDouble("maxGrade"),
            rs.getInt("classId")
        );
        a.setId(rs.getInt("assignmentId"));
        results.add(a);
      }
    } catch (SQLException e) {
      System.err.println("Failed to find assignments: " + e.getMessage());
    }

    return results;
  }

  @Override
  public void update(Assignment assignment) {
    String sql = "UPDATE assignments SET assignmentTitle = ?, description = ?, "
        + "dueDate = ?, maxGrade = ?, classId = ? WHERE assignmentId = ?";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, assignment.getTitle());
      stmt.setString(2, assignment.getDescription());
      stmt.setString(3, assignment.getDueDate().toString());
      stmt.setDouble(4, assignment.getMaxGrade());
      stmt.setInt(5, assignment.getClassId());
      stmt.setInt(6, assignment.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Failed to update assignment: " + e.getMessage());
    }
  }

  @Override
  public void delete(int assignmentId) {
    String sql = "DELETE FROM assignments WHERE assignmentId = ?";

    try (Connection conn = DatabaseManager.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, assignmentId);
      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Failed to delete assignment: " + e.getMessage());
    }
  }
}
