package com.gradetracker.controller;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Robert Mozzetti
 * created: 4/13/2026
 * Explanation: Unit tests for CreateUserController validation
 */
class CreateUserControllerTest {

  //Tests for valid user creation with a new username, password, and by selecting a role
  @Test
  void testValidUserCreation() {
    String result = CreateUserController.validate(
            "rmozzetti",
            3,
            "password123",
            "password123",
            Collections.emptySet());
    assertNull(result);
  }

  //Tests for failed user creation by using a duplicate username
  @Test
  void testDuplicateUsername() {
    String result = CreateUserController.validate(
            "admin",
            1,
            "password123",
            "password123",
            Set.of("admin", "teacher1"));
    assertEquals("That username is already taken.", result);
  }

  //Tests for creating 2 users with different usernames
  //but identical passwords (This should not return an error)
  @Test
  void testTwoUsersIdenticalPasswords() {
    String firstResult = CreateUserController.validate(
            "robert",
            3,
            "samepassword123",
            "samepassword123",
            Collections.emptySet());
    String secondResult = CreateUserController.validate(
            "harvey",
            3,
            "samepassword123",
            "samepassword123",
            Set.of("robert"));
    assertNull(firstResult);
    assertNull(secondResult);
  }
}
