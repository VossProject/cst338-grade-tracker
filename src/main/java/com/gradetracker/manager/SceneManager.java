package com.gradetracker.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handles scene switching for application.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class SceneManager {

  private final Stage stage;

  public SceneManager(Stage stage) {
    this.stage = stage;
  }

  public void switchScene(String fxmlFile, String title) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent root = loader.load();

      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle(title);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
