package ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import persistence.JsonIO;
import persistence.UserIO;

public final class CategoryController implements Initializable {

    /**
     * Username of the current user. Assigned to 'guest' if guest, else the provided
     * username.
     */
    private String username;

    /**
     * Reference to the FXML box containing available categories.
     */
    @FXML
    private VBox vbox;

    /**
     * FXML buttons for respectively displaying the upload informatin, and to upload
     * a file.
     */
    @FXML
    private Button customCategory, upload;

    /**
     * FXML component providing scrolling throught the available categories.
     */
    @FXML
    private ScrollPane scrollpane;

    /**
     * FXML component containing the file-uploading information.
     */
    @FXML
    private Pane pane;

    /**
     * Toggles the visibility of the option for the user to upload a custom
     * category.
     */
    @FXML
    public void loadCustomCategory() {
        if (pane.isVisible()) {
            pane.setVisible(false);
        } else {
            pane.setVisible(true);
        }
    }

    /**
     * Uploads the file selected in the GUI.
     */
    @FXML
    public void uploadFile() {
        if (!username.equals("guest")) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                String filename = selectedFile.getName();
                UserIO.uploadFile(selectedFile.getAbsolutePath(), username, filename);
                renderCategories();
            }
        }
    }

    /**
     * Constructor used for controlling whether or not to retrieve custom
     * categories.
     *
     * @param usernameParameter 'guest' if guest user, else the provided username.
     */
    public CategoryController(final String usernameParameter) {
        this.username = usernameParameter;
    }

    /**
     * constant used for vertical padding of category choices.
     */
    private static final int VERTICAL_PADDING = 15;

    /**
     * constant used for horizontal padding of category choices.
     */
    private static final int HORIZONTAL_PADDING = 10;

    /**
     * initialization of the Category controller triggers a query retrieving all
     * available categories.
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        renderCategories();
        if (username.equals("guest")) {
            upload.setOpacity(0);
        }
    }

    /**
     * Renders the available categories in the GUI.
     */
    public void renderCategories() {
        pane.setVisible(false);
        Collection<String> categories = JsonIO.loadDefaultCategories();
        if (!username.equals("guest")) {
            categories.addAll(JsonIO.loadCustomCategories(username));
        }
        for (String category : categories) {
            Button button = new Button(category);
            button.setId(category);
            button.setUserData(category);
            button.setPadding(new Insets(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
            button.setFont(new Font(VERTICAL_PADDING));
            vbox.getChildren().add(button);
            Label ekstraPlass = new Label("");
            ekstraPlass.setPadding(new Insets(VERTICAL_PADDING, 0, 0, 0));
            vbox.getChildren().add(ekstraPlass);

            button.setOnAction(event -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GamePage.fxml"));
                    fxmlLoader.setControllerFactory(new GamePageFactory(username, category));
                    Parent parent = fxmlLoader.load();
                    Stage stage = (Stage) button.getScene().getWindow();
                    stage.setScene(new Scene(parent));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
