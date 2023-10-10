package ui;

import javafx.util.Callback;

public final class CategoryFactory implements Callback<Class<?>, Object> {

    /**
     * Variable for holding the username used to create the category controller
     * object.
     */
    private final String username;

    /**
     * Constructor constructing the factory object.
     *
     * @param usernameParameter
     */
    public CategoryFactory(final String usernameParameter) {
        this.username = usernameParameter;
    }

    /**
     * Provides a reference to an instance of CategoryController with the correct
     * username.
     */
    @Override
    public Object call(final Class<?> type) {
        if (type == CategoryController.class) {
            return new CategoryController(username);
        }
        return null;
    }
}
