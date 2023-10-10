package ui;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class CategoryTest extends ApplicationTest {

    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
        fxmlLoader.setControllerFactory(new CategoryFactory("guest"));
        root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private String getChosenCategory() {
        return ((Label) root.lookup("#displayCategory")).getText();
    }

    @Test
    public void testClick() {
        clickOn("#countries", MouseButton.PRIMARY);
        root = lookup("#displayCategory").queryParent();
        String chosenCategory = getChosenCategory();
        Assert.assertTrue(chosenCategory.equals("countries"));
    }

}
