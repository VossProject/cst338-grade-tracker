package com.gradetracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Login controller for application.
 *
 * @author Harvey Duran
 * @since TBD
 */
public class LoginController {

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