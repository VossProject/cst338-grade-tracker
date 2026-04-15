package com.gradetracker.dao;

import com.gradetracker.model.Assignment;
import java.util.List;

/**
 * Data access interface for assignments.
 *
 * @author Mikey Voss
 * @since 2026-04-12
 */
public interface AssignmentDao {

  /**
   * Saves an assignment and assigns it an id.
   *
   * @param assignment the assignment to save
   */
  void save(Assignment assignment);

  /**
   * Finds all assignments for a given class.
   *
   * @param classId the class id to search by
   * @return list of assignments in that class
   */
  List<Assignment> findByClassId(int classId);

  /**
   * Updates an existing assignment.
   *
   * @param assignment the assignment with updated fields
   */
  void update(Assignment assignment);

  /**
   * Deletes an assignment by its id.
   *
   * @param assignmentId the id of the assignment to delete
   */
  void delete(int assignmentId);
}
