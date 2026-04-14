/**
 * @author Robert Mozzetti
 * created: 4/13/2026
 * Explanation: Controller for the create user form
 */
package com.gradetracker.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.util.HashSet;
import java.util.Set;

public class CreateUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    // Temporary placeholder until a real DAO/database is wired in.
    private final Set<String> existingUsernames = new HashSet<>();

    @FXML
    private void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList(
                "Admin",
                "Teacher",
                "Student"
        ));

        existingUsernames.add("admin");
        existingUsernames.add("teacher1");

        // Default message style
        setMessage("", "text-muted");
    }

    /**
     * Helper to apply AtlantaFX message styling.
     */
    private void setMessage(String message, String styleClass) {
        messageLabel.setText(message);
        messageLabel.getStyleClass().setAll(styleClass);
    }

    /**
     * Validates user creation form values.
     */
    static String validate(
            String username,
            String firstName,
            String lastName,
            String role,
            String password,
            String confirmPassword,
            Set<String> existing
    ) {
        if (username == null || username.isBlank()) {
            return "Username is required.";
        }
        if (firstName == null || firstName.isBlank()) {
            return "First name is required.";
        }
        if (lastName == null || lastName.isBlank()) {
            return "Last name is required.";
        }
        if (role == null || role.isBlank()) {
            return "Role is required.";
        }
        if (password == null || password.isBlank()) {
            return "Password is required.";
        }
        if (confirmPassword == null || confirmPassword.isBlank()) {
            return "Please confirm the password.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters.";
        }
        for (String existingUsername : existing) {
            if (existingUsername.equalsIgnoreCase(username)) {
                return "That username is already taken.";
            }
        }
        return null;
    }

    @FXML
    private void handleCreateUser() {
        String username = usernameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String role = roleComboBox.getValue();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        String error = validate(
                username,
                firstName,
                lastName,
                role,
                password,
                confirmPassword,
                existingUsernames
        );

        if (error != null) {
            setMessage(error, "text-danger");
            return;
        }

        // TODO: Replace this with DAO/database save logic.
        existingUsernames.add(username);

        setMessage("User created successfully.", "text-success");

        clearForm();
    }

    @FXML
    private void handleBack() {
        // TODO: Navigate back once SceneManager routing is connected.
        setMessage("Back button clicked.", "text-muted");
    }

    private void clearForm() {
        usernameField.clear();
        firstNameField.clear();
        lastNameField.clear();
        roleComboBox.setValue(null);
        passwordField.clear();
        confirmPasswordField.clear();
    }
}
