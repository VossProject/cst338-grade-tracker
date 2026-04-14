package com.gradetracker;

import atlantafx.base.theme.Dracula;
import com.gradetracker.manager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the Grade Tracker application.
 *
 * @author Mikey Voss
 * @since 2026-04-02
 */
public class GradeTrackerApp extends Application {

  @Override
  public void start(Stage stage) {
    Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());

    // TODO: change to login.fxml once login scene is wired up
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/create-user.fxml", "Create User");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
