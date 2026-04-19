package com.gradetracker.model;

/**
 * @author Robert Mozzetti
 * created: 4/17/2026
 * Explanation: Represents a class in the Grade Tracker system.
 */
public class ClassRecord {

  private int classId;
  private String className;
  private String description;
  private int teacherId;

  public ClassRecord(String className, String description, int teacherId) {
    this.className = className;
    this.description = description;
    this.teacherId = teacherId;
  }

  public int getClassId() {
    return classId;
  }

  public void setClassId(int classId) {
    this.classId = classId;
  }

  public String getClassName() {
    return className;
  }

  public String getDescription() {
    return description;
  }

  public int getTeacherId() {
    return teacherId;
  }
}
