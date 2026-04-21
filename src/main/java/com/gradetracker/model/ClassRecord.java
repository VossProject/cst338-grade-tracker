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
  private String userName;

  public ClassRecord(int classId, String className, String description, int teacherId, String teacherName) {
    this.classId = classId;
    this.className = className;
    this.description = description;
    this.teacherId = teacherId;
    this.userName = teacherName;
  }

  public ClassRecord(String className, String description, int teacherId) {
    this(0, className, description, teacherId, "");
  }

  public ClassRecord(int classId, String className, String description, String teacherName) {
    this(classId, className, description, 0, teacherName);
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

  public String getUserName() {
    return userName;
  }
}
