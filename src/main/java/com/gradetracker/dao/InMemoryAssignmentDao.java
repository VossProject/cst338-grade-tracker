package com.gradetracker.dao;

import com.gradetracker.model.Assignment;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of AssignmentDao for testing.
 * TODO: replace with SqliteAssignmentDao once database layer is set up
 *
 * @author Mikey Voss
 * @since 2026-04-12
 */
public class InMemoryAssignmentDao implements AssignmentDao {

  private final List<Assignment> assignments = new ArrayList<>();
  private int nextId = 1;

  @Override
  public void save(Assignment assignment) {
    assignment.setId(nextId++);
    assignments.add(assignment);
  }

  @Override
  public List<Assignment> findByClassId(int classId) {
    List<Assignment> result = new ArrayList<>();
    for (Assignment a : assignments) {
      if (a.getClassId() == classId) {
        result.add(a);
      }
    }
    return result;
  }
}
