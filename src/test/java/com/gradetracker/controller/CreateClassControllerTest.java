package com.gradetracker.controller;

import com.gradetracker.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Robert Mozzetti
 * created: 4/13/2026
 * Explanation: Unit tests for CreateClassController validation
 */
class CreateClassControllerTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  //Tests for valid class creation using teachers and students
  @Test
  void testValidClassCreation() {
    User teacher = new User(1, "Professor Mozzarella", "Teacher");
    String result = CreateClassController.validate("CST 338", "Software Design", teacher);
    assertNull(result);
  }

  //Tests for failed class creation if no teachers are available or teachers dropdown is empty
  @Test
  void testFailedClassCreationNoTeacher() {
    String result = CreateClassController.validate("CST 338", "Software Design", null);
    assertEquals("A teacher must be selected.", result);
  }

  //TODO: Manually test for displayed error message in the event a student who is already enrolled is added again

  //TODO: Manually test for displayed error message if a class with a duplicate name is created
}