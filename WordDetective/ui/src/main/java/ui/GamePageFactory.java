package ui;

import javafx.util.Callback;

public final class GamePageFactory implements Callback<Class<?>, Object> {

    /**
     * Variable for holding the username used to create the gamepage's controller.
     * object.
     */
    private final String username;

    /**
     * Variable for holding the category used to create the gamepage's controller.
     * object.
     */
    private final String category;

    /**
     * Constructor constructing the factory object.
     *
     * @param usernameParameter used to set up correct instance fo the game.
     * @param categoryParameter used to load the wordlist correlating to the
     *                          category.
     */
    public GamePageFactory(final String usernameParameter, final String categoryParameter) {
        this.username = usernameParameter;
        this.category = categoryParameter;
    }

    /**
     * Provides a reference to an instance of the GamePageController with the
     * designated
     * username and category.
     */
    @Override
    public Object call(final Class<?> type) {
        if (type == GamePageController.class) {
            return new GamePageController(username, category);
        }
        return null;
    }
}
