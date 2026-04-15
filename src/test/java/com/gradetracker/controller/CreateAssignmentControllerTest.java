package com.gradetracker.controller;

import java.util.Collections;
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

  // TODO: testing plan item 2b - no max grade
  // TODO: testing plan item 3 - duplicate assignment name
}
