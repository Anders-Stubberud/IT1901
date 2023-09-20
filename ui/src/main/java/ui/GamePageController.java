package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import core.FileIO;
import core.GameLogic;
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

    /**
     * The profileCircle is the circle that represents the player.
     */
    @FXML
    private Circle profileCircle, profileCircle1, profileCircle2, profileCircle3;
    /**
     * The wordMaster is the pane that contains the letters.
     */
    @FXML
    private Pane wordMaster;
    /**
     * The window is the pane that contains the game.
     */
    @FXML
    private AnchorPane window;
    /**
     * The playerInputField is the textfield where the player writes the word.
     */
    @FXML
    private TextField playerInputField;
    /**
     * The letters is the label that contains the letters.
     */
    @FXML
    private Label letters, points;

    /**
     * The user is the GameLogic object that is used to get the words.
     */
    private GameLogic user;
    /**
     * The substring is the letters that the player has to use.
     */
    private String substring;
    /**
     * The players is the list of players.
     */
    private List<Circle> players = new ArrayList<>();
    /**
     * The layoutX is the X position of the wordMaster.
     */
    private final int layoutX = 470;
    /**
     * The layoutY is the Y position of the wordMaster.
     */
    private final int layoutY = 30;
    /**
     * The layoutCenter is the center of the wordMaster.
     */
    private final int layoutCenter = 30;

    /**
     * The radius is the radius of the player circle.
     */
    private final int radius = 30;

    /**
     * The centerY is the default Y position of the player circle.
     */
    private final int centerY = 425; // Default Y position

    /**
     * The playerCenterX is the X position of the player circle.
     */
    private double playerCenterX = 500.0; // Default X is center
    /**
     * The bots is the number of bots in the game.
     */
    private final int botsMultiplier = 5;

    /**
     * Move the wordMaster (The letters) to a chosen location. Resets to original
     * posistion after animation.
     *
     * @param targetX - X position of target
     * @param targetY - Y position of target
     * @param speed   - Speed of animation in milliseconds
     */
    public void wordMasterMoveTo(final double targetX, final double targetY, final int speed) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(wordMaster);
        translate.setDuration(Duration.millis(speed));
        translate.setByY(targetY - wordMaster.getLayoutY() - layoutCenter); // -30 so the reference point is the center
        translate.setByX(targetX - wordMaster.getLayoutX() - layoutCenter);
        translate.play();
        translate.setOnFinished((event) -> {
            wordMaster.setLayoutX(layoutX); // Reset layout to defualt values
            wordMaster.setLayoutY(layoutY);
            wordMaster.setTranslateX(0); // Move wordMaster to default value
            wordMaster.setTranslateY(0);
            translate.stop();
        });

    }

    /**
     * Create players and draw them on the fxml.
     *
     * @param haveBots - If true add 1-4 bots
     * @throws FileNotFoundException If player pictures is not found
     */
    public void createPlayers(final boolean haveBots) throws FileNotFoundException {
        // TODO read from active player JSON

        if (haveBots) {
            double numOfBots = Math.floor(Math.random() * botsMultiplier) + 2; // minimum of 1 bot
            double centerX = window.getPrefWidth() / (numOfBots + 1);
            for (int j = 1; j < (numOfBots + 1); j++) {
                if (j != (int) Math.ceil(numOfBots / 2)) {
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
     * wordMaster checks if player written word is correct. If right add 1 point
     * else shake wordMaster.
     *
     * @param ke - KeyEvent
     */
    public void checkWrittenWord(final KeyEvent ke) {
        // TODO green color on right letters and red on wrong

        if (ke.getCode().equals(KeyCode.ENTER)) { // If pressed Enter, then check word
            String playerGuess = playerInputField.getText();
            if (user.checkValidWord(substring, playerGuess)) {
                // TODO - FileWriter add points
                FileIO.incrementHighScore();
                int Points = FileIO.getHighScore();
                // int Points = Integer.parseInt(points.getText()) + 1;
                points.setText(String.valueOf(Points));
                rndwordMasterLetters();
            }
            // TODO - Animate shake effect on wrong answer

        }

    }

    /**
     * Displays a random set of letters from the category answers.
     * The length of the letters is either 2 or 3.
     */
    public void rndwordMasterLetters() {
        String string = user.getRandomWord();
        substring = GameLogic.getRandomSubstring(string);
        letters.setText(substring);
    }

    @Override // Runs on start of the application
    public void initialize(final URL location, final ResourceBundle resources) {
        try {
            points.setText(String.valueOf(FileIO.getHighScore()));
            user = new GameLogic("guest");
            user.setCategory("default_category1");
            rndwordMasterLetters();
            createPlayers(true);
            playerInputField.requestFocus();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
