package com.gradetracker.controller;

import java.util.Collections;
import java.util.Set;
import com.gradetracker.dao.InMemoryUserDao;
import com.gradetracker.dao.UserDao;

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
    UserDao dao = new InMemoryUserDao();
    String result = CreateUserController.validate(
            "rmozzetti",
            3,
            "password123",
            "password123",
            dao);
    assertNull(result);
  }

  //Tests for failed user creation by using a duplicate username
  @Test
  void testDuplicateUsername() {
    UserDao dao = new InMemoryUserDao();
    String result = CreateUserController.validate(
            "admin",
            1,
            "password123",
            "password123",
            dao);
    assertEquals("That username is already taken.", result);
  }

  //Tests for creating 2 users with different usernames
  //but identical passwords (This should not return an error)
  @Test
  void testTwoUsersIdenticalPasswords() {
    UserDao dao = new InMemoryUserDao();
    String firstResult = CreateUserController.validate(
            "robert",
            3,
            "samepassword123",
            "samepassword123",
            dao);
    String secondResult = CreateUserController.validate(
            "harvey",
            3,
            "samepassword123",
            "samepassword123",
            dao);
    assertNull(firstResult);
    assertNull(secondResult);
  }
}
