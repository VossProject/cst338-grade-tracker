package com.gradetracker.controller;

import com.gradetracker.dao.SqliteClassDao;
import com.gradetracker.model.ClassRecord;
import com.gradetracker.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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
 * Controller for the list of all classes scene (available onl to admin users)
 *
 * @author Olga Bradford
 * @since 4/18/2026
 */
public class AdminClassesController {
  @FXML
  private TableView<ClassRecord> classTable;

  @FXML
  private TableColumn<ClassRecord, Integer> classIdColumn;

  @FXML
  private TableColumn<ClassRecord, String> classNameColumn;

  @FXML
  private TableColumn<ClassRecord, String> descriptionColumn;

  @FXML
  private TableColumn<ClassRecord, String> teacherColumn;

  private final SqliteClassDao classDao = new SqliteClassDao();

  /**
   * Initializing the list of classes table
   */
  @FXML
  public void initialize() {
    classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
    classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    teacherColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
    classTable.setItems(getClassData());
  }

  /**
   * Fetching class data for the table
   * @return list of classes
   */
  public ObservableList<ClassRecord> getClassData(){
    return FXCollections.observableArrayList(classDao.getAllClasses());
  }

  /**
   * Creates a popup and loads up the Create Class Form scene
   */
  @FXML
  private void createClass(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-class.fxml"));
      Parent root = loader.load();
      Stage popup = new Stage();
      popup.setTitle("Create New Class");
      popup.initModality(Modality.APPLICATION_MODAL);

      //Setting the pop up height and width to 30% of the screen and height to auto
      Rectangle2D screen = Screen.getPrimary().getVisualBounds();
      double width = screen.getWidth() * 0.30;
      popup.setScene(new Scene(root, width, -1));
      popup.showAndWait();

      //Refreshing the table when popup closes
      initialize();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

