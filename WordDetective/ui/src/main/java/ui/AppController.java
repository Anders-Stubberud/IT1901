package ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public final class AppController implements Initializable {

    /**
     * The root pane of the App.fxml.
     */
    @FXML
    private AnchorPane anchorPane;

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

    /**
     * Reference to the given loader.
     */
    @FXML
    private FXMLLoader fxmlLoader;

    /**
     * Send user to Login page.
     */
    @FXML
    public void handleLogIn() {
        try {
            fxmlLoader = new FXMLLoader(this.getClass().getResource("LoginPage.fxml"));
            Stage stage = (Stage) appLogInBtn.getScene().getWindow();
            Parent parent = fxmlLoader.load();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send user to Category selection as guest.
     */
    @FXML
    void launchGame() {
        try {
            fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
            fxmlLoader.setControllerFactory(new CategoryFactory("guest"));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) appGuestBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the background video on your application.
     *
     * @param background - The {@link AnchorPane} to add the video to
     *
     */
    public static void startBGVideo(final AnchorPane background) {
        System.out.println();
        File video = new File(Paths.get("assets").toAbsolutePath()
                + "/video/WordDetectiveBackgroundVideo.mp4");
        File sound = new File(Paths.get("assets").toAbsolutePath()
                + "/music/WordDetectiveMusic.L.wav");
        MediaPlayer videoMP = new MediaPlayer(new Media(video.toURI().toString()));
        MediaPlayer soundMP = new MediaPlayer(new Media(sound.toURI().toString()));
        MediaView videoMV = new MediaView(videoMP);
        MediaView soundMV = new MediaView(soundMP);

        DoubleProperty mvw = videoMV.fitWidthProperty();
        DoubleProperty mvh = videoMV.fitHeightProperty();

        mvw.bind(Bindings.selectDouble(videoMV.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(videoMV.sceneProperty(), "height"));
        videoMV.setPreserveRatio(false);

        background.getChildren().add(0, soundMV);
        background.getChildren().add(0, videoMV);
        videoMP.setCycleCount(Timeline.INDEFINITE);
        soundMP.setCycleCount(Timeline.INDEFINITE);
        videoMP.play();
        soundMP.play();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        startBGVideo(anchorPane);
    }

}
