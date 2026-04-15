package com.gradetracker.model;

import java.time.LocalDate;

/**
 * Represents an assignment in a class.
 *
 * @author Mikey Voss
 * @since 2026-04-12
 */
public class Assignment {

  private int id;
  private String title;
  private String description;
  private LocalDate dueDate;
  private double maxGrade;
  private int classId;

  /**
   * Creates a new assignment.
   *
   * @param title assignment title
   * @param description assignment description
   * @param dueDate due date
   * @param maxGrade maximum grade possible
   * @param classId id of the class this assignment belongs to
   */
  public Assignment(String title, String description, LocalDate dueDate,
      double maxGrade, int classId) {
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.maxGrade = maxGrade;
    this.classId = classId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public double getMaxGrade() {
    return maxGrade;
  }

  public int getClassId() {
    return classId;
  }
}
