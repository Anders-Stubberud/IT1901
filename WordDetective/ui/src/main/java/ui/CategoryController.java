package ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import types.User;

public final class CategoryController implements Initializable {

    /**
     * The current user.
     */
    private User user;

    /**
     * Database to get all default categories.
     */
    private JsonIO database = new JsonIO();
    /**
     * Reference to the FXML box containing available categories.
     */
    private boolean isGuest;
    /**
     * Boolean to indicate if the user is a guest or not
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
     * Constructor used for controlling whether or not to retrieve custom
     * categories.
     *
     * @param newUser - A user
     */
    public CategoryController(final User newUser) {
        isGuest = newUser.getUsername().equals("guest");
        if (!isGuest) {
            this.user = newUser;
        }
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
     */
    @FXML
    public void uploadCategory() {
        if (!isGuest) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                String categoryName = "Name of Category"; // Replace with actual category name
                // Read and process the file content, assuming it contains a valid JSON structure
                List<String> wordList = readWordListFromFile(selectedFile);// Read word list from the JSON file

                if (wordList != null) {
                    // Store the new category in the user's data
                    currentUser.addCustomCategories(categoryName, wordList);

                    // Save changes in the JSON file using JsonIO class
                    JsonIO jsonIO = new JsonIO();
                    jsonIO.updateUser(currentUser); // Update the user's data in the JSON file

                    renderCategories(); // Update the UI to display the new categories
                }
            }
        }
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
        if (user.getUsername().equals("guest")) {
            upload.setOpacity(0);
        }
    }

    /**
     * Renders the available categories in the GUI.
     */
    public void renderCategories() {
        pane.setVisible(false);
        List<String> categories = new ArrayList<>(database.getAllDefaultCategories().keySet());
        if (!user.getUsername().equals("guest")) {
            categories.addAll(user.getCustomCategories().keySet());
        }
        categories.addAll(database.getAllDefaultCategories().keySet());
        for (String category : categories) {
            String formattedCategory = formatString(category); // Add formatting on the category.
            Button button = new Button(formattedCategory);
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
                    fxmlLoader.setControllerFactory(new GamePageFactory(user, category));
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

    /**
     * Formats the buttons correct.
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
