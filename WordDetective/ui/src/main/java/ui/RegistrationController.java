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

public class RegistrationController {

    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Button signUp;

    @FXML
    public void fireSignUp() {
        String providedUsername = newUsername.getText();
        String providedPassword = newPassword.getText();
        if (UserInfoIO.getAllUsernames().contains(providedUsername)) {
            // Animasjon som blinker rødt ellerno
            throw new IllegalStateException("Brukernavnet er opptatt");
        } else {
            UserInfoIO.insertNewUser(providedUsername, providedPassword);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
                fxmlLoader.setControllerFactory(new CategoryFactory(providedUsername));
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
