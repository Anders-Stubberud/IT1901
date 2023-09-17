package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GamePageController implements Initializable {

    @FXML
    private Circle profileCircle, profileCircle1, profileCircle2, profileCircle3;
    @FXML
    private Pane WordMaster;
    @FXML
    private AnchorPane window;
    @FXML
    private TextField PlayerInputField;
    @FXML
    private Label letters, points;

    private List<Circle> players = new ArrayList<>();
    private List<String> categoryAnswers = new ArrayList<>(Arrays.asList("norway"));

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

    /**
     * Create players and draw them on the fxml
     * 
     * @param haveBots - If true add 1-4 bots
     * 
     * @throws FileNotFoundException If player pictures is not found
     */
    public void createPlayers(boolean haveBots) throws FileNotFoundException {
        // TODO read from active player JSON
        int radius = 30;
        int centerY = 425; // Default Y position
        double playerCenterX = 500; // Default X is center

        if (haveBots) {
            double NumOfBots = Math.floor(Math.random() * 5) + 2;// minimum of 1 bot
            double centerX = window.getPrefWidth() / (NumOfBots + 1);
            for (int j = 1; j < (NumOfBots + 1); j++) {
                if (j != (int) Math.ceil(NumOfBots / 2)) {
                    players.add(new Circle(centerX * j, centerY, radius, Color.BLACK));
                } else {
                    playerCenterX = centerX * j;
                }
            }
        }
        Circle activePlayer = new Circle(playerCenterX, centerY, radius,
                new ImagePattern(new Image(new FileInputStream("./assets/images/Brage.png"))));

        players.add(((int) Math.floor(players.size() / 2)), activePlayer);
        window.getChildren().addAll(players);

    }

    /**
     * WordMaster checks if player written word is correct. If right add 1 point
     */
    public void checkWrittenWord(KeyEvent ke) {
        // TODO green color on right letters and red on wrong

        if (ke.getCode().equals(KeyCode.ENTER)) { // If pressed Enter, then check word
            String playerGuess = PlayerInputField.getText().toLowerCase();
            if (playerGuess.contains(letters.getText().toLowerCase())
                    && categoryAnswers.contains(playerGuess.toLowerCase())) {

                // TODO - FileWriter add points
                int Points = Integer.parseInt(points.getText()) + 1;
                points.setText(String.valueOf(Points));
                rndWordMasterLetters();
            } else {
                // TODO - Animate shake effect on wrong answer
            }

        }

    }

    /**
     * Displays a random set of letters from the category answers.
     * The length of the letters is either 2 or 3.
     */
    public void rndWordMasterLetters() {
        String rndCategoryWord = categoryAnswers.get(new Random().nextInt(categoryAnswers.size()));
        int rndIndex = (int) Math.floor(Math.random() * (rndCategoryWord.length() - 3));
        int strLength = (int) Math.floor(Math.random() * 2) + 2;
        String finalString = rndCategoryWord.substring(rndIndex, rndIndex + strLength);
        letters.setText(finalString.toUpperCase());
    }

    @Override // Runs on start of the application
    public void initialize(URL location, ResourceBundle resources) {
        try {
            rndWordMasterLetters();
            createPlayers(true);
            PlayerInputField.requestFocus();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
