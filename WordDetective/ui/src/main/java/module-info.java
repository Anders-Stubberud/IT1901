module project.ui {
    requires project.core;
    requires project.persistence;
    requires project.types;
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.graphics, javafx.fxml;
}
