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
import types.User;

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

    // /**
    //  * Database.
    //  */
    // private JsonIO database = new JsonIO();

    /**
     * Method fired when pressing the "login" button. Loads the category window.
     */
    @FXML
    public void performLogin() {

        //Undøvendig å hente all brukerinfo (inkludert custom lists) når kun passord er nødvendig, men implementasjonen din står.
        // User newUser = database.getUser(username.getText());

        User newUser = null;
        try {
            newUser = ApiConfig.loginControllerPerformLogin(username.getText());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Gir NullPointerException og utfører ikke else-blokken,
        //dersom du ikke tar høyde for at ikke-eksisterende brukernavn kan skrives inn.
        // if (newUser.getPassword().equals(password.getText()))

        if (newUser != null && newUser.getPassword().equals(password.getText())) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
                fxmlLoader.setControllerFactory(new CategoryFactory(newUser));
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
