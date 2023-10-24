package ui;

import javafx.util.Callback;
import types.User;

public final class CategoryFactory implements Callback<Class<?>, Object> {

    /**
     * The user to store.
     */
    private final User user;

    /**
     * Constructor constructing the factory object.
     *
     * @param usernameParameter
     */
    public CategoryFactory(final User newUser) {
        this.user = newUser;
    }

    /**
     * Provides a reference to an instance of CategoryController with the correct
     * username.
     */
    @Override
    public Object call(final Class<?> type) {
        if (type == CategoryController.class) {
            return new CategoryController(user);
        }
        return null;
    }
}
