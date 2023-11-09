package ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * An abstract parent class for controllers containing the common methods for
 * controllers.
 */
public abstract class AbstractController {

  /**
   * Change the scene to a new fxml scene.
   *
   * @param scene         - The fxml scene to change to. For example
   *                      {@code App.fxml}
   * @param buttonPressed - The button that is clicked when you want to switch
   *                      scene
   */
  public void changeSceneTo(final String scene, final Button buttonPressed) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(scene));
      Stage stage = (Stage) buttonPressed.getScene().getWindow();
      Parent parent = fxmlLoader.load();
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
}
