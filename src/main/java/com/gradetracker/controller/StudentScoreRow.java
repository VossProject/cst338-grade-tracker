package com.gradetracker.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * One row in the Grade Assignment table: a student and their editable score.
 *
 * @author Mikey Voss
 * @since 2026-04-18
 */
public class StudentScoreRow {

  private final int studentId;
  private final String studentName;
  private final StringProperty scoreText;

  /**
   * Creates a row for one enrolled student.
   *
   * @param studentId the student's user id
   * @param studentName display name shown in the Student column
   * @param initialScore pre-filled score text (empty string if not yet graded)
   */
  public StudentScoreRow(int studentId, String studentName, String initialScore) {
    this.studentId = studentId;
    this.studentName = studentName;
    this.scoreText = new SimpleStringProperty(initialScore == null ? "" : initialScore);
  }

  public int getStudentId() {
    return studentId;
  }

  public String getStudentName() {
    return studentName;
  }

  public String getScoreText() {
    return scoreText.get();
  }

  /**
   * Updates the score text shown in the table.
   *
   * @param value new score text
   */
  public void setScoreText(String value) {
    scoreText.set(value);
  }

  /**
   * Exposes the score as an observable property so the TableView cell can
   * bind to it directly.
   *
   * @return the score text property
   */
  public StringProperty scoreTextProperty() {
    return scoreText;
  }
}
