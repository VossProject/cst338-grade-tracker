package com.gradetracker.controller;

import com.gradetracker.model.NavItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Controller for the main parent scheme.
 *
 * @author Olga Bradford
 * @since 4/15/2026
 */
public class DashboardController {

  //TODO: Make the Dashboard (home scene) preselected on log in

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
    //TODO: implement navigation based on logged in user role
    resources.getChildren().add(new TreeItem<>(new NavItem("Classes", "/fxml/student-class-view.fxml")));
    resources.getChildren().add(new TreeItem<>(new NavItem("Users", "/fxml/user-list.fxml")));

    navTree.setRoot(root);

    navTree.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
      if (newVal != null && newVal.getValue().getFxmlPath() != null) {
        navigateTo(newVal.getValue().getFxmlPath());
      }
    });

    // Making the menu take up all available vertical space
    navTree.setMaxHeight(Double.MAX_VALUE);
    VBox.setVgrow(navTree, Priority.ALWAYS);
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

  //TODO: Implement Sign Out logic
  /**
   * Signing out of the app and going back to the login scene
   */
  public void signOut(){

  }
}
