package com.gradetracker.controller;

import com.gradetracker.model.Assignment;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for StudentClassController helper logic.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
class StudentClassControllerTest {

  @Test
  void buildTotalPointsTextReturnsLetterGradeForGraded() {
    Assignment a1 = new Assignment("Homework 1", "Intro to JavaFX",
        LocalDate.of(2026, 4, 20), 100, 1);
    a1.setId(1);
    Assignment a2 = new Assignment("Quiz 1", "Basic MVC concepts",
        LocalDate.of(2026, 4, 25), 20, 1);
    a2.setId(2);
    Assignment a3 = new Assignment("Project Checkpoint", "Login progress",
        LocalDate.of(2026, 5, 1), 50, 1);
    a3.setId(3);

    Map<Integer, Double> grades = new HashMap<>();
    grades.put(1, 85.0);
    grades.put(2, 18.0);

    String result = StudentClassController.buildTotalPointsText(
        List.of(a1, a2, a3), grades);

    assertEquals("Grade: B (85.8%)", result);
  }
}
