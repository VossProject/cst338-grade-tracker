package com.gradetracker.controller;

import com.gradetracker.dao.SqliteUserDao;
import com.gradetracker.dao.UserDao;
import com.gradetracker.model.Role;
import com.gradetracker.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.gradetracker.model.Role;

/**
 * @author Robert Mozzetti
 * created: 4/13/2026
 * Explanation: Controller for the create user form
 */
public class CreateUserController {

  @FXML
  private TextField usernameField;

  @FXML
  private ComboBox<Role> roleComboBox;

  @FXML
  private PasswordField passwordField;

  @FXML
  private PasswordField confirmPasswordField;

  @FXML
  private Label messageLabel;

  private UserDao userDao = new SqliteUserDao();

  /**
   * Allows injecting an alternative UserDao implementation (e.g. for testing).
   *
   * @param userDao the UserDao implementation to use
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @FXML
  private void initialize() {
    roleComboBox.setItems(FXCollections.observableArrayList(
            new Role(1, "Admin"),
            new Role(2, "Teacher"),
            new Role(3, "Student")
    ));

    usernameField.setOnAction(e -> roleComboBox.requestFocus());
    passwordField.setOnAction(e -> confirmPasswordField.requestFocus());

    setMessage("", "text-muted");
  }

  /**
   * Validates user creation form values.
   *
   * @param username the entered username
   * @param roleId the selected role ID, or null if none selected
   * @param password the entered password
   * @param confirmPassword the confirmation password entry
   * @param userDao the UserDao used to check for duplicate usernames
   * @return error message if invalid, otherwise null
   */
  static String validate(
          String username,
          Integer roleId,
          String password,
          String confirmPassword,
          UserDao userDao
  ) {
    if (username == null || username.isBlank()) {
      return "Username is required.";
    }
    if (roleId == null) {
      return "Role is required.";
    }
    if (password == null || password.isBlank()) {
      return "Password is required.";
    }
    if (confirmPassword == null || confirmPassword.isBlank()) {
      return "Please confirm the password.";
    }
    if (!password.equals(confirmPassword)) {
      return "Passwords do not match.";
    }
    if (password.length() < 8) {
      return "Password must be at least 8 characters.";
    }
    if (userDao.usernameExists(username)) {
      return "That username is already taken.";
    }
    return null;
  }

  @FXML
  private void handleCreateUser() {
    String username = usernameField.getText().trim();
    Role role = roleComboBox.getValue();
    Integer roleId = role == null ? null : role.getRoleId();
    String password = passwordField.getText();
    String confirmPassword = confirmPasswordField.getText();

    String error = validate(username, roleId, password, confirmPassword, userDao);
    if (error != null) {
      setMessage(error, "text-warning");
      return;
    }

    try {
      User newUser = new User(0, username, password, role.toString());
      userDao.save(newUser);
      setMessage("User created successfully.", "text-success");
      clearForm();
    } catch (IllegalStateException e) {
      setMessage("Unable to create user right now.", "text-warning");
    }
  }

  private void clearForm() {
    usernameField.clear();
    roleComboBox.setValue(null);
    passwordField.clear();
    confirmPasswordField.clear();
  }

  /**
   * Applies AtlantaFX message styling to the message label.
   *
   * @param message the text to display
   * @param styleClass the AtlantaFX style class (e.g. "text-danger", "text-success")
   */
  private void setMessage(String message, String styleClass) {
    messageLabel.setText(message);
    String color = switch (styleClass) {
      case "text-success" -> "#50FA7B";
      case "text-warning" -> "#FFB86C";
      case "text-danger" -> "#FF5555";
      default -> "inherit";
    };
    messageLabel.setStyle("-fx-text-fill: " + color + ";");
  }
}