package com.gradetracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Login controller for application.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class LoginController {
  // Testing input validation.
  public boolean isInputValid(String username, String password) {
    return username != null && !username.isBlank()
        && password != null && !password.isBlank();
  }

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label messageLabel;

  @FXML
  private void handleLogin() {
    // TODO: Validate username/password with database
    // TODO: Determine user role (admin, teacher, student)
    // TODO: Use SceneManager to navigate to dashboard
    messageLabel.setText("Login clicked");
  }

  @FXML
  private void handleRegister() {
    // TODO: Implement user registration logic
    messageLabel.setText("Register clicked");
  }
}

