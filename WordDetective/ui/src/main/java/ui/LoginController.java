package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public final class LoginController extends AbstractController implements Initializable {
    /**
     * Anchor pane of login.fxml.
     */
    @FXML
    private AnchorPane loginPage;
    /**
     * Label for marking of incorrect password.
     */
    @FXML
    private Label errorDisplay;

    /**
     * FXML component for enabling user to provide username.
     */
    @FXML
    private TextField usernameField;

    /**
     * FXML component for enabling user to provide password.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * FXML buttons providing access to respectively "performLogin" and
     * "registerNewUser".
     */
    @FXML
    private Button login, registerUser, backArrowbtn;

    /**
     * Imageview of backbutton.
     */
    @FXML
    private ImageView backArrowImg;

    /**
     * Display an error message.
     *
     * @param message - The message to display
     */
    private void displayError(final String message) {
        errorDisplay.setText(message);
        errorDisplay.setOpacity(1);
    }

    /**
     * Method fired when pressing the "login" button. Loads the category window.
     */
    @FXML
    public void performLogin() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            switch (ApiConfig.performLogin(username, password)) {
                case SUCCESS:
                    changeSceneTo("Category.fxml", login, new CategoryFactory(username));
                    break;
                case USERNAME_DOES_NOT_EXIST:
                    displayError("Username does not exist.");
                    break;
                case INCORRECT_PASSWORD:
                    displayError("Incorrect password.");
                    break;
                case READ_ERROR:
                    displayError("Error during extraction of password.");
                    break;
                default:
                    displayError("Unknown error occured.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method fired when pressing the "registerUser" button. Loads the registration
     * window.
     */
    @FXML
    public void registerNewUser() {
        changeSceneTo("Registration.fxml", registerUser);
    }

    /**
     * Change scene back to main page.
     */
    @FXML
    public void backToMainPage() {
        changeSceneTo("App.fxml", backArrowbtn);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setBackArrowImg(backArrowImg);
        startBGVideo(loginPage);
    }

}
