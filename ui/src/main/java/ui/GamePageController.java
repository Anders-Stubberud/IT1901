package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GamePageController implements Initializable {

    @FXML
    private Circle profileCircle, profileCircle1, profileCircle2, profileCircle3;
    @FXML
    private Pane WordMaster;
    @FXML
    private TextField PlayerInputField;

    /**
     * Move the WordMaster (The letters) to a chosen location. Resets to original
     * posistion after animation.
     * 
     * @param targetX - X position of target
     * @param targetY - Y position of target
     * @param speed   - Speed of animation in milliseconds
     */
    public void WordMasterMoveTo(double targetX, double targetY, int speed) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(WordMaster);
        translate.setDuration(Duration.millis(speed));
        translate.setByY(targetY - WordMaster.getLayoutY() - 30); // -30 so the reference point is the center
        translate.setByX(targetX - WordMaster.getLayoutX() - 30);
        translate.play();
        translate.setOnFinished((event) -> {
            WordMaster.setLayoutX(470); // Reset layout to defualt values
            WordMaster.setLayoutY(70);
            WordMaster.setTranslateX(0); // Move Wordmaster to default value
            WordMaster.setTranslateY(0);
            translate.stop();
        });

    }

    // Write a word, displays it on screen TODO
    public void writeWord() {
        WordMasterMoveTo(profileCircle2.getLayoutX(), profileCircle2.getLayoutY(), 1000);
        PlayerInputField.requestFocus();
    }

    @Override // Runs on start of the application
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image profileImg = new Image(new FileInputStream("./assets/images/Brage.png"));

            profileCircle.setFill(new ImagePattern(profileImg));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
