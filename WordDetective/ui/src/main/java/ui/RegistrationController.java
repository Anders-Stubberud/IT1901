package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {

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
     * FXML component used for signing up.
     */
    @FXML
    private Button signUp;

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
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
                    fxmlLoader.setControllerFactory(new CategoryFactory(username));
                    Parent parent = fxmlLoader.load();
                    Stage stage = (Stage) signUp.getScene().getWindow();
                    stage.setScene(new Scene(parent));
                    stage.show();
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

}
