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

public class LoginController {
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
    private Button login, registerUser;

    /**
     * Constant for display of incorrect password.
     */
    private static final int DISPLAY_ERROR_DURATION_MS = 3000;

    private void displayError(String error) {
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
     * Method fired when pressing the "login" button. Loads the category window.
     */
    @FXML
    public void performLogin() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            switch (ApiConfig.loginControllerPerformLogin(username, password)) {
                case SUCCESS:
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
                    fxmlLoader.setControllerFactory(new CategoryFactory(username));
                    Parent parent = fxmlLoader.load();
                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.setScene(new Scene(parent));
                    stage.show();
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
