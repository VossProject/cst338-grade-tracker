// Declares the libraries this project depends on and which
// packages are accessible to JavaFX for loading FXML scenes.
module com.gradetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;

    opens com.gradetracker to javafx.fxml;
    opens com.gradetracker.controller to javafx.fxml;
    opens com.gradetracker.model to javafx.fxml;
    // NOTE: new packages with @FXML fields need an opens line here
    exports com.gradetracker;
}
