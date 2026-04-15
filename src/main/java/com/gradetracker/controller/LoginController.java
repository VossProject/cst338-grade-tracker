package com.gradetracker.controller;

import com.gradetracker.dao.InMemoryUserDao;
import com.gradetracker.dao.UserDao;
import com.gradetracker.manager.SceneManager;
import com.gradetracker.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the login scene.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class LoginController {

  private UserDao userDao = new InMemoryUserDao();

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Label messageLabel;

  public String validate(String username, String password) {
    if (username == null || username.isBlank()) {
      return "Username is required.";
    }
    if (password == null || password.isBlank()) {
      return "Password is required.";
    }
    return null;
  }

  @FXML
  private void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    String error = validate(username, password);
    if (error != null) {
      messageLabel.setText(error);
      return;
    }

    User user = userDao.authenticate(username, password);

    if (user == null) {
      messageLabel.setText("Invalid username or password.");
      return;
    }

    Stage stage = (Stage) usernameField.getScene().getWindow();
    SceneManager sceneManager = new SceneManager(stage);

    String role = user.getRoleName();

    if ("Admin".equalsIgnoreCase(role)) {
      sceneManager.switchScene("/fxml/create-user.fxml", "Admin");
    } else if ("Teacher".equalsIgnoreCase(role)) {
      sceneManager.switchScene("/fxml/create-assignment.fxml", "Teacher");
    } else if ("Student".equalsIgnoreCase(role)) {
      sceneManager.switchScene("/fxml/student-class-view.fxml", "Student");
    } else {
      messageLabel.setText("Unknown user role.");
    }
  }
}
