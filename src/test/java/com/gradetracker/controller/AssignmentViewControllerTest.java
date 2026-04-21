package com.gradetracker.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for AssignmentViewController grade validation.
 * Tests align with "Test Teacher Enters Grades" in the testing plan.
 *
 * @author Mikey Voss
 * @since 2026-04-19
 */
class AssignmentViewControllerTest {

  // Testing plan item 1: valid grade submission on a valid assignment
  @Test
  void validGradeReturnsNull() {
    String result = AssignmentViewController.validate("85", 100.0);
    assertNull(result);
  }

  // Testing plan item 2: grade exceeds max grade
  @Test
  void gradeExceedsMaxReturnsError() {
    String result = AssignmentViewController.validate("150", 100.0);
    assertEquals("Grade cannot exceed max grade of 100.0.", result);
  }

  // Testing plan item 3: negative grade values
  @Test
  void negativeGradeReturnsError() {
    String result = AssignmentViewController.validate("-5", 100.0);
    assertEquals("Grade cannot be negative.", result);
  }
}
