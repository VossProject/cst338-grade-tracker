package com.gradetracker.controller;

import com.gradetracker.manager.Session;
import com.gradetracker.model.ClassRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for a Class Card
 *
 * @author Olga Bradford
 * @since 4/19/2026
 */
public class ClassCardController {

  @FXML
  private Label classNameText;
  @FXML
  private Label descriptionText;
  @FXML
  private Label teacherText;

  @FXML
  private Pane cardContainer;

  @FXML
  private MenuItem createAssignmentItem;

  private ClassRecord classRecord;

  @FXML
  public void initialize() {
    // Create Assignment is only available to teachers
    createAssignmentItem.setVisible(Session.isTeacher());
  }

  /**
   * Loads data from the ClassRecord object into class card.
   */
  public void setData(ClassRecord record) {
    this.classRecord = record;
    classNameText.setText(record.getClassName());
    descriptionText.setText(record.getDescription());
    teacherText.setText(record.getUserName());
  }

  /**
   * Redirecting to the Student Class View scene and passing classId to its controller
   */
  @FXML
  private void viewClass() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student-class-view.fxml"));
      Parent detailView = loader.load();

      // Passing classId to StudentClassController
      StudentClassController controller = loader.getController();
      controller.setClassId(this.classRecord.getClassId());

      // Finding the elements with fx:id = contentArea to load up the scene
      StackPane contentArea = (StackPane) cardContainer.getScene().lookup("#contentArea");

      contentArea.getChildren().clear();
      contentArea.getChildren().add(detailView);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a popup and loads up the Create Assignment Form scene
   * @param event the menu select event
   */
  @FXML
  private void createAssignment(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-assignment.fxml"));
      Parent root = loader.load();

      CreateAssignmentController controller = loader.getController();
      controller.setClassId(this.classRecord.getClassId());

      Stage popup = new Stage();
      popup.setTitle("Create New Assignment");
      popup.initModality(Modality.APPLICATION_MODAL);

      //Setting the pop up height and width to 30% of the screen and height to auto
      Rectangle2D screen = Screen.getPrimary().getVisualBounds();
      double width = screen.getWidth() * 0.30;
      popup.setScene(new Scene(root, width, -1));
      popup.showAndWait();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
