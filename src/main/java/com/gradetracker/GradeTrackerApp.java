package com.gradetracker;

import atlantafx.base.theme.Dracula;
import com.gradetracker.manager.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point for the Grade Tracker application.
 *
 * @author Mikey Voss
 * @since 2026-04-02
 */
public class GradeTrackerApp extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());

    //Making the app automatically occupy 85% of the screen and centering it
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    double width = screenBounds.getWidth() * 0.85;
    double height = screenBounds.getHeight() * 0.85;
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setX((screenBounds.getWidth() - width) / 2);
    stage.setY((screenBounds.getHeight() - height) / 2);

    // TODO: change to login.fxml once login scene is wired up
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/dashboard.fxml", "Grade Tracker");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
