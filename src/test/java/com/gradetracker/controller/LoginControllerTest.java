package com.gradetracker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for LoginController validation.
 *
 * @author Harvey Duran
 * @since TBD
 */
class LoginControllerTest {

  private LoginController controller;

  @BeforeEach
  void setUp() {
    controller = new LoginController();
  }

  @Test
  void validInputReturnsNull() {
    String result = controller.validate("user", "pass");
    assertNull(result);
  }

  @Test
  void emptyUsernameReturnsError() {
    String result = controller.validate("", "pass");
    assertEquals("Username is required.", result);
  }

  @Test
  void emptyPasswordReturnsError() {
    String result = controller.validate("user", "");
    assertEquals("Password is required.", result);
  }

  @Test
  void bothEmptyReturnsUsernameError() {
    String result = controller.validate("", "");
    assertEquals("Username is required.", result);
  }
}