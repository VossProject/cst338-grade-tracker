package com.gradetracker.controller;

import com.gradetracker.dao.AssignmentDao;
import com.gradetracker.dao.InMemoryAssignmentDao;
import com.gradetracker.manager.SceneManager;
import com.gradetracker.model.Assignment;
import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Student class view controller to view assignments.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class StudentClassController {

  private final AssignmentDao assignmentDao = new InMemoryAssignmentDao();

  @FXML
  private Label classTitleLabel;

  @FXML
  private Label classDescriptionLabel;

  @FXML
  private TableView<Assignment> assignmentTable;

  @FXML
  private TableColumn<Assignment, String> titleColumn;

  @FXML
  private TableColumn<Assignment, LocalDate> dueDateColumn;

  @FXML
  private TableColumn<Assignment, String> scoreColumn;

  @FXML
  private TableColumn<Assignment, Integer> maxGradeColumn;

  @FXML
  public void initialize() {
    classTitleLabel.setText("CST 338 - Software Design");
    classDescriptionLabel.setText("Current assignments for this class");

    titleColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getTitle()));

    dueDateColumn.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getDueDate()));

    scoreColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty("N/A"));

    maxGradeColumn.setCellValueFactory(cellData ->
        new SimpleIntegerProperty(cellData.getValue().getMaxGrade()).asObject());

    loadSampleAssignments();
    assignmentTable.getItems().setAll(assignmentDao.findByClassId(1));
  }

  private void loadSampleAssignments() {
    assignmentDao.save(new Assignment(
        "Homework 1",
        "Intro to JavaFX",
        LocalDate.of(2026, 4, 20),
        100,
        1
    ));

    assignmentDao.save(new Assignment(
        "Quiz 1",
        "Basic MVC concepts",
        LocalDate.of(2026, 4, 25),
        20,
        1
    ));

    assignmentDao.save(new Assignment(
        "Project Checkpoint",
        "Login scene and controller progress",
        LocalDate.of(2026, 5, 1),
        50,
        1
    ));
  }

  @FXML
  private void handleBack() {
    // Temporary: route somewhere that already exists
    Stage stage = (Stage) assignmentTable.getScene().getWindow();
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/login.fxml", "Login");
  }

  @FXML
  private void handleLogout() {
    Stage stage = (Stage) assignmentTable.getScene().getWindow();
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/login.fxml", "Login");
  }
}