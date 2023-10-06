package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class AppController {

    /**
     * Buttons on the Front,Login page.
     */
    @FXML
    private Button appLogInBtn, appGuestBtn, undoButton;

    /**
     * Textfield on the Front,Login page.
     */
    @FXML
    private TextField usernameTF, passwordTF;

    // @FXML
    // void handleLogIn() {
    // appGuestBtn.setVisible(false);
    // appLogInBtn.setVisible(false);
    // usernameTF.setVisible(true);
    // passwordTF.setVisible(true);
    // undoButton.setVisible(true);
    // }

    /**
     * Goes back to Frontpage.
     */
    @FXML
    public void handleUndo() {
        undoButton.setVisible(false);
        usernameTF.setVisible(false);
        passwordTF.setVisible(false);
        appGuestBtn.setVisible(true);
        appLogInBtn.setVisible(true);
    }

    /**
     * Send user to game page.
     */
    @FXML
    public void handleLogIn() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
            // må ha en tilsvarende metode som leser inn brukernavnet som ble skrevet inn og
            // bruker det som parameter
            fxmlLoader.setControllerFactory(new CustomControllerFactory("guest"));
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
