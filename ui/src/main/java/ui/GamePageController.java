package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class GamePageController implements Initializable {

    @FXML
    private Circle profileCircle, profileCircle1, WordMaster;
    @FXML
    private ImageView arrow;

    // Words move towards given player TODO
    public void WordMasterMoveTo(double targetX, double targetY) {

    }

    // Write a word, displays it on screen TODO
    public void writeWord() {

    }

    @Override // Runs on start of the application
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image profileImg = new Image(new FileInputStream("./assets/images/Brage.png"));
            Image arrowImg = new Image(new FileInputStream("./assets/images/Arrow.png"));

            profileCircle.setFill(new ImagePattern(profileImg));
            arrow.setImage(arrowImg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
