package types;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public final class User {
    
    /**
     * The username of the given player.
     */
    @SerializedName("username")
    private String name;

    /**
     * The password of the given player.
     */
    @SerializedName("password")
    private String pwd;

    /**
     * The highscore of the given player.
     */
    @SerializedName("highscore")
    private int highscore;

    /**
     * The user's custom categories.
     */
    @SerializedName("customCategories")
    private HashMap<String, List<String>> customCategories;

    /**
     * Constructor setting up a new User object to be injected into correlating.
     * json file.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     */
    public User(final String username, final String password) {
        // this.customCategories = new HashMap<>();
        this.highscore = 0;
        this.name = username;
        this.pwd = password;
    }

    /**
     * Empty constructor for guests users.
     * Also used to initiate class for Json file reading
     */
    public User() {
        this("guest", "");
    }

    /**
     * Get user's highscore.
     *
     * @return The highscore of the given user as an Integer.
     */
    public int getHighScore() {
        return highscore;
    }

    /**
     * Get user's username.
     *
     * @return The username of the given user as String.
     */
    public String getUsername() {
        return name;
    }

    /**
     * Get user's password.
     *
     * @return The password of the given user as a string.
     */
    public String getPassword() {
        return pwd;
    }

    /**
     * Get user's custom categories.
     *
     * @return a HashMap containing the user's custom categories
     */
    public HashMap<String, List<String>> getCustomCategories() {
        return customCategories;
    }

    /**
     * Set the user's highscore.
     *
     * @param score - The highscore to set
     */
    public void setHighscore(final int score) {
        this.highscore = score;
    }

    /**
     * Set the user's custom categories.
     *
     * @param newCustomCategories - a HashMap with all the categories
     */
    public void setCustomCategories(final HashMap<String, List<String>> newCustomCategories) {
        this.customCategories = newCustomCategories;
    }

    /**
     * Add a custom category to user.
     *
     * @param categoryName   - Name of category
     * @param customCategory - List of all answers in category
     */
    public void addCustomCategories(final String categoryName, final List<String> customCategory) {
        this.customCategories.put(categoryName, customCategory);
    }

    /**
     * Delete a custom category from user's custom categories.
     *
     * @param category - The category to delete
     */
    public void deleteCustomCategories(final String category) {
        this.customCategories.remove(category);
    }

    /**
     * check if username is correct according to set regex.
     *
     * @return {@link Boolean}
     */
    public boolean isCorrectUsername() {
        return getUsername().matches("^(?!guest)[a-zA-Z0-9_ ]{2,}$");
    }

    /**
     * check if password is correct according to set regex.
     *
     * @return {@link Boolean}
     */
    public boolean isCorrectPassword() {
        return getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$");
    }

}
