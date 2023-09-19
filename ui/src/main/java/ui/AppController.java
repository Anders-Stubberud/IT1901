package ui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AppController {


    @FXML
    private Button appLogInBtn, appGuestBtn, undoButton;

    @FXML
    private TextField usernameTF, passwordTF;

    


    @FXML
    void handleLogIn() {
        appGuestBtn.setVisible(false);
        appLogInBtn.setVisible(false);
        usernameTF.setVisible(true);
        passwordTF.setVisible(true);
        undoButton.setVisible(true);
    }

    @FXML
    void handleUndo() {
        undoButton.setVisible(false);
        usernameTF.setVisible(false);
        passwordTF.setVisible(false);
        appGuestBtn.setVisible(true);
        appLogInBtn.setVisible(true);
    }

    @FXML
    void launchGame() {

    }

}
