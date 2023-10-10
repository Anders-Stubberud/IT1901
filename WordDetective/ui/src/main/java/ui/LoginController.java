package ui;

import java.io.IOException;

import core.UserIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

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
            // Blinke rødt ellerno
            System.out.println("feil brukernavn og/eller passord");
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
