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
import persistence.JsonIO;
import types.User;

public class RegistrationController {

    /**
     * Duration of the error displayed useed if username is taken.
     */
    private static final int DISPLAY_ERROR_DURATION_MS = 3000;

    /**
     * Database to read and write to.
     */
    private JsonIO database = new JsonIO();

    /**
     * FXML component used to display error if provided username is taken.
     */
    @FXML
    private Label usernameTaken;

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

    /**
     * Method fired when "signUp" is pressed. Launches category selection if
     * username is not taken.
     */
    @FXML
    public void fireSignUp() {
        User newUser = new User(newUsername.getText(), newPassword.getText());
        if (database.getAllUsernames().contains(newUser.getUsername())
                || !(newUser.isCorrectPassword())) {

            usernameTaken.setOpacity(1);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            usernameTaken.setOpacity(0);
                        }
                    },
                    DISPLAY_ERROR_DURATION_MS);

        } else {
            database.addUser(newUser);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
                fxmlLoader.setControllerFactory(new CategoryFactory(newUser));
                Parent parent = fxmlLoader.load();
                Stage stage = (Stage) signUp.getScene().getWindow();
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
