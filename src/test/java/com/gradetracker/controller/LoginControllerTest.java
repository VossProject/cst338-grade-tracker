package com.gradetracker.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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