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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public final class GamePageController implements Initializable {

    /**
     * The profileCircle is the circle that represents the player.
     */

    @FXML
    private Circle profileCircle, profileCircle1, profileCircle2, profileCircle3;

    /**
     * The lettersCircle is the pane that contains the letters.
     */

    @FXML
    private Pane lettersCircle;
    /**
     * The window is the pane that contains the game.
     */

    @FXML
    private Pane window;
    /**
     * The playerInputField is the textfield where the player writes the word.
     */

    @FXML
    private TextField playerInputField;
    /**
     * Labels on the game page.
     */

    @FXML
    private Label letters, points, categoryDisplay;

    /**
     * Outputfield of what the player writes.
     */
    @FXML
    private TextFlow outputField;

    /**
     * The HowToPlay window.
     */
    @FXML
    private Pane howToPlay;

    /**
     * Buttons for opening and closing HowToPlay window.
     */
    @FXML
    private Button closeHTPBtn, openHTPBtn;

    /**
     * a GameLogic object used to controll the game
     */
    private GameLogic game;
    /**
     * The substring is the letters that the player has to use.
     */
    private String substring;
    /**
     * The players is the list of players.
     */
    private List<Circle> players = new ArrayList<>();
    /**
     * The layoutX is the X position of the game.
     */
    private final int layoutX = 470;
    /**
     * The layoutY is the Y position of the game.
     */
    private final int layoutY = 30;
    /**
     * The layoutCenter is the center of the game.
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
    private final double pageCenter = 500.0; // Default X is center
    /**
     * The bots is the number of bots in the game.
     */
    private final int botsMultiplier = 5;

    /**
     * Number for moving node in X-direction on shake animation.
     */
    private final int shakeXMovment = 4;

    /**
     * Duration of shake animation in milliseconds.
     */
    private final int shakeDuration = 250;

    /**
     * Boolean that determines if HowToPlay window should be opened or closed.
     * Start value is true because of automatic pop-up on screen on game start.
     */
    private boolean showHowToPlay = true;

    /**
     * Variable holding the username of the current player.
     */
    private String username;

    /**
     * Variable holding the category of the given game.
     */
    private String category;

    /**
     * Constructor initializing the object.
     *
     * @param usernameParameter username,'guest' if guest, else provided username.
     * @param categoryParameter category of the given game.
     */
    public GamePageController(final String usernameParameter, final String categoryParameter) {
        this.username = usernameParameter;
        this.category = categoryParameter;
    }

    /**
     * Pick a random player from players list.
     *
     * @return the chosen player
     */
    public String pickPlayer() {
        return game.pickRndPlayer();
    }

    /**
     * Move the game (The letters) to a chosen location. Resets to original
     * posistion after animation.
     *
     * @param targetX  - X position of target
     * @param targetY  - Y position of target
     * @param duration - Duration of animation in seconds
     */
    public void moveLettersTo(final double targetX, final double targetY, final int duration) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(lettersCircle);
        translate.setDuration(Duration.seconds(duration));
        translate.setByY(targetY - lettersCircle.getLayoutY() - layoutCenter); // -30 so the reference point is the
                                                                               // center
        translate.setByX(targetX - lettersCircle.getLayoutX() - layoutCenter);
        translate.play();
        translate.setOnFinished((event) -> {
            lettersCircle.setLayoutX(layoutX); // Reset layout to defualt values
            lettersCircle.setLayoutY(layoutY);
            lettersCircle.setTranslateX(0); // Move lettersCircle to default value
            lettersCircle.setTranslateY(0);
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
        double playerCenterX = pageCenter;

        if (haveBots) {
            double numOfBots = Math.floor(Math.random() * botsMultiplier) + 2; // minimum of 1 bot
            double centerX = window.getPrefWidth() / (numOfBots + 1);
            for (int j = 1; j < (numOfBots + 1); j++) {
                if (j != (int) Math.ceil(numOfBots / 2)) {
                    players.add(new Circle(centerX * j, centerY, radius,
                            new ImagePattern(new Image(new FileInputStream("./assets/images/Abdulbari.png")))));
                } else {
                    playerCenterX = centerX * j;
                }
            }
        }
        Circle activePlayer = new Circle(playerCenterX, centerY, radius,
                new ImagePattern(new Image(new FileInputStream("./assets/images/Brage.png"))));

        // players.add(((int) Math.floor(players.size() / 2)), activePlayer);
        players.add((int) (players.size() / 2), activePlayer);
        window.getChildren().addAll(players);

    }

    /**
     * game checks if player written word is correct. If right add 1 point
     * else shake game.
     *
     * @param ke - KeyEvent
     */
    public void checkWrittenWord(final KeyEvent ke) {
        colorCorrectLetters(playerInputField, outputField);
        if (ke.getCode().equals(KeyCode.ENTER)) { // If pressed Enter, then check word
            String playerGuess = playerInputField.getText();
            if (game.checkValidWord(substring, playerGuess)) {
                FileIO.incrementHighScore(username);
                int pointsHS = FileIO.getHighScore(username);
                points.setText(String.valueOf(pointsHS));
                rndwordMasterLetters();
            } else {
                // Shake inputfield
                TranslateTransition shake = new TranslateTransition();
                shake.setDuration(Duration.millis(shakeDuration));
                shake.setNode(outputField);
                shake.setFromX(-shakeXMovment);
                shake.setToX(shakeXMovment);
                shake.play();
            }

        }

    }

    /**
     * Color the letters in the guessed word that corresponds
     * with the Wordmaster letters in green.
     *
     * @param playerInput - The string the player has written
     * @param textFlow    - where to place the output string
     */
    public void colorCorrectLetters(final TextField playerInput, final TextFlow textFlow) {
        textFlow.getChildren().clear();
        boolean isFrstUsed = false, isScndUsed = false, isThirdUsed = false;
        String playerString = playerInput.getText();
        char[] correctLetters = substring.toCharArray();

        for (int i = 0; i < playerString.length(); i++) {
            char[] inputArray = playerString.toCharArray();
            Text coloredLetter = new Text(String.valueOf(inputArray[i]));
            try {
                if (inputArray[i] == correctLetters[0] && !isFrstUsed) {
                    coloredLetter.setFill(Color.GREEN);
                    isFrstUsed = true;
                } else if (inputArray[i - 1] == correctLetters[0] && inputArray[i] == correctLetters[1]
                        && !isScndUsed) {
                    coloredLetter.setFill(Color.GREEN);
                    isScndUsed = true;
                } else if (inputArray[i - 2] == correctLetters[0]
                        && inputArray[i - 1] == correctLetters[1]
                        && inputArray[i] == correctLetters[2]
                        && !isThirdUsed) {
                    coloredLetter.setFill(Color.GREEN);
                    isThirdUsed = true;
                } else {
                    coloredLetter.setFill(Color.WHITE);
                }
            } catch (Exception e) {
                coloredLetter.setFill(Color.WHITE);
            }
            textFlow.getChildren().add(coloredLetter);
        }
    }

    /**
     * Displays a random set of letters from the category answers.
     * The length of the letters is either 2 or 3.
     */
    public void rndwordMasterLetters() {
        String string = game.getRandomWord();
        System.out.println(string);
        substring = GameLogic.getRandomSubstring(string);
        letters.setText(substring.toUpperCase());
    }

    /**
     * Opens or closes the HowToPlay window.
     */
    public void howToPlay() {
        if (showHowToPlay) {
            howToPlay.setVisible(false);
            showHowToPlay = false;
        } else {
            howToPlay.setVisible(true);
            showHowToPlay = true;
        }
    }

    @Override // Runs on start of the application
    public void initialize(final URL location, final ResourceBundle resources) {
        try {
            game = new GameLogic(username);
            game.setCategory(category);
            rndwordMasterLetters();
            createPlayers(true);
            outputField.setStyle("-fx-font: 24 arial;");
            // Change textfield format till uppercase
            playerInputField.setTextFormatter(new TextFormatter<>((change) -> {
                change.setText(change.getText().toUpperCase());
                playerInputField.setStyle("-fx-opacity: 0");
                return change;
            }));
            playerInputField.requestFocus();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GameLogic getGame() {
        return game;
    }

    public void setGame(final GameLogic game) {
        this.game = game;
    }
}
