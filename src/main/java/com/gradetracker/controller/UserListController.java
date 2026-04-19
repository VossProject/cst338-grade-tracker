package com.gradetracker.controller;

import com.gradetracker.dao.InMemoryUserDao;
import com.gradetracker.dao.UserDao;
import com.gradetracker.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for List of Users scene
 *
 * @author Olga Bradford
 * @since 4/18/2026
 */
public class UserListController {
  @FXML
  private TableView<User> userTable;

  @FXML
  private TableColumn<User, Integer> userIdColumn;

  @FXML
  private TableColumn<User, String> userNameColumn;

  @FXML
  private TableColumn<User, String> roleColumn;

  private final UserDao userDao = new InMemoryUserDao();

  /**
   * Initializing the list of users table
   */
  @FXML
  public void initialize() {
    userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
    roleColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
    userTable.setItems(getUserData());
  }

  /**
   * Fetching user data for the table
   * @return list of users
   */
  public ObservableList<User> getUserData(){
    return FXCollections.observableArrayList(userDao.getAllUsers());
  }

  /**
   * Creates a popup and loads up the Create User Form scene
   */
  @FXML
  private void createUser(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-user.fxml"));
      Parent root = loader.load();
      Stage popup = new Stage();
      popup.setTitle("Create New User");
      popup.initModality(Modality.APPLICATION_MODAL);

      //Setting the pop up height and width to 30% of the screen and height to auto
      Rectangle2D screen = Screen.getPrimary().getVisualBounds();
      double width = screen.getWidth() * 0.30;
      popup.setScene(new Scene(root, width, -1));
      popup.showAndWait();
      //Refreshing the table when popup closes
      //might need to call initialize() instead
      //TODO: check if the table is actually refreshing
      getUserData();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
