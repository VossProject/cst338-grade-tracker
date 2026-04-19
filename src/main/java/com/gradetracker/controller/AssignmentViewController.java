package com.gradetracker.controller;

import com.gradetracker.dao.ClassDao;
import com.gradetracker.dao.GradeDao;
import com.gradetracker.dao.SqliteClassDao;
import com.gradetracker.dao.SqliteGradeDao;
import com.gradetracker.model.Assignment;
import com.gradetracker.model.Grade;
import com.gradetracker.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller for the grade assignment scene. Displays the assignment and
 * a table of enrolled students with an editable score per row.
 *
 * @author Mikey Voss
 * @since 2026-04-18
 */
public class AssignmentViewController {

  @FXML
  private Label titleLabel;

  @FXML
  private Label descriptionLabel;

  @FXML
  private Label dueDateLabel;

  @FXML
  private Label maxGradeLabel;

  @FXML
  private TableView<StudentScoreRow> scoreTable;

  @FXML
  private TableColumn<StudentScoreRow, String> studentColumn;

  @FXML
  private TableColumn<StudentScoreRow, String> scoreColumn;

  @FXML
  private Button submitButton;

  @FXML
  private Label statusLabel;

  private final ObservableList<StudentScoreRow> rows = FXCollections.observableArrayList();
  private GradeDao gradeDao = new SqliteGradeDao();
  private ClassDao classDao = new SqliteClassDao();
  private Assignment assignment;

  /**
   * Sets the assignment shown in this view and refreshes the student table.
   *
   * @param assignment assignment to grade
   */
  public void setAssignment(Assignment assignment) {
    this.assignment = assignment;
    populateFields();
  }

  void setGradeDao(GradeDao gradeDao) {
    this.gradeDao = gradeDao;
  }

  void setClassDao(ClassDao classDao) {
    this.classDao = classDao;
  }

  @FXML
  private void initialize() {
    scoreTable.setItems(rows);
    scoreTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    studentColumn.setCellValueFactory(cell ->
        new SimpleStringProperty(cell.getValue().getStudentName()));
    studentColumn.setCellFactory(column -> new DividerCell());
    scoreColumn.setCellValueFactory(cell -> cell.getValue().scoreTextProperty());
    scoreColumn.setCellFactory(column -> new LiveTextFieldCell());
  }

  // Plain text cell with a right border so the Student column reads as
  // visually separated from the Score column.
  private static class DividerCell extends TableCell<StudentScoreRow, String> {
    DividerCell() {
      setStyle("-fx-border-color: transparent -color-border-default transparent transparent;"
          + " -fx-border-width: 0 1 0 0;");
    }

    @Override
    protected void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);
      setText(empty ? null : item);
    }
  }

  /**
   * A TableCell that keeps an always-visible TextField so users can type
   * across rows without re-entering edit mode. Edits write through to the
   * row model.
   */
  private static class LiveTextFieldCell extends TableCell<StudentScoreRow, String> {
    private final TextField field = new TextField();
    private StudentScoreRow boundRow;

    LiveTextFieldCell() {
      field.setMaxWidth(Double.MAX_VALUE);
      field.textProperty().addListener((obs, oldV, newV) -> {
        if (boundRow != null) {
          boundRow.setScoreText(newV);
        }
      });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);
      TableRow<StudentScoreRow> row = getTableRow();
      if (empty || row == null || row.getItem() == null) {
        boundRow = null;
        setGraphic(null);
        return;
      }
      rebind(row.getItem(), item == null ? "" : item);
    }

    // Briefly clears boundRow while refilling the TextField so the
    // textProperty listener does not treat the refill as a user edit.
    private void rebind(StudentScoreRow row, String current) {
      boundRow = null;
      if (!field.getText().equals(current)) {
        field.setText(current);
      }
      boundRow = row;
      setGraphic(field);
      setText(null);
    }
  }

  /**
   * Validates a single grade entry against the assignment's max grade.
   * A blank entry is treated as "no grade entered" and returns null.
   *
   * @param gradeText raw score text from a row
   * @param maxGrade max grade allowed for this assignment
   * @return error message if invalid, null if valid or blank
   */
  static String validate(String gradeText, double maxGrade) {
    if (gradeText == null || gradeText.isBlank()) {
      return null;
    }
    double grade;
    try {
      grade = Double.parseDouble(gradeText);
    } catch (NumberFormatException e) {
      return "Grade must be a number.";
    }
    if (grade < 0) {
      return "Grade cannot be negative.";
    }
    if (grade > maxGrade) {
      return "Grade cannot exceed max grade of " + maxGrade + ".";
    }
    return null;
  }

  private void populateFields() {
    if (assignment == null) {
      titleLabel.setText("No assignment selected");
      descriptionLabel.setText("");
      dueDateLabel.setText("");
      maxGradeLabel.setText("");
      rows.clear();
      submitButton.setDisable(true);
      return;
    }
    titleLabel.setText(assignment.getTitle());
    descriptionLabel.setText(assignment.getDescription());
    dueDateLabel.setText(
        assignment.getDueDate() == null ? "" : assignment.getDueDate().toString());
    maxGradeLabel.setText(String.valueOf(assignment.getMaxGrade()));
    submitButton.setDisable(false);
    loadStudentRows();
  }

  private void loadStudentRows() {
    List<User> students = classDao.findEnrolledStudents(assignment.getClassId());
    List<Grade> existingGrades = gradeDao.findByAssignmentId(assignment.getId());

    Map<Integer, Double> latestByStudent = new HashMap<>();
    for (Grade g : existingGrades) {
      latestByStudent.put(g.getStudentId(), g.getGradeValue());
    }

    List<StudentScoreRow> built = new ArrayList<>();
    for (User student : students) {
      Double existing = latestByStudent.get(student.getUserId());
      built.add(new StudentScoreRow(
          student.getUserId(),
          student.getUsername(),
          existing == null ? "" : String.valueOf(existing)
      ));
    }

    rows.setAll(built);
  }

  @FXML
  private void handleSubmit() {
    if (assignment == null) {
      setStatus("No assignment loaded.", true);
      return;
    }
    List<Grade> toSave = new ArrayList<>();
    for (StudentScoreRow row : rows) {
      String text = row.getScoreText();
      String error = validate(text, assignment.getMaxGrade());
      if (error != null) {
        setStatus(error + " (" + row.getStudentName() + ")", true);
        return;
      }
      if (text == null || text.isBlank()) {
        continue;
      }
      toSave.add(new Grade(
          Double.parseDouble(text), assignment.getId(), row.getStudentId()));
    }
    for (Grade grade : toSave) {
      gradeDao.save(grade);
    }
    setStatus("Saved " + toSave.size() + " grade(s).", false);
    loadStudentRows();
  }

  private void setStatus(String message, boolean isError) {
    statusLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    statusLabel.setText(message);
  }
}
