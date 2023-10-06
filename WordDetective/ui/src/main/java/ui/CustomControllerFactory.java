package ui;

import javafx.util.Callback;

public class CustomControllerFactory implements Callback<Class<?>, Object> {
    private final String username;

    public CustomControllerFactory(String username) {
        this.username = username;
    }

    @Override
    public Object call(Class<?> type) {
        if (type == CategoryController.class) {
            return new CategoryController(username);
        }
        return null;
    }
}
