module project.ui {
    requires project.core;
    requires project.persistence;
    requires project.types;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml, javafx.media;
}
