package com.gradetracker.controller;

import com.gradetracker.dao.AssignmentDao;
import com.gradetracker.dao.GradeDao;
import com.gradetracker.dao.SqliteAssignmentDao;
import com.gradetracker.dao.SqliteGradeDao;
import com.gradetracker.manager.Session;
import com.gradetracker.model.Assignment;
import com.gradetracker.model.Grade;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Student class view controller to view assignments.
 *
 * @author Harvey Duran
 * @since 04/24/26
 */
public class StudentClassController {

  private final AssignmentDao assignmentDao = new SqliteAssignmentDao();
  private final GradeDao gradeDao = new SqliteGradeDao();

  private int classId;

  private Map<Integer, Double> gradesByAssignment = new HashMap<>();

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
  private TableColumn<Assignment, Void> gradeColumn;

  /**
   * Sets up the assignment table, binds the simple columns, and applies the
   * role-based visibility rules. Per-row data is loaded later by
   * {@link #setClassId(int)} since classId isn't known at init time.
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

    maxGradeColumn.setCellValueFactory(cellData ->
        new SimpleIntegerProperty((int) cellData.getValue().getMaxGrade()).asObject());

    boolean isStudent = Session.isStudent();
    gradeColumn.setVisible(Session.isTeacher() || Session.isAdmin());
    scoreColumn.setVisible(isStudent);
    totalPointsLabel.setVisible(isStudent);
    totalPointsLabel.setManaged(isStudent);
    gradeColumn.setCellFactory(col -> new TableCell<Assignment, Void>() {
      private final Button gradeButton = new Button("Grade");
      {
        gradeButton.setOnAction(event -> {
          Assignment assignment = getTableView().getItems().get(getIndex());
          openGrading(assignment);
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : gradeButton);
      }
    });
  }

  @FXML
  private Label totalPointsLabel;

  /**
   * Builds the bottom label text showing the student's overall grade.
   * Ungraded assignments are excluded from both the numerator and the
   * denominator, so missing work doesn't count against the student until
   * it's graded.
   *
   * @param assignments all assignments for the class
   * @param gradesByAssignment map of assignment id to grade value
   * @return formatted label text, e.g. "Grade: B (85.8%)" or "Grade: Not Graded"
   */
  static String buildTotalPointsText(Iterable<Assignment> assignments,
      Map<Integer, Double> gradesByAssignment) {
    double totalEarned = 0;
    double totalPossible = 0;
    boolean anyGraded = false;

    for (Assignment assignment : assignments) {
      Double grade = gradesByAssignment.get(assignment.getId());
      if (grade != null) {
        totalEarned += grade;
        totalPossible += assignment.getMaxGrade();
        anyGraded = true;
      }
    }

    if (!anyGraded) {
      return "Grade: Not Graded";
    }

    double percentage = (totalEarned * 100.0) / totalPossible;
    return String.format("Grade: %s (%.1f%%)", letterGrade(percentage), percentage);
  }

  /**
   * Maps a percentage to a letter grade using the standard US scale
   * with plus/minus variants.
   *
   * @param pct the grade percentage (0 to 100)
   * @return the letter grade
   */
  static String letterGrade(double pct) {
    if (pct >= 97) {
      return "A+";
    }
    if (pct >= 93) {
      return "A";
    }
    if (pct >= 90) {
      return "A-";
    }
    if (pct >= 87) {
      return "B+";
    }
    if (pct >= 83) {
      return "B";
    }
    if (pct >= 80) {
      return "B-";
    }
    if (pct >= 77) {
      return "C+";
    }
    if (pct >= 73) {
      return "C";
    }
    if (pct >= 70) {
      return "C-";
    }
    if (pct >= 67) {
      return "D+";
    }
    if (pct >= 63) {
      return "D";
    }
    if (pct >= 60) {
      return "D-";
    }
    return "F";
  }

  /**
   * Formats a grade value, dropping the trailing ".0" when the value is a
   * whole number so the Score column shows "85" instead of "85.0".
   *
   * @param value the grade value
   * @return formatted string
   */
  static String formatGrade(double value) {
    if (value == (long) value) {
      return String.valueOf((long) value);
    }
    return String.valueOf(value);
  }

  private void updateTotalPoints() {
    totalPointsLabel.setText(buildTotalPointsText(assignmentTable.getItems(), gradesByAssignment));
  }

  @FXML
  private void handleBack() {
    try {
      Parent destination = FXMLLoader.load(getClass().getResource("/fxml/non-admin-classes.fxml"));
      StackPane contentArea = (StackPane) assignmentTable.getScene().lookup("#contentArea");
      contentArea.getChildren().setAll(destination);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Passing classId to the scene.
   *
   * @param classId the id of the class in the database
   */
  public void setClassId(int classId) {
    this.classId = classId;
    getClassData();
  }

  private void getClassData() {
    final List<Assignment> assignments = assignmentDao.findByClassId(this.classId);
    List<Grade> myGrades = gradeDao.findByStudentId(Session.getUserId());

    gradesByAssignment = new HashMap<>();
    for (Grade g : myGrades) {
      gradesByAssignment.put(g.getAssignmentId(), g.getGradeValue());
    }

    scoreColumn.setCellValueFactory(cellData -> {
      Double grade = gradesByAssignment.get(cellData.getValue().getId());
      return new SimpleStringProperty(grade != null ? formatGrade(grade) : "Not Graded");
    });

    assignmentTable.getItems().setAll(assignments);
    updateTotalPoints();
  }

  /**
   * Opens the Grade Assignment scene as a modal popup sized to 30% of the
   * screen width. Modal so the user can't interact with the class view
   * until grading is dismissed, which keeps the grades map in sync.
   *
   * @param assignment the assignment to grade
   */
  private void openGrading(Assignment assignment) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/assignment-view.fxml"));
      final Parent root = loader.load();

      AssignmentViewController controller = loader.getController();
      controller.setAssignment(assignment);

      Rectangle2D screen = Screen.getPrimary().getVisualBounds();
      double width = screen.getWidth() * 0.30;

      Stage popup = new Stage();
      popup.setTitle("Grade Assignment");
      popup.initModality(Modality.APPLICATION_MODAL);
      popup.setScene(new Scene(root, width, -1));
      popup.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
