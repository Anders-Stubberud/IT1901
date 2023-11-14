package ui;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public final class CategoryController extends AbstractController implements Initializable {

    /**
     * The current user.
     */
    private String username;

    /**
     * Anchor pane of page.
     */
    @FXML
    private AnchorPane categoryPage;

    /**
     * Vbox containing categories.
     */
    @FXML
    private VBox vbox;

    /**
     * FXML buttons for respectively displaying the upload information, upload
     * to a file, and to toggle off the category information pane.
     */
    @FXML
    private Button showCustomCatBtn, upload, categoryInformationButton;

    /**
     * FXML component providing scrolling throught the available categories.
     */
    @FXML
    private ScrollPane scrollpane;

    /**
     * FXML textarea where user writes their categories.
     */
    @FXML
    private TextArea categoryName, categoryWords, nameLabel, wordFormat;

    /**
     * FXML components containing respectively the file-uploading information and
     * category information.
     */
    @FXML
    private Pane addCategoryPane, categoryInformationPane;

    /**
     * A pane that pops up when the user wants to delete a category.
     */
    @FXML
    private Pane showAreYouSure;

    /**
     * Button for going back to main page.
     */
    @FXML
    private Button backArrowbtn;
    /**
     * Imageview of back arrow png.
     */
    @FXML
    private ImageView backArrowImg;

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
     * Closes the category information pane.
     */
    @FXML
    public void closeCategoryInformation() {
        categoryInformationPane.setVisible(false);
    }

    /**
     * Toggles the visibility of the option for the user to upload a custom
     * category.
     */
    @FXML
    public void showCustomCategory() {
        if (addCategoryPane.isVisible()) {
            addCategoryPane.setVisible(false);
        } else {
            addCategoryPane.setVisible(true);
        }
    }

    /**
     * Uploads a category selected in the GUI and stores in database.
     *
     * @throws InterruptedException
     * @throws IOException
     */
    @FXML
    public void uploadCategory() {
        if (!username.equals("guest")) {
            String chosenCategoryName = categoryName.getText();
            String words = categoryWords.getText();
            String[] wordList = words.split("\n");
            try {
                ApiConfig.addCustomCategory(chosenCategoryName, wordList);
                addCategoryPane.setVisible(false);
                renderCategories();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
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
        setBackArrowImg(backArrowImg);
        startBGVideo(categoryPage);
        renderCategories();
        renderCategories();
        if (username.equals("guest")) {
            showCustomCatBtn.setVisible(false);
            upload.setOpacity(0);
        }
    }

    /**
     * Renders the available categories in the GUI.
     */
    public void renderCategories() {
        if (username.equals("guest")) {
            showCustomCatBtn.setVisible(false);
        }
        try {
            vbox.getChildren().clear();
            for (Map.Entry<String, Set<String>> categorySet : ApiConfig.getCategories(username).entrySet()) {
                for (String category : categorySet.getValue()) {
                    String formattedCategory = formatString(category); // Legger til formatting på kategorien
                    // Make button
                    Button button = new Button(formattedCategory);
                    button.setId(category);
                    button.getStyleClass().add("categoryBtn");
                    button.setUserData(category);
                    button.setPadding(
                            new Insets(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING));
                    button.setFont(new Font(VERTICAL_PADDING));

                    if (categorySet.getKey().equals("custom")) {
                        HBox hbox = new HBox();
                        hbox.setSpacing(10);
                        hbox.setAlignment(javafx.geometry.Pos.CENTER);
                        Button deleteButton = new Button("X");
                        deleteButton.getStyleClass().add("deleteBtn");
                        deleteButton.setOnAction((event) -> {
                            deleteCategory(button.getText());
                        });
                        hbox.getChildren().addAll(button, deleteButton);
                        vbox.getChildren().add(hbox);
                    } else {
                        vbox.getChildren().add(button);
                    }
                    Label ekstraPlass = new Label("");
                    ekstraPlass.setPadding(new Insets(VERTICAL_PADDING, 0, 0, 0));
                    vbox.getChildren().add(ekstraPlass);

                    button.setOnAction(event -> {
                        changeSceneTo("GamePage.fxml", button, new GamePageFactory(username, category));
                    });
                }
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

    /**
     * Change scene back to main page.
     */
    public void backToMainPage() {
        changeSceneTo("App.fxml", backArrowbtn);
    }

    /**
     * Deletes a category from the database.
     *
     * @param category - Category to be deleted
     *
     */
    public void deleteCategory(final String category) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText("You are about to delete the category " + category);
        alert.setContentText("Are you sure you want to delete this category?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Deleting category" + category);
            try {
                ApiConfig.deleteCustomCategory(category);
                System.out.println("Category deleted");
                renderCategories();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Cancelled");
        }
    }

}
