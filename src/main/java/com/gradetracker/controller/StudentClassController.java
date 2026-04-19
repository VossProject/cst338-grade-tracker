package com.gradetracker.controller;

import com.gradetracker.dao.AssignmentDao;
import com.gradetracker.dao.SqliteAssignmentDao;
import com.gradetracker.manager.SceneManager;
import com.gradetracker.manager.Session;
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
 * Controller for the student class view.
 * Displays assignments, placeholder scores, and total points
 * for the currently selected class.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class StudentClassController {

  private final AssignmentDao assignmentDao = new SqliteAssignmentDao();

  /**
   * Temporary default class id until class selection is passed in dynamically.
   */
  private int classId = 1;

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
  private Label totalPointsLabel;

  /**
   * Initializes the student class view table and loads assignments.
   */
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

    loadAssignments();
  }

  /**
   * Sets the class id and reloads assignments for that class.
   *
   * @param classId the selected class id
   */
  public void setClassId(int classId) {
    this.classId = classId;
    loadAssignments();
  }

  /**
   * Loads assignments for the current class and updates total points.
   */
  private void loadAssignments() {
    assignmentTable.getItems().setAll(assignmentDao.findByClassId(classId));
    updateTotalPoints();
  }

  /**
   * Builds the total-points text shown below the table.
   * Currently uses temporary mock score logic until real grades are connected.
   *
   * @param assignments the assignments to total
   * @return formatted total-points text
   */
  static String buildTotalPointsText(Iterable<Assignment> assignments) {
    int totalPossible = 0;
    int totalEarned = 0;

    for (Assignment assignment : assignments) {
      totalPossible += (int) assignment.getMaxGrade();
      totalEarned += (int) (assignment.getMaxGrade() * 0.8); // TODO: replace with real grades
    }

    return "Total Points: " + totalEarned + " / " + totalPossible;
  }

  /**
   * Updates the total-points label.
   */
  private void updateTotalPoints() {
    totalPointsLabel.setText(buildTotalPointsText(assignmentTable.getItems()));
  }

  /**
   * Returns the user to the dashboard.
   */
  @FXML
  private void handleBack() {
    Stage stage = (Stage) assignmentTable.getScene().getWindow();
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/dashboard.fxml", "Dashboard");
  }

  /**
   * Clears the current session and returns the user to the login screen.
   */
  @FXML
  private void handleLogout() {
    Session.clearSession();

    Stage stage = (Stage) assignmentTable.getScene().getWindow();
    SceneManager sceneManager = new SceneManager(stage);
    sceneManager.switchScene("/fxml/login.fxml", "Login");
  }
}