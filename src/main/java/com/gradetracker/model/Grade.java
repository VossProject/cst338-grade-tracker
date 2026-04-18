package com.gradetracker.model;

/**
 * Represents a grade for a student on an assignment.
 *
 * @author Mikey Voss
 * @since 2026-04-16
 */
public class Grade {

  private int id;
  private double gradeValue;
  private int assignmentId;
  private int studentId;

  /**
   * Creates a new grade.
   *
   * @param gradeValue the score value
   * @param assignmentId id of the assignment this grade belongs to
   * @param studentId id of the student who earned the grade
   */
  public Grade(double gradeValue, int assignmentId, int studentId) {
    this.gradeValue = gradeValue;
    this.assignmentId = assignmentId;
    this.studentId = studentId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getGradeValue() {
    return gradeValue;
  }

  public int getAssignmentId() {
    return assignmentId;
  }

  public int getStudentId() {
    return studentId;
  }
}
