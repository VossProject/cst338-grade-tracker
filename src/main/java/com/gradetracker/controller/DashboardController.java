package com.gradetracker.controller;

import com.gradetracker.manager.SceneManager;
import com.gradetracker.manager.Session;
import com.gradetracker.model.NavItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the main parent scheme.
 *
 * @author Olga Bradford
 * @since 4/15/2026
 */
public class DashboardController {

  @FXML
  private TreeView<NavItem> navTree;
  @FXML
  private StackPane contentArea;

  /**
   * Initializing the navigation menu
   */
  public void initialize() {

    //Main menu items
    TreeItem<NavItem> root = new TreeItem<>(new NavItem("Root", null));
    TreeItem<NavItem> resources = new TreeItem<>(new NavItem("Resources", null));

    root.getChildren().add(new TreeItem<>(new NavItem("Dashboard", "/fxml/home.fxml")));
    root.getChildren().add(resources);

    //Resources sub-items
    //List of Users should only be available to admins
    //Also admins must be able to access the list of all classes
    if (Session.isAdmin()) {
      resources.getChildren().add(new TreeItem<>(new NavItem("Classes", "/fxml/admin-classes.fxml")));
      resources.getChildren().add(new TreeItem<>(new NavItem("Users", "/fxml/user-list.fxml")));
    } else {
      resources.getChildren().add(new TreeItem<>(new NavItem("Classes", "/fxml/non-admin-classes.fxml")));
    }

    navTree.setRoot(root);

    navTree.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null && newVal.getValue().getFxmlPath() != null) {
        navigateTo(newVal.getValue().getFxmlPath());
      }
    });

    // Making the menu take up all available vertical space
    navTree.setMaxHeight(Double.MAX_VALUE);
    VBox.setVgrow(navTree, Priority.ALWAYS);

    //Automatically selecting Dashboard Home after login
    navTree.getSelectionModel().select(root.getChildren().getFirst());
  }

  /**
   * Loading a scene into the content area.
   * @param fxmlFile scene file path
   */
  private void navigateTo(String fxmlFile) {
    try {
      Parent destination = FXMLLoader.load(getClass().getResource(fxmlFile));
      contentArea.getChildren().setAll(destination);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Signing out of the app and going back to the login scene
   * Created by Harvey Duran, I am just moving this to the appropriate scene after it has been created
   */
  @FXML
  public void signOut(){
    Stage stage = (Stage) contentArea.getScene().getWindow();
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/login.fxml", "Login");
    Session.clearSession();
  }
}
