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

public class RegistrationController extends AbstractController implements Initializable {

    /**
     * Duration of the error displayed useed if username is taken.
     */
    private static final int DISPLAY_ERROR_DURATION_MS = 3000;

    /**
     * FXML component used to display error.
     */
    @FXML
    private Label errorDisplay;

    /**
     * FXML component used for providing new username.
     */
    @FXML
    private TextField newUsername;

    /**
     * FXML component for providing new password.
     */
    @FXML
    private PasswordField newPassword;

    /**
     * FXML button used for signing up.
     * FXML button for backArrow
     */
    @FXML
    private Button signUp, backArrowbtn;

    /**
     * ImageView of back arrow
     */
    @FXML
    private ImageView backArrowImg;

    private void displayError(final String error) {
        errorDisplay.setText(error);
        errorDisplay.setOpacity(1);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        errorDisplay.setOpacity(0);
                    }
                },
                DISPLAY_ERROR_DURATION_MS);
    }

    /**
     * Method fired when "signUp" is pressed. Launches category selection if
     * username is not taken.
     *
     */
    @FXML
    public void fireSignUp() {
        try {
            String username = newUsername.getText();
            String password = newPassword.getText();
            switch (ApiConfig.registrationResult(username, password)) {
                case SUCCESS:
                    changeSceneTo("Category.fxml", signUp, new CategoryFactory(username));
                    break;
                case USERNAME_TAKEN:
                    displayError("The username \"" + username + "\" is already taken.");
                    break;
                case USERNAME_NOT_MATCH_REGEX:
                    // TODO mer brukervennlig forklaring
                    displayError("The username \"" + username + "\" should be minimum 2 characters and not be 'guest'");
                    break;
                case PASSWORD_NOT_MATCH_REGEX:
                    // TODO mer brukervennlig forklaring
                    displayError("The password \"" + password
                            + "\" needs to be more then 4 characters, contain at least 1 number, "
                            + "1 lowercase letter, 1 uppercase letter and 1 special character '#$%&/?!+'");
                    break;
                case UPLOAD_ERROR:
                    displayError("Error during instantiation of new user.");
                    break;
                default:
                    displayError("Unknown error occured.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change scene back to login page.
     */
    public void toLoginPage() {
        changeSceneTo("LoginPage.fxml", backArrowbtn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackArrowImg(backArrowImg);
    }

}
