package com.gradetracker.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing for login scene.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
class LoginControllerTest {

  @Test
  void testValidInput() {
    LoginController controller = new LoginController();

    assertTrue(controller.isInputValid("user", "pass"));
  }

  @Test
  void testEmptyUsername() {
    LoginController controller = new LoginController();

    assertFalse(controller.isInputValid("", "pass"));
  }

  @Test
  void testEmptyPassword() {
    LoginController controller = new LoginController();

    assertFalse(controller.isInputValid("user", ""));
  }

  @Test
  void testBothEmpty() {
    LoginController controller = new LoginController();

    assertFalse(controller.isInputValid("", ""));
  }
}