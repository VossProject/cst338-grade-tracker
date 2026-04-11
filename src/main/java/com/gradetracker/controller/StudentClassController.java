package com.gradetracker.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Student class view controller to view assignments.
 *
 * @author Harvey Duran
 * @since TBD
 */
public class StudentClassController {

  @FXML
  private Label classTitleLabel;

  @FXML
  private Label classDescriptionLabel;

  @FXML
  private TableView<?> assignmentTable;

  @FXML
  private TableColumn<?, ?> titleColumn;

  @FXML
  private TableColumn<?, ?> dueDateColumn;

  @FXML
  private TableColumn<?, ?> scoreColumn;

  @FXML
  private TableColumn<?, ?> maxGradeColumn;

  @FXML
  private void handleBack() {
    // TODO: Navigate back to student dashboard
  }

  @FXML
  private void handleLogout() {
    // TODO: Navigate back to login scene
  }
}