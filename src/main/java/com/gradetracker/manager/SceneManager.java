package com.gradetracker.manager;

import javafx.scene.image.Image;
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

  /**
   * Creates a SceneManager for the given stage.
   *
   * @param stage the primary stage used for scene switching
   */
  public SceneManager(Stage stage) {
    this.stage = stage;
  }

  /**
   * Switches to a new scene using default window size.
   *
   * @param fxmlFile the FXML file path to load
   * @param title the window title
   */
  public void switchScene(String fxmlFile, String title) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
      Parent root = loader.load();

      Scene scene = new Scene(root, 500, 450);
      stage.setScene(scene);
      stage.setTitle(title);
      stage.getIcons().add(
          new Image(getClass().getResourceAsStream("/images/icon.png"))
      );
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException("Failed to load scene: " + fxmlFile, e);
    }
  }
}
