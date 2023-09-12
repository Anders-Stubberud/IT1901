package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class GamePageController implements Initializable {

    @FXML
    private Circle profileCircle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image profileImg;
        try {
            profileImg = new Image(new FileInputStream("./assets/images/Brage.png"));
            profileCircle.setFill(new ImagePattern(profileImg));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
