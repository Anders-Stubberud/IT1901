package ui;

import java.io.IOException;

import core.UserInfoIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login, registerUser;

    @FXML
    public void performLogin() {
        String providedUsername = username.getText();
        String providedPassword = password.getText();
        if (UserInfoIO.correctUsernameAndPassword(providedUsername, providedPassword)) {
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
