package com.gradetracker.controller;

import com.gradetracker.model.Assignment;
import java.time.LocalDate;
import java.util.List;
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
  void buildTotalPointsTextReturnsCorrectTotal() {
    List<Assignment> assignments = List.of(
        new Assignment("Homework 1", "Intro to JavaFX",
            LocalDate.of(2026, 4, 20), 100, 1),
        new Assignment("Quiz 1", "Basic MVC concepts",
            LocalDate.of(2026, 4, 25), 20, 1),
        new Assignment("Project Checkpoint", "Login progress",
            LocalDate.of(2026, 5, 1), 50, 1)
    );

    String result = StudentClassController.buildTotalPointsText(assignments);

    assertEquals("Total Points: 136 / 170", result);
  }

  @Test
  void buildTotalPointsTextReturnsZeroWhenNoAssignments() {
    List<Assignment> assignments = List.of();

    String result = StudentClassController.buildTotalPointsText(assignments);

    assertEquals("Total Points: 0 / 0", result);
  }
}