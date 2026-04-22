package com.gradetracker.controller;

import com.gradetracker.dao.DaoProvider;
import com.gradetracker.dao.UserDao;
import com.gradetracker.manager.SceneManager;
import com.gradetracker.manager.Session;
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

  private UserDao userDao = DaoProvider.userDao();

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  /** Text field for entering the username. */
  @FXML
  private TextField usernameField;

  /** Password field for entering the password. */
  @FXML
  private PasswordField passwordField;

  /** Label used to display validation or login error messages. */
  @FXML
  private Label messageLabel;

  @FXML
  public void initialize() {
    usernameField.setOnAction(e -> passwordField.requestFocus());
  }

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

  /**
   * Validates the username and password fields.
   *
   * @param username the username entered by the user
   * @param password the password entered by the user
   * @return an error message if validation fails, otherwise null
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
   * Handles the login button action.
   * Validates user input, attempts authentication through the UserDao,
   * and switches scenes based on the user's role if successful.
   * Displays an error message if authentication fails.
   */
  @FXML
  private void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();

    String error = validate(username, password);
    if (error != null) {
      setMessage(error, "text-warning");
      return;
    }

    User user = userDao.authenticate(username, password);

    if (user == null) {
      setMessage("Invalid username or password.", "text-warning");
      return;
    }

    Session.startSession(user.getUserId(), user.getRoleId());

    Stage stage = (Stage) usernameField.getScene().getWindow();
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/dashboard.fxml", "Dashboard");
  }
}
