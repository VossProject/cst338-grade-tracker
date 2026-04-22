package com.gradetracker.controller;

import com.gradetracker.dao.AssignmentDao;
import com.gradetracker.dao.SqliteAssignmentDao;
import com.gradetracker.model.Assignment;
import java.time.LocalDate;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Controller for the create assignment form.
 *
 * @author Mikey Voss
 * @since 2026-04-12
 */
public class CreateAssignmentController {

  @FXML
  private TextField titleField;

  @FXML
  private TextArea descriptionField;

  @FXML
  private DatePicker dueDatePicker;

  @FXML
  private TextField maxGradeField;

  @FXML
  private Label errorLabel;

  private AssignmentDao dao = new SqliteAssignmentDao();
  private int classId;

  /**
   * Passing classId to the scene
   * @param classId int the id of the class in the database
   */
  public void setClassId(int classId) {
    this.classId = classId;
  }

  @FXML
  public void initialize() {
    titleField.setOnAction(e -> descriptionField.requestFocus());
  }

  /**
   * Validates the assignment form fields.
   *
   * @param title the assignment title
   * @param maxGradeText the max grade as a string
   * @param existing assignments already in this class
   * @return error message if invalid, null if valid
   */
  static String validate(String title, String maxGradeText, List<Assignment> existing) {
    if (title == null || title.isBlank()) {
      return "Title is required.";
    }
    if (maxGradeText == null || maxGradeText.isBlank()) {
      return "Max grade is required.";
    }
    try {
      double maxGrade = Double.parseDouble(maxGradeText);
      if (maxGrade <= 0) {
        return "Max grade must be positive.";
      }
    } catch (NumberFormatException e) {
      return "Max grade must be a number.";
    }
    for (Assignment a : existing) {
      if (a.getTitle().equalsIgnoreCase(title)) {
        return "An assignment with this title already exists.";
      }
    }
    return null;
  }

  @FXML
  private void handleSubmit() {
    String title = titleField.getText();
    String maxGradeText = maxGradeField.getText();
    List<Assignment> existing = dao.findByClassId(classId);

    String error = validate(title, maxGradeText, existing);
    if (error != null) {
      setMessage(error, "text-warning");
      return;
    }

    double maxGrade = Double.parseDouble(maxGradeText);
    String description = descriptionField.getText();
    LocalDate dueDate = dueDatePicker.getValue();

    Assignment assignment = new Assignment(title, description, dueDate, maxGrade, classId);
    dao.save(assignment);
    setMessage("Assignment created.", "text-success");
  }

  private void setMessage(String message, String styleClass) {
    errorLabel.setText(message);
    String color = switch (styleClass) {
      case "text-success" -> "#50FA7B";
      case "text-warning" -> "#FFB86C";
      case "text-danger" -> "#FF5555";
      default -> "inherit";
    };
    errorLabel.setStyle("-fx-text-fill: " + color + ";");
  }

}
