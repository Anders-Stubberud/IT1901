module project.ui {
    requires project.core;
    requires project.persistence;
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.graphics, javafx.fxml;
}
