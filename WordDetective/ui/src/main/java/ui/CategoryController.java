package ui;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public final class CategoryController implements Initializable {

    /**
     * The current user.
     */
    private String username;

    // /**
    // * Database to get all default categories.
    // */
    // private JsonIO database = new JsonIO();

    // /**
    // * Reference to the FXML box containing available categories.
    // */
    // private boolean isGuest;

    /**
     * Boolean to indicate if the user is a guest or not.
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
     * FXML textarea where user writes their categories.
     */
    @FXML
    private TextArea customCategoryName, customCategoryWords;

    /**
     * FXML component containing the file-uploading information.
     */
    @FXML
    private Pane pane;

    /**
     * Constructor used for controlling whether or not to retrieve custom
     * categories.
     *
     * @param usernameParameter - A user
     */
    public CategoryController(final String usernameParameter) {
        this.username = usernameParameter;
    }

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
     * Uploads a category selected in the GUI and stores in database.
     *
     * @throws InterruptedException
     * @throws IOException
     */
    @FXML
    // Ser ikke ut som at files lastes inn.
    public void uploadCategory() {
        // if (!username.equals("guest")) {
        // FileChooser fileChooser = new FileChooser();
        // fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON
        // Files", "*.json"));
        // File selectedFile = fileChooser.showOpenDialog(new Stage());
        // if (selectedFile != null) {
        // // Denne gir spotbugs error, dermed kommentert ut.
        // // String filename = selectedFile.getName();

        // jsonIOUser.addCustomCategories(categoryTitle, wordsList);
        // // Store the new category in the user's data
        // ApiConfig.updateUser(jsonIOUser);
        // // Save changes in the JSON file using JsonIO class

        // renderCategories(); // Update the UI to display the new categories
        // }
        // }
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
        try {
            for (String category : ApiConfig.getCategories(username)) {
                String formattedCategory = formatString(category); // Legger til formatting på kategorien
                Button button = new Button(formattedCategory);
                button.setId(category);
                button.setUserData(category);
                button.setPadding(
                        new Insets(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
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
        } catch (IOException | InterruptedException e) {
            // TODO informere bruker om at kategorier ikke ble lastet inn rett
            e.printStackTrace();
        }
    }

    /**
     * Formats the buttons correct.
     *
     * @param input - Category before formatting
     * @return - Category name after formatting
     */
    public String formatString(final String input) {
        String[] words = input.split("_");
        StringBuilder formattedString = new StringBuilder();

        for (String word : words) {
            if (word.length() > 1) {
                formattedString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            } else {
                formattedString.append(word.toUpperCase());
            }

            formattedString.append(" ");
        }

        return formattedString.toString().trim();
    }

}
