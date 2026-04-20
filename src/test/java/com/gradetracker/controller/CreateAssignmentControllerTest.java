package com.gradetracker.controller;

import com.gradetracker.model.Assignment;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for CreateAssignmentController validation.
 * Tests align with "Test Teacher Creates an Assignment" in the testing plan.
 *
 * @author Mikey Voss
 * @since 2026-04-13
 */
class CreateAssignmentControllerTest {

  // Testing plan item 1: valid assignment creation
  @Test
  void validAssignmentReturnsNull() {
    String result = CreateAssignmentController.validate("Homework 1", "100", Collections.emptyList());
    assertNull(result);
  }

  // Testing plan item 2a: no assignment title
  @Test
  void emptyTitleReturnsError() {
    String result = CreateAssignmentController.validate("", "100", Collections.emptyList());
    assertEquals("Title is required.", result);
  }

  // Testing plan item 2b: no max grade
  @Test
  void emptyMaxGradeReturnsError() {
    String result = CreateAssignmentController.validate("Homework 1", "", Collections.emptyList());
    assertEquals("Max grade is required.", result);
  }

  // Testing plan item 3: duplicate assignment name (case-insensitive)
  @Test
  void duplicateTitleReturnsError() {
    Assignment existing = new Assignment("Homework 1", "", null, 100, 1);
    String result = CreateAssignmentController.validate(
        "HOMEWORK 1", "100", List.of(existing));
    assertEquals("An assignment with this title already exists.", result);
  }
}
