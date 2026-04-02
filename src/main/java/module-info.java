// Declares the libraries this project depends on and which
// packages are accessible to JavaFX for loading FXML scenes.
module com.gradetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;

    opens com.gradetracker to javafx.fxml;
    exports com.gradetracker;
}
