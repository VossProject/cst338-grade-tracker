package com.gradetracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller for the login scene.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class LoginController {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label messageLabel;

  /**
   * Validates login input fields.
   *
   * @param username the entered username
   * @param password the entered password
   * @return an error message if input is invalid, otherwise null
   */
  public String validate(String username, String password) {
    if (username == null || username.isBlank()) {
      return "Username is required.";
    }
    if (password == null || password.isBlank()) {
      return "Password is required.";
    }
    return null;
  }

  /**
   * Handles login button click.
   */
  @FXML
  private void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    String error = validate(username, password);
    if (error != null) {
      messageLabel.setText(error);
      return;
    }

    // TODO: Connect to UserDao/authentication logic
    // TODO: Verify credentials against database
    // TODO: Determine user role
    // TODO: Use SceneManager to navigate to correct dashboard

    messageLabel.setText("Login logic not connected yet.");
  }

  /**
   * Handles register button click.
   */
  @FXML
  private void handleRegister() {
    // TODO: Implement registration flow or navigate to registration scene
    messageLabel.setText("Register clicked.");
  }
}