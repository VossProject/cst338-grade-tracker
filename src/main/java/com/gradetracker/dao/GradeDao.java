package com.gradetracker.dao;

import com.gradetracker.model.Grade;
import java.util.List;

/**
 * Data access interface for grades.
 *
 * @author Mikey Voss
 * @since 2026-04-16
 */
public interface GradeDao {

  /**
   * Saves a grade and assigns it an id.
   *
   * @param grade the grade to save
   */
  void save(Grade grade);

  /**
   * Finds all grades submitted for a given assignment.
   *
   * @param assignmentId the assignment id to search by
   * @return list of grades for that assignment
   */
  List<Grade> findByAssignmentId(int assignmentId);

  /**
   * Finds all grades across every assignment in a given class.
   *
   * @param classId the class id to search by
   * @return list of grades in that class
   */
  List<Grade> findByClassId(int classId);

  /**
   * Finds all grades earned by a given student.
   *
   * @param studentId the student id to search by
   * @return list of grades for that student
   */
  List<Grade> findByStudentId(int studentId);
}
