package ui;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import core.FileIO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public final class CategoryController implements Initializable {

    private String username;

    @FXML
    private VBox vbox;

    public CategoryController(String username) {
        this.username = username;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Collection<String> categories = FileIO.loadDefaultCategories();
        if (!username.equals("guest")) {
            categories.addAll(FileIO.loadCustomCategories(username));
        }
        for (String category : categories) {
            Button label = new Button(category);
            label.setPadding(new Insets(15, 10, 15, 10));
            label.setFont(new Font(15));
            vbox.getChildren().add(label);
            Label ekstraPlass = new Label("");
            ekstraPlass.setPadding(new Insets(15, 0, 0, 0));
            vbox.getChildren().add(ekstraPlass);
        }
    }

}
