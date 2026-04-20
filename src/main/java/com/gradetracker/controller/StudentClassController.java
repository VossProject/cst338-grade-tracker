package com.gradetracker.controller;

import com.gradetracker.dao.AssignmentDao;
import com.gradetracker.dao.SqliteAssignmentDao;
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

  private final AssignmentDao assignmentDao = new SqliteAssignmentDao();

  //Adding the field to pass classId
  private int classId;

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
    assignmentTable.setPlaceholder(new Label("No assignments available"));
    assignmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    classTitleLabel.setText("CST 338 - Software Design");
    classDescriptionLabel.setText("Current assignments for this class");

    titleColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty(cellData.getValue().getTitle()));

    dueDateColumn.setCellValueFactory(cellData ->
        new SimpleObjectProperty<>(cellData.getValue().getDueDate()));

    scoreColumn.setCellValueFactory(cellData ->
        new SimpleStringProperty("Not Graded"));

    maxGradeColumn.setCellValueFactory(cellData ->
        new SimpleIntegerProperty((int) cellData.getValue().getMaxGrade()).asObject());

    assignmentTable.getItems().setAll(assignmentDao.findByClassId(1));
    updateTotalPoints();
  }

  @FXML
  private Label totalPointsLabel;

  static String buildTotalPointsText(Iterable<Assignment> assignments) {
    int totalPossible = 0;
    int totalEarned = 0;

    for (Assignment assignment : assignments) {
      totalPossible += (int) assignment.getMaxGrade();
      totalEarned += (int) (assignment.getMaxGrade() * 0.8); // temporary mock score
    }

    return "Total Points: " + totalEarned + " / " + totalPossible;
  }

  private void updateTotalPoints() {
    totalPointsLabel.setText(buildTotalPointsText(assignmentTable.getItems()));
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

  /**
   * Passing classId to the scene
   * @param classId int the id of the class in the database
   */
  public void setClassId(int classId) {
    this.classId = classId;
    getClassData(); // Call your DAO here using the ID
  }

  private void getClassData() {
    System.out.println("Getting data for class: " + classId);
    // add your pull from the db here
  }

}
