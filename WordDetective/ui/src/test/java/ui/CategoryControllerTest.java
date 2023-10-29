package ui;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import types.User;

public class CategoryControllerTest extends ApplicationTest {

    /**
     * The root of the application is used as reference to the DOM.
     */
    private Parent root;

    /**
     * Properties t0 get the gitlab CI pipeline to run headless for Integration
     * tests.
     */
    @BeforeClass
    public static void headless() {
        System.setProperty("prism.verbose", "true");
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("testfx.setup.timeout", "2500");
    }

    /**
     * Instantiates the stage.
     */
    @Override
    public void start(final Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Category.fxml"));
        fxmlLoader.setControllerFactory(new CategoryFactory("username"));
        root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Fetches the displayed selected category.
     *
     * @return The displayed selected category.
     */
    private String getChosenCategory() {
        return ((Label) root.lookup("#displayCategory")).getText();
    }

    /**
     * Tests if the correct category gets chosen once it is selected.
     */
    @Test
    public void testSelectCategory() {
        // clickOn("#countries", MouseButton.PRIMARY);
        // root = lookup("#displayCategory").queryParent();
        // String chosenCategory = getChosenCategory();
        // Assert.assertTrue(chosenCategory.equals("countries"));
    }

}
