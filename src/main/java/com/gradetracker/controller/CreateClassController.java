package com.gradetracker.controller;

import com.gradetracker.dao.ClassDao;
import com.gradetracker.dao.SqliteClassDao;
import com.gradetracker.model.ClassRecord;
import com.gradetracker.model.User;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author Robert Mozzetti
 * created: 4/17/2026
 * Explanation: Controller for the create class form.
 */
public class CreateClassController {

  @FXML
  private TextField classNameField;

  @FXML
  private TextArea descriptionField;

  @FXML
  private ComboBox<User> teacherComboBox;

  @FXML
  private ComboBox<User> studentComboBox;

  @FXML
  private ListView<User> enrolledStudentsListView;

  @FXML
  private Label messageLabel;

  @FXML
  private Button submitButton;

  private final ClassDao classDao = new SqliteClassDao();
  private final ObservableList<User> enrolledStudents = FXCollections.observableArrayList();

  @FXML
  private void initialize() {
    configureUserDisplay();
    loadTeachers();
    loadStudents();
    enrolledStudentsListView.setItems(enrolledStudents);
    setMessage("", "text-muted");
  }

  /**
   * @param className the entered class name
   * @param description the entered class description
   * @param teacher the selected teacher
   * @return error message if invalid, otherwise null
   */
  static String validate(String className, String description, User teacher) {
    if (className == null || className.isBlank()) {
      return "Class name is required.";
    }
    if (description == null || description.isBlank()) {
      return "Class description is required.";
    }
    if (teacher == null) {
      return "A teacher must be selected.";
    }
    return null;
  }

  @FXML
  private void handleAddStudent() {
    User selectedStudent = studentComboBox.getValue();
    if (selectedStudent == null) {
      setMessage("Select a student to enroll.", "text-danger");
      return;
    }

    for (User enrolledStudent : enrolledStudents) {
      if (enrolledStudent.getUserId() == selectedStudent.getUserId()) {
        setMessage("That student is already enrolled in this class.", "text-danger");
        return;
      }
    }

    enrolledStudents.add(selectedStudent);
    setMessage("Student added to enrolled list.", "text-success");
    studentComboBox.setValue(null);
  }

  @FXML
  private void handleSubmit() {
    String className = classNameField.getText();
    String description = descriptionField.getText();
    User selectedTeacher = teacherComboBox.getValue();

    String error = validate(className, description, selectedTeacher);
    if (error != null) {
      setMessage(error, "text-danger");
      return;
    }

    try {
      ClassRecord classRecord = new ClassRecord(
          className.trim(),
          description.trim(),
          selectedTeacher.getUserId()
      );
      int classId = classDao.saveClass(classRecord);

      if (!enrolledStudents.isEmpty()) {
        List<Integer> studentIds = enrolledStudents.stream()
            .map(User::getUserId)
            .toList();
        classDao.enrollStudents(classId, studentIds);
      }

      setMessage("Class created and saved successfully.", "text-success");
      clearForm();
      loadTeachers();
      loadStudents();
    } catch (IllegalStateException e) {
      String message = e.getMessage();
      if (message != null && message.contains("UNIQUE")) {
        setMessage("A class with that name already exists.", "text-danger");
        return;
      }
      setMessage("Unable to create class right now.", "text-danger");
    }
  }

  @FXML
  private void handleBack() {
    setMessage("Back button clicked.", "text-muted");
  }

  private void loadTeachers() {
    List<User> teachers = classDao.findAvailableTeachers();
    teacherComboBox.setItems(FXCollections.observableArrayList(teachers));

    if (teachers.isEmpty()) {
      teacherComboBox.setDisable(true);
      submitButton.setDisable(true);
      setMessage("No teachers available. Create a teacher account first.", "text-danger");
      return;
    }

    teacherComboBox.setDisable(false);
    submitButton.setDisable(false);
  }

  private void loadStudents() {
    studentComboBox.setItems(FXCollections.observableArrayList(classDao.findStudents()));
  }

  private void configureUserDisplay() {
    teacherComboBox.setCellFactory(param -> createUserCell());
    teacherComboBox.setButtonCell(createUserCell());
    studentComboBox.setCellFactory(param -> createUserCell());
    studentComboBox.setButtonCell(createUserCell());
    enrolledStudentsListView.setCellFactory(param -> createUserCell());
  }

  private ListCell<User> createUserCell() {
    return new ListCell<>() {
      @Override
      protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        setText(empty || user == null ? null : user.getUserName());
      }
    };
  }

  private void clearForm() {
    classNameField.clear();
    descriptionField.clear();
    teacherComboBox.setValue(null);
    studentComboBox.setValue(null);
    enrolledStudents.clear();
  }

  private void setMessage(String message, String styleClass) {
    messageLabel.setText(message);
    messageLabel.getStyleClass().setAll(styleClass);
  }
}
