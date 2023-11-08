package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    private Pane innerWindow;
    /**
     * The playerInputField is the textfield where the player writes the word.
     */

    @FXML
    private TextField playerInputField;
    /**
     * Labels on the game page.
     */

    @FXML
    private Label letters, points, categoryDisplay, highScore;

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

    // /**
    // * a Game object used to controll the game.
    // */
    // private Game game;
    /**
     * The substring is the letters that the player has to use.
     */
    private String substring;
    /**
     * The current user.
     */
    private String username;
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
    private final double centerX = 500.0; // Default X is center
    // /**
    // * The bots is the number of bots in the game.
    // */
    // private final int botsMultiplier = 5;

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
     * Variable holding the category of the given game.
     */
    private String currentCategory;

    /**
     * Constructor initializing the object.
     *
     * @param usernameParameter username.
     * @param categoryParameter category of the given game.
     */
    public GamePageController(final String usernameParameter, final String categoryParameter) {
        this.username = usernameParameter;
        this.currentCategory = categoryParameter;
    }

    /**
     * Empty Constuctor for initialising controller.
     *
     * @param category The category chosen by the user.
     */
    public GamePageController(final String category) {
        this("guest", category);
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
     * game checks if player written word is correct. If right add 1 point
     * else shake game.
     *
     * @param ke - KeyEvent
     */
    public void checkWrittenWord(final KeyEvent ke) {
        colorCorrectLetters(playerInputField, outputField);
        if (ke.getCode().equals(KeyCode.ENTER)) { // If pressed Enter, then check word
            String playerGuess = playerInputField.getText();
            try {
                if (ApiConfig.checkValidWord(playerGuess, playerGuess)) {
                    int newPoints = Integer.parseInt(points.getText()) + 1;
                    points.setText(String.valueOf(newPoints));
                    playerInputField.setText("");
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
            } catch (NumberFormatException | IOException | InterruptedException e) {
                e.printStackTrace();
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
        try {
            substring = ApiConfig.getSubstring();
            letters.setText(substring.toUpperCase());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens or closes the HowToPlay window.
     */
    @FXML
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
            try {
                ApiConfig.newGame(username, currentCategory);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            rndwordMasterLetters();
            Circle activePlayer = new Circle(centerX, centerY, radius,
                    new ImagePattern(new Image(new FileInputStream("./assets/images/Brage.png"))));

            innerWindow.getChildren().addAll(activePlayer);
            // createPlayers(true);
            outputField.setStyle("-fx-font: 24 arial;");
            // Change textfield format till uppercase
            playerInputField.setTextFormatter(new TextFormatter<>((change) -> {
                change.setText(change.getText().toUpperCase());
                playerInputField.setStyle("-fx-opacity: 0");
                return change;
            }));
            playerInputField.requestFocus();
            categoryDisplay.setText("Category: " + currentCategory.toUpperCase().replace("_", " "));
            try {
                highScore.setText(String.valueOf(ApiConfig.getHighScore()));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            // Add shutdownhook that updates user highscore when closing application
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    if (!username.equals("guest")) {
                        try {
                            // game.savePlayerHighscore(Integer.valueOf(points.getText()));
                            ApiConfig.savePlayerHighscore(points.getText());
                        } catch (NumberFormatException | IOException | InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }, "Shutdown-thread"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
