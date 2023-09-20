package ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AppController {

    @FXML
    private Button appLogInBtn, appGuestBtn, undoButton;

    @FXML
    private TextField usernameTF, passwordTF;

    


    // @FXML
    // void handleLogIn() {
    //     appGuestBtn.setVisible(false);
    //     appLogInBtn.setVisible(false);
    //     usernameTF.setVisible(true);
    //     passwordTF.setVisible(true);
    //     undoButton.setVisible(true);
    // }

    @FXML
    void handleUndo() {
        undoButton.setVisible(false);
        usernameTF.setVisible(false);
        passwordTF.setVisible(false);
        appGuestBtn.setVisible(true);
        appLogInBtn.setVisible(true);
    }

    @FXML
    void handleLogIn() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GamePage.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) appGuestBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void launchGame() {

    }

}
