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
import persistence.UserIO;

public class LoginController {

    /**
     * Label for marking of incorrect password.
     */
    @FXML
    private Label incorrect;

    /**
     * FXML component for enabling user to provide username.
     */
    @FXML
    private TextField username;

    /**
     * FXML component for enabling user to provide password.
     */
    @FXML
    private PasswordField password;

    /**
     * FXML buttons providing access to respectively "performLogin" and
     * "registerNewUser".
     */
    @FXML
    private Button login, registerUser;

    /**
     * Constant for display of incorrect password.
     */
    private static final int DISPLAY_ERROR_DURATION_MS = 3000;

    /**
     * Method fired when pressing the "login" button. Loads the category window.
     */
    @FXML
    public void performLogin() {
        String providedUsername = username.getText();
        String providedPassword = password.getText();
        if (UserIO.correctUsernameAndPassword(providedUsername, providedPassword)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
                fxmlLoader.setControllerFactory(new CategoryFactory(providedUsername));
                Parent parent = fxmlLoader.load();
                Stage stage = (Stage) login.getScene().getWindow();
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            incorrect.setOpacity(1);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            incorrect.setOpacity(0);
                        }
                    },
                    DISPLAY_ERROR_DURATION_MS);
        }
    }

    /**
     * Method fired when pressing the "registerUser" button. Loads the registration
     * window.
     */
    @FXML
    public void registerNewUser() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Registration.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) login.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
